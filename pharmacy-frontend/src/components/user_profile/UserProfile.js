import './UserProfile.css'
import React,{ useState, useRef, useEffect, useMemo} from 'react';
import Select from 'react-select'
import countryList from 'react-select-country-list'

const BaseUri = 'http://localhost:8088/user'
const AccountSettings = ({userId}) => {
  const [country, setCountry] = useState('')
  const options = useMemo(() => countryList().getData(), [])

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
        const response = await fetch(`${BaseUri}/get-user/${userId}`);
        if (response.ok) {
          const userData = await response.json();
          console.log(userData)
          setFormData(userData);
          setCountry({'label': userData.country, 'value': countryList().getValue(userData.country)})
        } else {
          console.error('Failed to fetch user data');
        }
      } catch (error) {
        console.error('Error fetching user data:', error);
      }
    };

    fetchUserData();
  }, [userId]); // The empty dependency array ensures that this effect runs only once when the component mounts

  const validateForm = () => {
    let isValid = true;
    const errors = {};
  
    // Check if any field is empty or input is invalid
    Object.entries(formData).forEach(([key, value]) => {
      if (key !== 'username' && value === '') {
        isValid = false;
        errors[key] = 'This field is required';
      } else if(!validate(key, value)){
        isValid = false;
        let msg = ''
        if(key === 'zipCode')
          msg = 'This field should contain only 3-5 digits'
        else if(key === 'phoneNumber')
          msg = 'This field should contain only 11 digits'
        else if(key === 'city')
          msg = 'This field should contain only alphabetic characters'
        errors[key] = msg;
      }

    });
  
    setFormErrors(errors);
    return isValid;
  };

  const validate = (key, value) => {
    if(key === 'phoneNumber')
     return /^\d{11}$/.test(value);
    if(key === 'zipCode')
     return /^\d{3,5}$/.test(value);
    if(key === 'city')
      return /^[a-zA-Z]+$/.test(value) 
    return true
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleCountryChange = (value) => {
    setCountry(value)
    setFormData((prevData) => ({
      ...prevData,
      ['country']: value.label,
    }));
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Validate the form before submitting
    if (!validateForm()) {
      return;
    }

    console.log(JSON.stringify(formData))
    try {
      const response = await fetch(`${BaseUri}/update-data/${userId}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      
      if (response.ok) {
        alert('User data updated successfully');
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
          <Select className='input'
            name='country' 
            id='country' 
            options={options} 
            value={country} 
            onChange={handleCountryChange}
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
  

const ChangePassword = ({userId}) => {
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
      let valid = /^.{8,16}$/.test(value)
      if (!value.trim() || !valid) {
        isValid = false;
        errors[key] = 'This field must contain 8-16 characters';
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
      const response = await fetch(`${BaseUri}/change-password/${userId}?` + new URLSearchParams({
        currentPassword: formData.curpass,
        newPassword: formData.newpass
      }), {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        }
        
      });
      setFormData({cnewpass:'', curpass: '', newpass: ''})
      if (response.ok) {
        window.alert('Password changed successfully');
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
      <form className='form' id='form'>
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

        <button className='mainbutton1' style = {{backgroundColor: isPasswordMasked? 'green': 'grey'}} type="button" onClick={handleToggleMask}>
              {isPasswordMasked ? 'Show' : 'Hide'}
        </button>
        <button className='mainbutton1' type="submit" value="Submit" onClick={handleSubmit}>
          Save Changes
        </button>
      </form>
    </div>
  );
};

const ProfilePictureUploader = ({userId}) => {
  const [imageUrl, setImageUrl] = useState(null);
  const fileInputRef = useRef(null);
  const [isHovered, setIsHovered] = useState(false);

  const handleImageClick = () => {
    // Trigger the click event of the hidden file input
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleUpload = async (event) => {
    // Display the selected image on the screen
    const reader = new FileReader();
    reader.onloadend = (e) => {
      setImageUrl(e.target.result);
    };
    reader.readAsDataURL(event.target.files[0]);
  };

  return (
    <div className="img-wrap">
      <input type="file" onChange={handleUpload} ref={fileInputRef}
        style={{ display: 'none' }} />

      <img src={imageUrl || 'https://github.com/OlgaKoplik/CodePen/blob/master/profile.jpg?raw=true'} 
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

const UserProfile = ({userId = 1}) => {
  return (
    <div className='userprofile'>
      <SingleBanner 
        heading={`My Profile`}
        bannerimage = 'https://images.unsplash.com/photo-1607619056574-7b8d3ee536b2?q=80&w=1880&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D' 
        />
    
        <div className='userprofilein'>
          <div className='left'>
            <AccountSettings userId={userId}/>
            <ChangePassword userId={userId}/>
          </div>
          <div className='right'>
          <ProfilePictureUploader userId={userId}/>
          </div>
        </div>
        
        </div>
  )
}

export default UserProfile