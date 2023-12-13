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

const ListOfMediciens=()=>{
  const [initialMedicines, setInitialMedicines] = useState([])
  const [cart, setCart] = useState([])


  return (
  <div>
      <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static" sx={{ backgroundColor: '#2e2d88' }}>
      <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
          Medicied
          </Typography>
          <IconButton size="medium" edge="start" color="inherit" aria-label="menu" sx={{ mr: 1 }}>
          <ShoppingCartIcon />
          </IconButton>
          <IconButton size="medium" edge="start" color="inherit" aria-label="menu" sx={{ ml: 1, mr: 1 }}>
          <AccountCircleIcon />
          </IconButton>
      </Toolbar>
      </AppBar>
  </Box>
      <ImageList sx={{ width: '100%', height: '100%'}} cols={5}>
      {initialMedicines.map((item) => (
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
}

export default ListOfMediciens