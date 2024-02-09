import React, { useState, useEffect } from 'react';
import './OrderList.css';
import '../user_profile/UserProfile.css';
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';

const BaseUri = 'http://localhost:8088';

const OrderList = ({ userId, onSelectOrder, admin }) => {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    let Uri = '';
    if (admin) Uri = `${BaseUri}/orders/all`;
    else Uri = `${BaseUri}/orders/${userId}`;
    fetch(Uri)
      .then((response) => response.json())
      .then((data) => setOrders(data))
      .catch((error) => console.error('Error fetching user orders:', error));
  }, [userId, admin]);

  return (
    <div className="yourorders">
      <thead className="yourorderstable2">
        <tr>
          <th scope="col">Order ID</th>
          {admin && <th scope="col">User ID</th>}
          <th scope="col">Date</th>
          <th scope="col">Total</th>
          <th scope="col">Invoice</th>
        </tr>
      </thead>
      <div className="scrollable-container">
        <table className="yourorderstable">
          <tbody>
            {orders.map((order) => (
              <tr key={order.id}>
                <td data-label="OrderID">{order.id}</td>
                {admin && <td data-label="UserID">{order.userId}</td>}
                <td data-label="OrderDate">{order.dateCreated}</td>
                <td data-label="Total">${order.totalPrice}</td>
                <td data-label="Invoice">
                  <button
                    className="mainbutton1"
                    onClick={() => onSelectOrder(order.id, order.totalPrice, order.dateCreated)}
                  >
                    View
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

const InvoiceView = ({ orderDetails, totalPrice, dateCreated }) => {
  console.log("dateCreated: "+dateCreated);
  return (
    <div className="invoice-view">
      <div className="invoice-header">
        <h2>Invoice</h2>
        <p>Order ID: {orderDetails.length > 0 ? orderDetails[0].orderId : ''}</p>
        <p>Date: {dateCreated}</p>
      </div>
      <div className="invoice-details">
        <ul>
          {orderDetails.map((details) => (
            <div key={`${details.product.productSN}-${details.quantity}`}>
              <div className="product-info">
                <img src={details.product.photo} alt="Product Photo" className="product-photo" />
                <div className="product-details">
                  <p>{details.product.name}</p>
                  <p>Price: ${details.product.price}</p>
                  <p>Quantity: {details.quantity}</p>
                </div>
                {/* Display total quantity beside the item */}
                <p className="total-quantity">Quantity price: {details.product.price * details.quantity}</p>
              </div>
              <hr />
              {/* <p className="total-price">${details.product.price * details.quantity}</p> */}
            </div>
          ))}
        </ul>
      </div>
      <div className="invoice-total">
        <p>Total: ${totalPrice}</p>
      </div>
    </div>
  );
};

const OrderDetails = ({ orderId, total, dateCreated}) => {
  const [orderDetails, setOrderDetails] = useState([]);
  
  useEffect(() => {
    fetch(`${BaseUri}/orders/${orderId}/details`)
      .then((response) => response.json())
      .then((data) => {
        console.log("orderDetails:", data); 
        setOrderDetails(data)})
      .catch((error) =>
        console.error(`Error fetching order details for Order ID ${orderId}:`, error)
      );
  }, [orderId]);

  return (
    <div className="order-details-container">
      <InvoiceView orderDetails={orderDetails} totalPrice={total} dateCreated={dateCreated} />
    </div>
  );
};

const Modal = ({ isOpen, onClose, children }) => {
  const handleClose = (e) => {
    if (e.target.classList.contains('modal-overlay')) {
      onClose();
    }
  };

  return (
    isOpen && (
      <div className="modal-overlay" onClick={handleClose}>
        <div className="modal-content">
          {children}
        </div>
      </div>
    )
  );
};


const Orders = ({ userId = 1, admin = true }) => {
  const [selectedOrderId, setSelectedOrderId] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [totalPrice, setTotalPrice] = useState(0);
  const [dateCreated, setDateCreated] = useState(null);

  const handleSelectOrder = (orderId, total, dateCreated) => {
    setSelectedOrderId(orderId);
    setTotalPrice(total);
    setDateCreated(dateCreated);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setSelectedOrderId(null);
    setIsModalOpen(false);
  };

  return (
    <div>
      <OrderList userId={userId} onSelectOrder={handleSelectOrder} admin={admin} />
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        <OrderDetails orderId={selectedOrderId} total={totalPrice} dateCreated={dateCreated}/>
      </Modal>
    </div>
  );
};

export default Orders;