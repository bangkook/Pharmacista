import React, { useState, useEffect } from 'react';
import './OrderList.css'
import '../user_profile/UserProfile.css'
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';

const BaseUri = 'http://localhost:8088'

const OrderList = ({userId, onSelectOrder, admin = true}) => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    let Uri = ''
    if (admin)
      Uri = `${BaseUri}/orders/all`
    else
      Uri = `${BaseUri}/orders/${userId}`
    // Fetch user orders when the component mounts
    fetch(Uri)
      .then(response => response.json())
      .then(data => setOrders(data))
      .catch(error => console.error("Error fetching user orders:", error));
  }, []);

  return (
    <div className='yourorders'>        
        <table className='yourorderstable'>
          <thead>
            <tr>
              <th scope='col'>Order ID</th>
              {admin && <th scope='col'>User ID</th>}
              <th scope='col'>Date</th>
              <th scope='col'>Total</th>
              <th scope='col'>Invoice</th>
            </tr>
          </thead>

          <tbody>
            {orders.map(order => (
              <tr key = {order.id}>
                  <td data-label='OrderID'>{order.id}</td>
                  {admin && <td data-label='UserID'>{order.userId}</td>}
                  <td data-label='OrderDate'>{order.dateCreated}</td>
                  <td data-label='Total'>${order.totalPrice}</td>
                  <td data-label='Invoice'>
                      <button className='mainbutton1' onClick={() => onSelectOrder(order.id)}>View</button>
                  </td>
              </tr>
            ))}
          </tbody>
        </table>
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
      <div className='c3'>
        <table>
            <thead>
                <tr>
                    <th scope='col'>Sno.</th>
                    <th scope='col'>Product</th>
                    <th scope='col'>Price</th>
                    <th scope='col'>Quantity</th>
                    <th scope='col'>Total Price</th>
                </tr>
            </thead>

            <tbody>
                
              {orderDetails.map(details => ( 
                <tr key={details.productSN}>
                    <td>
                        {details.productSN}
                    </td>
                    <td>{details.productName}</td>
                    <td>${details.productPrice}</td>
                    <td>{details.quantity}</td>
                    <td>${details.productPrice} * {details.quantity}</td>
                </tr>
              ))}
                
            </tbody>
        </table>
      </div>
    );
  };

  const SingleBanner = ({bannerimage, heading}) => {
    return (
      <div className='singlebanner'>
          <div className='bannerimgfilter'></div>
          <img className='bannerimg' src={bannerimage} alt='noimg' />
          <div className='bannerheading'>
              <h1>{heading}</h1>
          </div>
      </div>
    )
  }

  const Orders = ({userId = 1}) => {
    const [selectedOrderId, setSelectedOrderId] = useState(null);
    const [popup, setPopup] = useState(false)

    const handleSelectOrder = (orderId) => {
      setSelectedOrderId(orderId);
      setPopup(true);
    };
    
    const handleClose = () => {
      setPopup(false);
    };

    return (
      <div>
        <SingleBanner 
        heading={`Orders`}
        bannerimage = 'https://images.unsplash.com/photo-1607619056574-7b8d3ee536b2?q=80&w=1880&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D' 
        />
        <OrderList userId={userId} onSelectOrder={handleSelectOrder} />
        {<Popup open={popup} modal nested> 
        {close => (
          <div className='modal'>
          <button className="close" onClick={() => { close(); handleClose();}}>
            &times;
          </button>
          <div className="header"> Order Details </div>
          <OrderDetails orderId={selectedOrderId} className="content"/>
          <div className='actions'>
            <button
            className="mainbutton1"
            onClick={() => {
              console.log('modal closed ');
              close();
              handleClose();
            }}
          >
            close
          </button>
          </div> 
          </div>
          
        )}
        </Popup>
        
        }
      </div>
    );
  };
  
  export default Orders;