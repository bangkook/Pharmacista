import React, { useState, useEffect,useMemo  } from "react";
import { Button, TextField } from "@mui/material";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import Box from "@mui/material/Box";
import { styled, alpha } from '@mui/material/styles';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import InputBase from '@mui/material/InputBase';
import MenuIcon from '@mui/icons-material/Menu';
import SearchIcon from '@mui/icons-material/Search';

const Search = styled('div')(({ theme }) => ({
  position: 'relative',
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha('#2e2d88', 0.15),
  '&:hover': {
    backgroundColor: alpha('#2e2d88', 0.25),
  },
  width: '100%',
  [theme.breakpoints.up('sm')]: {
    marginLeft: theme.spacing(1),
    width: 'auto',
  },
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: '100%',
  position: 'absolute',
  pointerEvents: 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  color: '#2e2d88', // Set the color of the search icon to white
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  color: 'inherit',
  width: '100%', // Make the search bar take the full width
  '& .MuiInputBase-input': {
    padding: theme.spacing(1, 1, 1, 0),
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create('width')
  },
}));

const ListOfMediciens=({userId})=>{
  const BaseUri = 'http://localhost:8088'
  const [initialMedicines, setInitialMedicines] = useState([]);
  const [filteredMedicines, setFilteredMedicines] = useState([]);
  const [cart, setCart] = useState([])
  const [products, setProducts] = useState([])
  const [searchTerm, setSearchTerm] = useState('');

  const getListOfMediciens = async () => {
    try {
      const response = await fetch(`${BaseUri}/product/getAllAvailableProducts`);
      if (response.ok) {
        const data = await response.json();
        setInitialMedicines(data);
        setFilteredMedicines(data);
        try {
          const responseCart = await fetch(`${BaseUri}/cartItem/ProductFromCart/${userId}`);
          if (responseCart.ok) {
            const dataCart = await responseCart.json();
            setProducts(dataCart);
          } else {
            console.error('Failed to fetch medicines:', responseCart.statusText);
          }
        } catch (error) {
          console.error('Error fetching products from cart:', error);
        }
      } else {
        console.error('Failed to fetch medicines:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching medicines:', error.message);
    }
  };
  
  useEffect(() => {
    getListOfMediciens();
  }, [userId]);
  
  useEffect(() => {
    setCart([...cart, ...products]);
  }, [products]);

  const handleInputChange = (event) => {
    console.log(event.target.value);
    const filteredMedicines = initialMedicines.filter(
      (medicine) => medicine.name.toLowerCase().includes(event.target.value.toLowerCase())
    );
    setFilteredMedicines(filteredMedicines); // Update the list on each input change
  };

  const addItemToCart = async (serialNumber) => {
    try {
      const response = await fetch(`${BaseUri}/cartItem/addCartItem`,{                
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({userId: userId, productSN: serialNumber, quantity: 1 })
      })

      if (response.ok) {
        const data = await response.text()
        console.log(data)
      } else {
        console.error('Failed to add item:', response.statusText)
      }
    } catch (error) {
      console.error('Error adding item:', error.message)
    }
  };

  const deleteCartItem = async (serialNumber) => {
    try {
      const response = await fetch(`${BaseUri}/cartItem/removeCartItem/${userId}/${serialNumber}`,{
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        }
      })

      if (response.ok) {
        const data = await response.text()
        console.log(data)
      } else {
        console.error('CartItem not found or deletion failed:', response.statusText)
      }
    } catch (error) {
      console.error('Erorr deleting:', error.message)
    }
  };
   
  const isAvailableProducts = async (serialNumber) => {
    try {
      const response = await fetch(`${BaseUri}/product/isAvailableProducts/${serialNumber}`)
      if (response.ok) {
        const isAvailable = await response.text();
        console.log(`Product is available: ${isAvailable}`);
        return isAvailable;
      } else {
        console.error('Failed to check product availability:', response.statusText);
        return false;
      }
    } catch (error) {
      console.error('Error fetching medicines:', error.message)
    }
  };
  
  const addToCart = async (medicine) => {
    const isAlreadyInCart = cart.some((item) => item === medicine)
    if (isAlreadyInCart) {
      const updatedCart = cart.filter((item) => item !== medicine)
      setCart(updatedCart)
      deleteCartItem(medicine)
    } else {
      if(await isAvailableProducts(medicine)==='true'){
        setCart([...cart, medicine])
        addItemToCart(medicine)
      }else{
        alert("out of stock")
        setInitialMedicines([])
        getListOfMediciens()
      }
    }
  }
  return (
  <div>
        <Search>
        <SearchIconWrapper>
          <SearchIcon />
        </SearchIconWrapper>
        <StyledInputBase
          placeholder="Search…"
          inputProps={{ 'aria-label': 'search' }}
          onInput={handleInputChange}
        />
      </Search>
      <ImageList sx={{ width: '100%', height: '100%'}} cols={5}>
      {filteredMedicines.map((item) => (
          <ImageListItem key={item.serialNumber}>
          <img src={item.photo} alt={item.name} style={{ Width: '248px', Height: '230px'}} />
          <ImageListItemBar title={item.name} subtitle={<span>Price: {item.price}</span>} position="below" />
          <Button
              onClick={() => addToCart(item.serialNumber)}
              variant="contained"
              style={{
              color: 'white',
              backgroundColor: cart.some((cartItem) => cartItem === item.serialNumber)
                  ? '#a6192e'
                  : '#2e2d88',
              }}
          >
              {cart.some((cartItem) => cartItem=== item.serialNumber) ? 'Remove from Cart' : 'Add to Cart'}
          </Button>
          </ImageListItem>
      ))}
      </ImageList>
  </div>
  );
};



export default ListOfMediciens