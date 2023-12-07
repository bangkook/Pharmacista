import React, { useState } from 'react';
import './MedicineInventory.css'; // Import your CSS file

const initialFormData = {
  medicineName: '',
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

  const openModal = () => {
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

  const addMedicine = () => {
    if (!formData.medicineName || !formData.quantity) {
      alert('Medicine Name and Quantity are required fields.');
      return;
    }

    const newMedicine = {
      id: Date.now(),
      ...formData,
    };

    setMedicines([...medicines, newMedicine]);
    closeModal();
  };

  const deleteMedicine = (id) => {
    setMedicines(medicines.filter((medicine) => medicine.id !== id));
  };

  const editMedicine = (medicine) => {
    setEditingId(medicine.id);
    setFormData(medicine);
    openModal();
  };

  const updateMedicine = () => {
    const updatedMedicines = medicines.map((medicine) =>
      medicine.id === editingId ? { ...formData, id: editingId } : medicine
    );

    setMedicines(updatedMedicines);
    closeModal();
  };

  return (
    <div className="medicine-inventory-container">
      {/* Navigation Bar */}
      <nav className="navbar">
        <div className="nav-item">Inventory</div>
        <div className="nav-item">Profile</div>
      </nav>

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
            <tr key={medicine.id}>
              <td>{medicine.medicineName}</td>
              <td>{medicine.quantity}</td>
              <td>{medicine.serialNumber}</td>
              <td>{medicine.price}</td>
              <td>{medicine.productionDate}</td>
              <td>{medicine.expiryDate}</td>
              <td>{medicine.description}</td>
              <td>
                {medicine.photo && <img src={medicine.photo} alt="Medicine" style={{ width: '50px' }} />}
              </td>
              <td>
                <button className="edit-btn" onClick={() => editMedicine(medicine)}>
                  Edit
                </button>
                <button className="delete-btn" onClick={() => deleteMedicine(medicine.id)}>
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
                  name="medicineName"
                  value={formData.medicineName}
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
                Description:
                <textarea
                  name="description"
                  value={formData.description}
                  onChange={handleInputChange}
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
    </div>
  );
};

export default MedicineInventory;
