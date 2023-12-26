import { Grid, Paper,Avatar, TextField, Button, Typography ,Link} from "@mui/material";
import React, { useState }  from "react";
import { useEffect } from 'react';
import IconButton from '@mui/material/IconButton';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { wait } from "@testing-library/user-event/dist/utils";

const ListOfMediciens=({userId})=>{
  console.log(userId)
  const BaseUri = 'http://localhost:8088'
  const [initialMedicines, setInitialMedicines] = useState([])
  const [cart, setCart] = useState([])
  const [products, setProducts] = useState([])


  const getListOfMediciens = async () => {
    try {
      const response = await fetch(`${BaseUri}/product/getAllAvailableProducts`);
      if (response.ok) {
        const data = await response.json();
        setInitialMedicines(data);
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
  }, []);
  
  // Use another useEffect to update cart when products change
  useEffect(() => {
    setCart([...cart, ...products]);
  }, [products]);
  

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
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" sx={{ backgroundColor: '#2e2d88' }}>
          <Toolbar>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'center' }}>
              Medicines List
            </Typography>
          </Toolbar>
        </AppBar>
      </Box>
      <ImageList sx={{ width: '100%', height: '100%' }} cols={5} gap={8}>
        {initialMedicines.map((item) => (
          <ImageListItem key={item.serialNumber}>
            <Paper elevation={3} sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
              <img
                src={item.photo}
                alt={item.name}
                className="medicine-image"
                style={{ maxWidth: '100%', maxHeight: '150px' }}
              />
              <ImageListItemBar title={item.name} subtitle={<span>Price: {item.price}</span>} position="below" />
              <Button
                onClick={() => addToCart(item.serialNumber)}
                variant="contained"
                size="small"
                style={{
                  color: 'white',
                  backgroundColor: cart.some((cartItem) => cartItem === item.serialNumber) ? '#a6192e' : '#2e2d88',
                }}
              >
                {cart.some((cartItem) => cartItem === item.serialNumber) ? 'Remove' : 'Add'}
              </Button>
            </Paper>
          </ImageListItem>
        ))}
      </ImageList>
    </div>
  );
};



export default ListOfMediciens