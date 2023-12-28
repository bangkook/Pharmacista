import React, { useState, useEffect } from 'react';
import '../orders_list/OrderList.css'
import '../user_profile/UserProfile.css'
import Popup from 'reactjs-popup';
import 'reactjs-popup/dist/index.css';
import ConfirmAlert from '../Alert/ConfirmAlert';

const BaseUri = 'http://localhost:8088'

const FavoritesList = ({userId}) => {
  const [favorites, setFavorites] = useState([]);
  const [isConfirmOpen, setConfirmOpen] = useState(false);

  useEffect(() => {
    // Fetch user favorites list when the component mounts
    fetch(`${BaseUri}/favorites/get/${userId}`)
      .then(response => response.json())
      .then(data => setFavorites(data))
      .catch(error => console.error("Error fetching user favorites list:", error));
  }, [userId]);

  const addItemToList = async (serialNumber) => {
    try {
      const response = await fetch(`${BaseUri}/favorites/add`,{                
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({userId: userId, productSN: serialNumber})
      })

      if (response.ok) {
        const data = await response.text()
        console.log(data)
        // Re-fetch list
        fetch(`${BaseUri}/favorites/get/${userId}`)
        .then(response => response.json())
        .then(data => setFavorites(data))
        .catch(error => console.error("Error fetching user favorites list:", error));
      } else {
        console.error('Failed to add item:', response.statusText)
      }
    } catch (error) {
      console.error('Error adding item:', error.message)
    }
  };

  const deleteItemFromList = async (serialNumber) => {
    // Display a confirmation prompt
    setConfirmOpen(true);
    const confirmDelete = window.confirm("Are you sure you want to remove this item from list?");

    if(!confirmDelete)
      return;

    try {
      console.log(JSON.stringify({userId: userId, productSN: serialNumber}))
      const response = await fetch(`${BaseUri}/favorites/delete`,{
        method: "DELETE",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({userId: userId, productSN: serialNumber})
      })

      if (response.ok) {
        const data = await response.text()
        console.log(data)
        // Re-fetch list
        fetch(`${BaseUri}/favorites/get/${userId}`)
        .then(response => response.json())
        .then(data => setFavorites(data))
        .catch(error => console.error("Error fetching user favorites list:", error));
      } else {
        console.error('Item not found or deletion failed:', response.statusText)
      }
    } catch (error) {
      console.error('Erorr deleting:', error.message)
    } finally {
      setConfirmOpen(false);
    }
  };

  const fetchSortedList = async () => {
    try {
        const response = await fetch(`${BaseUri}/favorites/get-sorted/${userId}`,{
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          }
        })
  
        if (response.ok) {
          const data = await response.json()
          setFavorites(data)
          console.log(data)
        } else {
          console.error('Error fetching list ', response.statusText)
        }
      } catch (error) {
        console.error('Unexpected error: ', error.message)
      }
  };

  const handleCancel = () => {
    // Close the confirmation modal without deleting
    setConfirmOpen(false);
  };

  return (
    <div>
      <div> <button className='mainbutton1' onClick={fetchSortedList} >Sort</button> </div>
    <div className='yourorders'>        
        <table className='yourorderstable'>
          <thead>
            <tr>
              <th scope='col'>Product</th>
              <th scope='col'>Serial Number</th>
              <th scope='col'>Name</th>
              <th scope='col'>Price</th>
              <th scope='col'>Action</th>
            </tr>
          </thead>

          <tbody>
            {favorites.map(item => (
              <tr key = {item.productSN}>
                  <td data-label='Image'>{item.productPhoto}</td>
                  <td data-label='SerialNumber'>{item.productSN}</td>
                  <td data-label='Name'>{item.productName}</td>
                  <td data-label='Price'>${item.productPrice}</td>
                  <td data-label='Action'>
                      <button className='mainbutton1' onClick={() => deleteItemFromList(item.productSN)}>Remove From Favorites</button>
                  </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      </div>
  );
};

export default FavoritesList;