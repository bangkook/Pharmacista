import React, { useState, useEffect } from 'react';
import '../orders_list/OrderList.css';
import '../user_profile/UserProfile.css';
import 'reactjs-popup/dist/index.css';
import ConfirmAlert from '../Alert/ConfirmAlert';

const BaseUri = 'http://localhost:8088';

const FavoritesList = ({ userId }) => {
  const [favorites, setFavorites] = useState([]);
  const [isConfirmOpen, setConfirmOpen] = useState(false);
  const [itemToDelete, setItemToDelete] = useState(null);

  useEffect(() => {
    // Fetch user favorites list when the component mounts
    fetch(`${BaseUri}/favorites/get-sorted/${userId}`)
      .then(response => response.json())
      .then(data => setFavorites(data))
      .catch(error => console.error("Error fetching user favorites list:", error));
  }, [userId]);

  const addItemToList = async (serialNumber) => {
    try {
      const response = await fetch(`${BaseUri}/favorites/add`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId: userId, productSN: serialNumber })
      });

      if (response.ok) {
        const data = await response.text();
        console.log(data);
        // Re-fetch list
        await fetch(`${BaseUri}/favorites/get/${userId}`)
          .then(response => response.json())
          .then(data => setFavorites(data))
          .catch(error => console.error("Error fetching user favorites list:", error));
      } else {
        console.error('Failed to add item:', response.statusText);
      }
    } catch (error) {
      console.error('Error adding item:', error.message);
    }
  };

   const deleteItemFromList = async (serialNumber) => {
    // Set the item to delete and open the confirmation modal
    setItemToDelete(serialNumber);
    setConfirmOpen(true);
  };

  const handleConfirm = async () => {
    try {
      const response = await fetch(`${BaseUri}/favorites/delete`, {
        method: "DELETE",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId: userId, productSN: itemToDelete })
      });

      if (response.ok) {
        const data = await response.text();
        console.log(data);
        // Re-fetch list
        fetch(`${BaseUri}/favorites/get/${userId}`)
          .then(response => response.json())
          .then(data => setFavorites(data))
          .catch(error => console.error("Error fetching user favorites list:", error));
      } else {
        console.error('Item not found or deletion failed:', response.statusText);
      }
    } catch (error) {
      console.error('Error deleting:', error.message);
    } finally {
      // Close the confirmation modal
      setConfirmOpen(false);
      setItemToDelete(null);
    }
  };
  const fetchSortedList = async () => {
    try {
      const response = await fetch(`${BaseUri}/favorites/get-sorted/${userId}`, {
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
    setConfirmOpen(false);
  };

  return (
    // <button className='mainbutton1' onClick={fetchSortedList} >Sort</button>
      <div className='yourorders'>
        <thead className="yourorderstable2">
            <tr>
              <th scope='col'>Product</th>
              <th scope='col'>Serial Number</th>
              <th scope='col'>Name</th>
              <th scope='col'>Price</th>
              <th scope='col'>Action</th>
            </tr>
          </thead>
      <div className="scrollable-container">
        <table className='yourorderstable'>
          <tbody>
            {favorites.map(item => (
              <tr key={item.productSN}>
                <td data-label='Image'>
                  <img src={item.productPhoto} alt={`Product ${item.productSN}`} style={{ width: '50px', height: '50px' }} />
                </td>
                <td data-label='SerialNumber'>{item.productSN}</td>
                <td data-label='Name'>{item.productName}</td>
                <td data-label='Price'>${item.productPrice}</td>
                <td data-label='Action'>
                  <button className='mainbutton1' onClick={() => deleteItemFromList(item.productSN)}>Remove From Favorites</button>
                </td>
              </tr>
            ))}
          </tbody>
          <ConfirmAlert
            message="Are you sure you want to remove this item from the list?"
            onConfirm={handleConfirm}
            onCancel={handleCancel}
            open={isConfirmOpen}
          />
        </table>
      </div>
    </div>
  );
};

export default FavoritesList;
