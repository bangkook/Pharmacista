import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CustomAlert from './Alert/CustomAlert';
import { Button, Container, Paper, Typography } from '@mui/material';

export default function ShoppingCart({userId = 1}) {
  //const serialNumbers = [1, 2, 3];
  const [medicines, setMedicines] = useState([]);
  const [cart, setCart] = useState([]);
  const [isCartEmpty, setIsCartEmpty] = useState(true); 
  const [totalPrice, setTotalPrice] = useState(0);
  const [customAlert, setCustomAlert] = useState(null);

  const showAlert = (message) => {
    return setCustomAlert(<CustomAlert message={message} onClose={() => setCustomAlert(null)} />);
  };
  
  
  
  const fetchMedicines = async () => {
    
    try {
      const response = await axios.get(`http://localhost:8088/Product-From-Cart?userId=${userId}`);
      setMedicines(response.data);
      console.log(response.data);
  
      // Use map to update each product in the response
      const updatedCart = response.data.map((product) => {
        let quantity;
        if(product.amount == 0){
          handleDeleteItem(product.productSN);
        }
        else if (product.quantity > product.amount) {
          quantity = product.amount;
        }
        else {
          quantity = product.quantity;
        }
  
        return {
          ...product,
          productName: product.productName, // Corrected from product.Name
          quantity: quantity,
        };
      });
  
      // Initialize the cart with default quantities and max amounts
      setCart(updatedCart);
    } catch (error) {
      console.error('Error fetching medicines:', error);
    }
  };
  
  
  useEffect(() => {
    fetchMedicines();
  }, [userId]);
  

  const handleQuantityChange = async (serialNumber, newQuantity) => {
    try {
      // Make an API call to fetch the max amount from the back-end
      const maxAmountResponse = await axios.get(`http://localhost:8088/get-max-amount?serialNumber=${serialNumber}`);
      const maxAmount = maxAmountResponse.data;
      console.log(maxAmount)
      if(newQuantity == 0){
        newQuantity = 1;
      }
      
      if(maxAmount == 0){
        handleDeleteItem(serialNumber);
        showAlert(`This product is out-of-stock`);
      }
      else{

        if(newQuantity > maxAmount){
          newQuantity = maxAmount
          //alert("here");
          showAlert(`Cannot exceed the maximum amount: ${maxAmount}`);
        }
      
        // Check if the new quantity exceeds the max amount
        if (newQuantity <= maxAmount) {
          
          setCart((prevCart) =>
            prevCart.map((item) =>
              item.serialNumber === serialNumber ? { ...item, quantity: newQuantity } : item
            )
          );

          try {
            await axios.put(`http://localhost:8088/Update-Quantity`, {
              userId: userId, // Replace with the actual user ID
              productSN: serialNumber,
              quantity: newQuantity,
            });
            fetchMedicines();
            setCart((prevCart) =>
              prevCart.map((item) =>
                item.serialNumber === serialNumber ? { ...item, quantity: newQuantity } : item
              )
            );


          } catch (error) {
            console.error('Error updating quantity:', error);
          }

        } 
      
        else {
         
          console.log(`Cannot exceed the maximum amount: ${maxAmount}`);
         // showAlert(`Cannot exceed the maximum amount: ${maxAmount}`);
        }
      }
      } catch (error) {
        console.error('Error fetching max amount:', error);
      }
  };

  const deleteCartItem = async (serialNumber) => {
    try {
      const response = await fetch(`http://localhost:8088/removeCartItem?userId=${userId}&productSN=${serialNumber}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      if (response.ok) {
        const data = await response.text();
        console.log(data);
      } else {
        console.error('CartItem not found or deletion failed:', response.statusText);
      }
    } catch (error) {
      console.error('Error deleting:', error.message);
    }
  };
  

  const handleDeleteItem = (serialNumber) => {
    deleteCartItem(serialNumber)
      .then(() => fetchMedicines()) // Fetch medicines only after successful deletion
      .catch((error) => console.error('Error deleting item:', error));
  };

  const calculateTotalPrice = () => {
    return cart.reduce((total, item) => {
      return total + item.quantity * item.price;
    }, 0);
  };
  

  const handleConfirmPurchase = async () => {
    try {
      // Fetch the updated cart data from the server
      const updatedCartResponse = await axios.get(`http://localhost:8088/Product-From-Cart?userId=${userId}`);
      const updatedCart = updatedCartResponse.data;
  
      // Compare the current cart with the updated cart to check for changes
      const cartChanges = cart.some((item) => {
      const updatedItem = updatedCart.find((updatedItem) => updatedItem.productSN === item.productSN);
        
        // Check if the quantity in the cart is higher than the quantity available in the inventory
      const quantityExceedsInventory = updatedItem && item.quantity > updatedItem.amount;

      return !updatedItem || updatedItem.quantity < item.quantity || quantityExceedsInventory;
      });

      if (cartChanges) {
        showAlert('Cart has been updated. Please review your items before confirming the purchase.');
        fetchMedicines(); // Refresh the cart to reflect the changes
        return;
      }
    
        
        const orderInfo = {
            userId: userId,
            dateCreated: new Date(),
            totalPrice: parseFloat(calculateTotalPrice()).toFixed(2)
        };
        console.log("orderInfo: "+orderInfo);

        const response = await axios.post('http://localhost:8088/createOrder', orderInfo);
        console.log(response.status);

        if (response.status === 200) {
            const orderId = response.data;
            console.log('Order ID:', orderId);

            for (const item of cart) {
                const orderDetail = {
                    orderId: orderId,
                    productSN: item.productSN,
                    quantity: item.quantity
                };

                try {
                    const orderDetailResponse = await axios.post('http://localhost:8088/createOrderDetails', orderDetail);
                    console.log(orderDetailResponse.status);
                    console.log(orderDetailResponse.data);

                    const requestData = {
                        serialNumber: item.productSN, // Fix the key to match the server-side
                        quantity: item.quantity
                    };

                    try {
                        const updateResponse = await axios.put('http://localhost:8088/UpdateOrderdProducts', requestData);
                        console.log(updateResponse.status);
                        console.log(updateResponse.data);
                        if(updateResponse.data == "empty"){
                          showAlert(`${item.productName} only has amount = ${item.quantity}`);
                          fetchMedicines();

                        }
                        else if(updateResponse.data == "outOfStock"){
                          showAlert(`${item.productName} is outOfStock`)
                          handleDeleteItem(item.productSN);
                        }
                        else{
                          showAlert(`successfully Purchase!`)
                          handleDeleteItem(item.productSN);
                        }

                    } catch (error) {
                        console.error('Error updating quantity:', error.message);
                    }

                } catch (orderDetailError) {
                    console.error('Error creating order detail:', orderDetailError.message);
                }
            }

        } else {
            console.error('Done:', response.status);
        }

    } catch (error) {
        console.error('Error:', error.message);
    }
  
};


  useEffect(() => {
    // Update isCartEmpty whenever cart changes
    setIsCartEmpty(cart.length === 0);
  }, [cart]);
  

  return (
    <Container style={{ marginTop: '20px' }}>
      <Paper elevation={3} style={{ padding: '20px', borderRadius: '8px' }}>
        <Typography variant="h4" style={{ color: '#4CAF50', marginBottom: '20px' }}>
          Your Shopping Cart
        </Typography>

        <div style={{ overflowY: 'scroll', maxHeight: '400px', marginBottom: '20px' }}>
          <ul style={{ listStyle: 'none', padding: 0 }}>
            {cart.map((item) => (
              <li
                key={item.productSN}
                style={{
                  marginBottom: '20px',
                  borderBottom: '1px solid #ddd',
                  paddingBottom: '10px',
                  display: 'flex',
                  alignItems: 'center',
                }}
              >
                <div>
                  <img
                    src={item.photo}
                    alt={item.productName}
                    style={{ maxWidth: '150px', maxHeight: '150px', marginRight: '20px', borderRadius: '8px' }}
                  />
                </div>
                <div style={{ flex: 1 }}>
                  <Typography variant="h6">Name: {item.productName}</Typography>
                  <Typography variant="body1">Price: ${item.price}</Typography>
                  <label>
                    Quantity:
                    <input
                      type="number"
                      min="1"
                      max={item.maxAmount}
                      value={item.quantity}
                      onChange={(e) => handleQuantityChange(item.productSN, parseInt(e.target.value, 10))}
                      style={{ marginLeft: '10px', padding: '5px', borderRadius: '5px' }}
                    />
                  </label>
                </div>
                <Button
                  onClick={() => handleDeleteItem(item.productSN)}
                  style={{
                    marginLeft: '10px',
                    backgroundColor: '#f44336',
                    color: 'white',
                    padding: '5px',
                    borderRadius: '5px',
                  }}
                >
                  X
                </Button>
              </li>
            ))}
          </ul>
        </div>
        <Typography variant="h6" style={{ marginTop: '10px' }}>
          Total Price: ${calculateTotalPrice().toFixed(2)}
        </Typography>
        <Button
          onClick={handleConfirmPurchase}
          variant="contained"
          style={{
            backgroundColor: isCartEmpty ? '#9e9e9e' : '#4CAF50',
            color: 'white',
            padding: '10px',
            borderRadius: '8px',
            cursor: isCartEmpty ? 'not-allowed' : 'pointer',
            marginTop: '20px',
          }}
          disabled={isCartEmpty}
        >
          Confirm Purchase
        </Button>
        {customAlert}
      </Paper>
    </Container>
  );
}