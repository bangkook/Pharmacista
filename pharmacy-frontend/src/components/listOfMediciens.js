import { Grid, Paper,Avatar, TextField, Button,  Card, CardContent, Modal, Typography ,Link} from "@mui/material";
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

import { styled } from '@mui/system';
const StyledImage = styled('img')({
  width: '100%',
  height: '210px',
  objectFit: 'cover',
  transition: 'opacity 0.3s ease-in-out, filter 0.3s ease-in-out',
  '&:hover': {
    cursor: 'pointer',
    opacity: 0.85,
    filter: 'brightness(0.7)',
  }
});

const ListOfMediciens=({userId})=>{
  console.log(userId)
  const BaseUri = 'http://localhost:8088'
  const [initialMedicines, setInitialMedicines] = useState([])
  const [cart, setCart] = useState([])
  const [products, setProducts] = useState([])
  const [selectedMedicine, setSelectedMedicine] = useState(null);
  
  const handleMedicineClick = (medicine) => {
    setSelectedMedicine(medicine);
  };

  const handleCloseModal = () => {
    setSelectedMedicine(null);
  };

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
        <ImageList sx={{ width: '100%', height: '100%'}} cols={5}>
        {initialMedicines.map((item) => (
            <ImageListItem key={item.serialNumber}>
            <StyledImage
              src={item.photo}
              alt={item.name}
              onClick={() => handleMedicineClick(item)}
            />
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
        <Modal
        open={Boolean(selectedMedicine)}
        onClose={handleCloseModal}
        sx={{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
      <Box sx={{ width: '55%', height: '75%', bgcolor: 'background.paper', p: 2 }}>
      {selectedMedicine && (
        <div>
          <Box display="flex">
            <Box display="flex" flexDirection="column" alignItems="center">
              <img
                alt={selectedMedicine.name}
                src={selectedMedicine.photo}
                style={{ objectFit: 'cover', height: '250px', width: '250px', marginBottom: '12px' }}
              />
              <Button
                onClick={() => addToCart(selectedMedicine.serialNumber)}
                variant="contained"
                style={{
                  width: '100%',
                  color: 'white',
                  backgroundColor: cart.some((cartItem) => cartItem === selectedMedicine.serialNumber) ? '#a6192e' : '#2e2d88',
                }}
              >
                {cart.some((cartItem) => cartItem === selectedMedicine.serialNumber) ? 'Remove from Cart' : 'Add to Cart'}
              </Button>
            </Box>
            <CardContent>
              <Typography variant="h5" component="div" style={{ marginBottom: '12px' }}>
                {selectedMedicine.name}
              </Typography>
              <Typography variant="body2" color="text.secondary" style={{ marginBottom: '12px' }}>
                Price: {selectedMedicine.price}
              </Typography>
              <Typography variant="body1" component="div" style={{ marginBottom: '12px' }}>
                {selectedMedicine.description}
              </Typography>
              <Typography variant="body2" color="text.secondary" style={{ marginBottom: '12px' }}>
                Production date: {selectedMedicine.productionDate}
              </Typography>
              <Typography variant="body2" color="text.secondary" style={{ marginBottom: '12px' }}>
                Expiry date: {selectedMedicine.expiryDate}
              </Typography>
            </CardContent>
          </Box>
        </div>
      )}
    </Box>
      </Modal>
    </div>
    );
  }
  



export default ListOfMediciens