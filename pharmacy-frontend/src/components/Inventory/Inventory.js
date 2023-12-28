import React, { useState, useEffect } from 'react';
import './MedicineInventory.css'; // Import your CSS file
import CustomAlert from '../Alert/CustomAlert';
import ConfirmAlert from '../Alert/ConfirmAlert';

const initialFormData = {
  name: '',
  quantity: '',
  serialNumber: '',
  price: '',
  productionDate: '',
  expiryDate: '',
  description: '',
  photo: '',
};



const MedicineInventory = () => {
  const [medicines, setMedicines] = useState([]);
  const [formData, setFormData] = useState(initialFormData);
  const [editingId, setEditingId] = useState(null);
  const [isModalOpen, setModalOpen] = useState(false);
  const [customAlert, setCustomAlert] = useState(null);

  const showAlert = (message) => {
    return setCustomAlert(<CustomAlert message={message} onClose={() => setCustomAlert(null)} />);
  };
  

  useEffect(() => {
    fetchMedicines();
  }, []);

  const fetchMedicines = async () => {
    try {
      const response = await fetch('http://localhost:8088/products');
      const data = await response.json();
      setMedicines(data);
    } catch (error) {
      console.error('Error fetching medicines:', error);
    }
  };

  const openModal = () => {
    console.log("model opened")
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setFormData(initialFormData);
    setEditingId(null);
  };

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const addMedicine = async () => {
    if (!formData.name || !formData.quantity || !formData.serialNumber || !formData.price || !formData.productionDate || !formData.expiryDate ) {
        showAlert('All fields are required.');
        return;
    }
  
    try {
        const response = await fetch('http://localhost:8088/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                ...formData,
                // Convert the image to a string (data URL)
                photo: formData.photo.toString(),
            }),
        });

        if (response.ok) {
            fetchMedicines();
            closeModal();
            setTimeout(() => {
              showAlert('Medicine added successfully.');
            }, 100);
        } else {
            const errorText = await response.text();
            showAlert(`Failed to add medicine. Error: ${errorText}`);
        }
    } catch (error) {
        console.error('Error adding medicine:', error);
    }
};

  

const [isConfirmOpen, setConfirmOpen] = useState(false);
  const [deleteSerialNumber, setDeleteSerialNumber] = useState(null);

  const deleteMedicine = async (serialNumber) => {
    // Set the serialNumber to delete and open the confirmation modal
    setDeleteSerialNumber(serialNumber);
    setConfirmOpen(true);
  };

  const handleConfirm = async () => {
    try {
      const response = await fetch(`http://localhost:8088/products/${deleteSerialNumber}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        fetchMedicines();
        showAlert('Medicine deleted successfully.');
      } else {
        showAlert('Failed to delete medicine.');
      }
    } catch (error) {
      console.error('Error deleting medicine:', error);
    } finally {
      // Close the confirmation modal
      setConfirmOpen(false);
    }
  };

  const handleCancel = () => {
    // Close the confirmation modal without deleting
    setConfirmOpen(false);
  };


  const editMedicine = (medicine) => {
    setEditingId(medicine.serialNumber);
    setFormData(medicine);
    openModal();
  };

  const updateMedicine = async () => {
    try {
        console.log(JSON.stringify(formData))
      const response = await fetch(`http://localhost:8088/products/${editingId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        fetchMedicines();
        closeModal();
        setTimeout(() => {
          showAlert('Medicine Updated successfully.');
        }, 100);
      } else {
        const errorText = await response.text();
        showAlert(`Failed to update medicine. Error: ${errorText}`);
      }
    } catch (error) {
      console.error('Error updating medicine:', error);
    }
};


  return (
    <div className="medicine-inventory-container">
      <h1>Medicine Inventory</h1>

      <table className="medicine-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Quantity</th>
            <th>Serial Number</th>
            <th>Price</th>
            <th>Production Date</th>
            <th>Expiry Date</th>
            <th>Description</th>
            <th>Photo</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {medicines.map((medicine) => (
            <tr key={medicine.serialNumber}>
              <td>{medicine.name}</td>
              <td>{medicine.quantity}</td>
              <td>{medicine.serialNumber}</td>
              <td>{medicine.price}</td>
              <td>{medicine.productionDate}</td>
              <td>{medicine.expiryDate}</td>
              <td>{medicine.description}</td>
              <td>
                {medicine.photo && (
                  <img src={medicine.photo} alt="Medicine" style={{ width: '50px' }} />
                )}
              </td>
              <td>
                <button className="edit-btn" onClick={() => editMedicine(medicine)}>
                  Edit
                </button>
                <button className="delete-btn" onClick={() => deleteMedicine(medicine.serialNumber)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="add-update-form">
        <button className="add-btn" type="button" onClick={openModal}>
          Add Medicine
        </button>
      </div>

      {isModalOpen && (
        <div className="modal-overlay">
          <div className="addMedicineModal modal">
            <h2>{editingId ? 'Update Medicine' : 'Add Medicine'}</h2>
            <form>
              <label>
                Medicine Name:
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                Quantity:
                <input
                  type="number"
                  name="quantity"
                  value={formData.quantity}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                Serial Number:
                <input
                  type="text"
                  name="serialNumber"
                  value={formData.serialNumber}
                  onChange={handleInputChange}
                  readOnly={!!editingId}
                />
              </label>

              <label>
                Price:
                <input
                  type="number"
                  name="price"
                  value={formData.price}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                Production Date:
                <input
                  type="date"
                  name="productionDate"
                  value={formData.productionDate}
                  onChange={handleInputChange}
                />
              </label>

              <label>
                Expiry Date:
                <input
                  type="date"
                  name="expiryDate"
                  value={formData.expiryDate}
                  onChange={handleInputChange}
                />
              </label>

              <label>
            Description<span className="optional-text">(optional)</span>:
            <textarea
                name="description"
                value={formData.description}
                onChange={handleInputChange}
                placeholder="(optional)"
            />
        </label>


              <label>
                Photo:
                <input
                  type="file"
                  name="photo"
                  accept="image/*"
                  onChange={(e) => {
                    const file = e.target.files[0];
                    const reader = new FileReader();

                    reader.onloadend = () => {
                      setFormData({
                        ...formData,
                        photo: reader.result,
                      });
                    };

                    if (file) {
                      reader.readAsDataURL(file);
                    }
                  }}
                />
              </label>

              <div className="button-container">
                <button
                  className="action-btn"
                  type="button"
                  onClick={editingId ? updateMedicine : addMedicine}
                >
                  {editingId ? 'Update Medicine' : 'Add Medicine'}
                </button>
                <button className="cancel-btn" type="button" onClick={closeModal}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
       <ConfirmAlert
        message="Are you sure you want to delete this medicine?"
        onConfirm={handleConfirm}
        onCancel={handleCancel}
        open={isConfirmOpen}
      />
      {customAlert}
    </div>
  );
};

export default MedicineInventory;