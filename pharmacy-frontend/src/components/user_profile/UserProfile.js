import './UserProfile.css'
import React,{ useState, useRef, useEffect} from 'react';
import axios from 'axios';

const AccountSettings = () => {
  const [formData, setFormData] = useState({
    username: '',
    phoneNumber: '',
    streetAddress: '',
    city: '',
    country: '',
    zipCode: '',
  });

  const [formErrors, setFormErrors] = useState({
    phoneNumber: '',
    streetAddress: '',
    city: '',
    country: '',
    zipCode: '',
  });

  useEffect(() => {
    // Fetch user data from the backend when the component mounts
    const fetchUserData = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/user/get-user/1'); // Update the endpoint to match your backend
        if (response.ok) {
          const userData = await response.json();
          console.log(userData)
          setFormData(userData); // Assuming the response body structure matches the state structure
        } else {
          console.error('Failed to fetch user data');
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();
  }, []); // The empty dependency array ensures that this effect runs only once when the component mounts

  const validateForm = () => {
    let isValid = true;
    const errors = {};
  
    // Check if any field is empty
    Object.entries(formData).forEach(([key, value]) => {
      if (key !== 'username' && value == '') {
        isValid = false;
        errors[key] = 'This field is required';
      }
    });
  
    setFormErrors(errors);
    return isValid;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    // Validate the form before submitting
    if (!validateForm()) {
      return;
    }

    console.log(JSON.stringify(formData))
    try {
      const response = await fetch('http://localhost:8081/api/user/update-data/1', {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      
      if (response.ok) {
        const result = response.body;
        alert('Backend Response:', result || 'Success');
      } else {
        alert('Error: Failed to update');
      }
    } catch (error) {
      console.error('Error sending data to the backend:', error);
      alert('Error:', error.message);
    }
  };
  return (
    <div className='accountsettings'>
      <h1 className='mainhead1'>Personal Information</h1>
      <hr></hr>
      <form className='form'>
        <div className='form-group'>
          <label htmlFor='username'>Username <span>*</span></label>
          <input
            type='text'
            name='username'
            id='name'
            value={formData.username}
            readOnly
          />
        </div>

        <div className='form-group'>
          <label htmlFor='phoneNumber'>Phone/Mobile <span>*</span></label>
          <input
            type='text'
            name='phoneNumber'
            id='phone'
            value={formData.phoneNumber}
            onChange={handleChange}
          />
          <span className='error'>{formErrors.phoneNumber}</span>
        </div>

        <div className='form-group'>
          <label htmlFor='streetAddress'>Street Address <span>*</span></label>
          <input
            type='text'
            name='streetAddress'
            id='saddr'
            value={formData.streetAddress}
            onChange={handleChange}
          />
          <span className='error'>{formErrors.streetAddress}</span>
        </div>

        <div className='form-group'>
          <label htmlFor='city'>City <span>*</span></label>
          <input
            type='text'
            name='city'
            id='city'
            value={formData.city}
            onChange={handleChange}
          />
          <span className='error'>{formErrors.city}</span>
        </div>

        <div className='form-group'>
          <label htmlFor='country'>Country <span>*</span></label>
          <input
            type='text'
            name='country'
            id='country'
            value={formData.country}
            onChange={handleChange}
          />
          <span className='error'>{formErrors.country}</span>
        </div>

        <div className='form-group'>
          <label htmlFor='zipCode'>Zip Code <span>*</span></label>
          <input
            type='text'
            name='zipCode'
            id='zipcode'
            value={formData.zipCode}
            onChange={handleChange}
          />
          <span className='error'>{formErrors.zipCode}</span>
        </div>

        <button className='mainbutton1' type='submit' onClick={handleSubmit}>
          Save Changes
        </button>
      </form>
    </div>
  );
};
  

const ChangePassword = () => {
  const [formData, setFormData] = useState({
    curpass: '',
    newpass: '',
    cnewpass: '',
  });

  const [isPasswordMasked, setIsPasswordMasked] = useState(true);
  const [formErrors, setFormErrors] = useState({
    curpass: '',
    newpass: '',
    cnewpass: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleToggleMask = () => {
    setIsPasswordMasked((prevIsMasked) => !prevIsMasked);
  };

  const validateForm = () => {
    let isValid = true;
    const errors = {};

    // Check for empty fields
    Object.entries(formData).forEach(([key, value]) => {
      if (!value.trim()) {
        isValid = false;
        errors[key] = 'This field is required';
      }
    });

    // Check if cnewpass equals newpass
    if (formData.newpass !== formData.cnewpass) {
      isValid = false;
      errors.cnewpass = 'Passwords do not match';
    }

    setFormErrors(errors);
    return isValid;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate the form before submitting
    if (!validateForm()) {
      return;
    }

    try {
      const response = await fetch('http://localhost:8081/api/user/change-password/1?' + new URLSearchParams({
        currentPassword: formData.curpass,
        newPassword: formData.newpass
      }), {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        }
        
      });

      console.log(response)
      if (response.ok) {
        const result = response.body;
        window.alert(result || 'Password changed successfully');
      } else {
        window.alert('Current password is wrong');
      }
    } catch (error) {
      console.error('Error changing password:', error);
      window.alert('An unexpected error occurred');
    }
  };

  return (
    <div className='accountsettings'>
      <h1 className='mainhead1'>Change Password</h1>
      <hr></hr>
      <form className='form' onSubmit={handleSubmit}>
        <div className='form-group'>
          <label htmlFor='curpass'>Current Password <span>*</span></label>
          <div className="password-input-container">
            <input
              type={isPasswordMasked ? 'password' : 'text'}
              name='curpass'
              value={formData.curpass}
              onChange={handleChange}
              required
            />
            <button type="button" onClick={handleToggleMask}>
              {isPasswordMasked ? 'Show' : 'Hide'}
            </button>
          </div>
          <span className='error'>{formErrors.curpass}</span>
        </div>

        <div className='form-group'>
          <label htmlFor='newpass'>New Password <span>*</span></label>
          <div className="password-input-container">
            <input
              type={isPasswordMasked ? 'password' : 'text'}
              name='newpass'
              value={formData.newpass}
              onChange={handleChange}
              required
            />
          </div>
          <span className='error'>{formErrors.newpass}</span>
        </div>

        <div className='form-group'>
          <label htmlFor='cnewpass'>Confirm New Password <span>*</span></label>
          <div className="password-input-container">
            <input
              type={isPasswordMasked ? 'password' : 'text'}
              name='cnewpass'
              value={formData.cnewpass}
              onChange={handleChange}
              required
            />
          </div>
          <span className='error'>{formErrors.cnewpass}</span>
        </div>

        <button className='mainbutton1' type='submit'>
          Save Changes
        </button>
      </form>
    </div>
  );
};

const ProfilePictureUploader = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [imageUrl, setImageUrl] = useState(null);
  const fileInputRef = useRef(null);
  const [isHovered, setIsHovered] = useState(false);

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);

    // Display the selected image on the screen
    const reader = new FileReader();
    reader.onload = (e) => {
      setImageUrl(e.target.result);
    };
    reader.readAsDataURL(event.target.files[0]);
  };

  const handleImageClick = () => {
    // Trigger the click event of the hidden file input
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleUpload = async (event) => {
    setSelectedFile(event.target.files[0]);
    // Display the selected image on the screen
    const reader = new FileReader();
    reader.onloadend = (e) => {
      setImageUrl(e.target.result);
    };
    reader.readAsDataURL(event.target.files[0]);

    try {
      // Create a FormData object to send the file
      const formData = new FormData();
      formData.append('file', selectedFile);
      console.log(formData)
      // Send the file to the backend
      const response = await axios.post('/api/user/upload-profile-picture/1', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      // Get the image URL from the backend response
      const { imageUrl } = response.data;

      // Set the image URL in the state
      setImageUrl(imageUrl);
    } catch (error) {
      console.error('Error uploading profile picture:', error);
    }
  };

  return (
    <div className="img-wrap">
      <input type="file" onChange={handleUpload} ref={fileInputRef}
        style={{ display: 'none' }} />
      <img for="photo-upload" src={imageUrl || 'https://github.com/OlgaKoplik/CodePen/blob/master/profile.jpg?raw=true'} 
      alt="Profile" onClick={handleImageClick}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
      style={{
        cursor: 'pointer',
        opacity: isHovered ? 0.7 : 1, // Change opacity on hover
      }}
      />
      {imageUrl}
    </div>
  );
};

const UserProfile = () => {
  return (
    <div className='userprofile'>

         <div className='userprofilein'>
            <div className='left'>
              <AccountSettings/>
              <ChangePassword/>
            </div>
            <div className='right'>
            <ProfilePictureUploader/>
            </div>
         </div>
        
        </div>
  )
}

export default UserProfile