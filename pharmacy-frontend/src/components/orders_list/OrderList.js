import React, { useState, useEffect } from 'react';

const BaseUri = 'http://localhost:8088/api'

const OrderList = ({userId, onSelectOrder}) => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    // Fetch user orders when the component mounts
    fetch(`${BaseUri}/orders/${userId}`)
      .then(response => response.json())
      .then(data => setOrders(data))
      .catch(error => console.error("Error fetching user orders:", error));
  }, []);

  return (
    <div>
      <h2>User Orders</h2>
      <ul>
        {orders.map(order => (
          <li key={order.id} onClick={() => onSelectOrder(order.id)}>
            Order ID: {order.id}, Date Created: {order.dateCreated}, Total Cost: {order.totalPrice}
          </li>
        ))}
      </ul>
    </div>
  );
};

const OrderDetails = ({ orderId }) => {
    const [orderDetails, setOrderDetails] = useState([]);
  
    useEffect(() => {
      // Fetch order details when the component mounts or when orderId changes
      fetch(`${BaseUri}/orders/${orderId}/details`)
        .then(response => response.json())
        .then(data => setOrderDetails(data))
        .catch(error => console.error(`Error fetching order details for Order ID ${orderId}:`, error));
    }, [orderId]);
  
    return (
      <div>
        <h2>Order Details</h2>
        <ul>
          {orderDetails.map(details => (
            <li key={details.id}>
              Serial Number : {details.productID}, Name: {details.productName}, Price: {details.productPrice}, 
              Quantity: {details.quantity}, Total: {details.productPrice} * {details.quantity}
            </li>
          ))}
        </ul>
      </div>
    );
  };

  const App = ({userId}) => {
    const [selectedOrderId, setSelectedOrderId] = useState(null);
  
    const handleSelectOrder = (orderId) => {
      setSelectedOrderId(orderId);
    };
  
    return (
      <div>
        <OrderList userId={userId} onSelectOrder={handleSelectOrder} />
        {selectedOrderId && <OrderDetails orderId={selectedOrderId} />}
      </div>
    );
  };
  
  export default App;