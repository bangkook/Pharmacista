import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function ShoppingCart() {

  

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', textAlign: 'center', maxWidth: '800px', margin: 'auto' }}>
      <h2 style={{ color: '#4CAF50' }}>your Shopping Cart</h2>
      <div style={{ overflowY: 'scroll', maxHeight: '400px', border: '1px solid #ddd', borderRadius: '8px', padding: '20px' }}>
        <ul style={{ listStyle: 'none', padding: 0 }}>
          {cart.map((item) => (
            <li
              key={item.productSN} // Assuming productSN is a unique identifier
              style={{ marginBottom: '20px', borderBottom: '1px solid #ddd', paddingBottom: '10px', display: 'flex', alignItems: 'center' }}
            >
              <div>
                <img src={item.photo} alt={item.productName} style={{ maxWidth: '150px', maxHeight: '150px', marginRight: '20px', borderRadius: '8px' }} />
              </div>
              <div style={{ flex: 1 }}>
                <p>Name: {item.productName}</p>
                <p>Price: ${item.price}</p>
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
              <button onClick={() => handleDeleteItem(item.productSN)} style={{ marginLeft: '10px', backgroundColor: 'red', color: 'white', padding: '5px', borderRadius: '5px', cursor: 'pointer' }}>
                X
              </button>
            </li>
          ))}
        </ul>
      </div>
      <p style={{ marginTop: '10px' }}>Total Price: ${calculateTotalPrice().toFixed(2)}</p>
      <button
  onClick={handleConfirmPurchase}
  style={{
    backgroundColor: isCartEmpty ? 'gray' : '#4CAF50',
    color: 'white',
    padding: '10px',
    borderRadius: '8px',
    cursor: isCartEmpty ? 'not-allowed' : 'pointer',
    marginTop: '20px',
  }}
  disabled={isCartEmpty}
>
  Confirm Purchase
</button>
    </div>
  );
}