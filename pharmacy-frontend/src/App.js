import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, useNavigate} from 'react-router-dom';
import GoogleSignIn from './components/login';
import GoogleSignUp from './components/googleSignUp';
import LoginBasic from './components/LoginBasic';
import Signup from './components/signup';
import UserProfile from './components/user_profile/UserProfile'
import Home from './components/home';
import ListUsers from './components/user_promotion/ListUsers';
import ListAdmins from './components/user_promotion/ListAdmins';
import AdminNav from './components/AdminNav';
import ShoppingCart from './components/cart';
import Orders from './components/orders_list/OrderList';
import MedicineInventory from './components/Inventory/Inventory'
import CustomAlert from './components/Alert/CustomAlert';

const BaseUri = 'http://localhost:8088/user'


function App() {
  const [username, setUserName] = useState('')
  const [currentUser, setCurrrentUser] = useState(null);
  const [customAlert, setCustomAlert] = useState(null);
  const showAlert = (message) => {
    return setCustomAlert(<CustomAlert message={message} onClose={() => setCustomAlert(null)} />);
  };

  const navigate = useNavigate();

  const handleSuccessfulLogin = async (username) => {
      console.log(username)
      try {
        const response = await fetch(`${BaseUri}/get-user-by-username/${username}`)
        const userData = await response.json()
        console.log(userData)

      if (response.ok) {
        setCurrrentUser(userData)
        navigate("/home")
        //alert('User data retrieved successfully');
      } else {
        showAlert('Error: Failed to retrieve user');
      }
    } catch (error) {
      console.error('Error sending data to the backend:', error);
      alert('Error:', error.message);
    }
  };
  
  return (
      <div className='App'>
        <Routes>
          <Route path='/' element={<Signup handleSuccessfulLogin={handleSuccessfulLogin}/>} />
          <Route path='/googleLogin' element={<GoogleSignIn handleSuccessfulLogin={handleSuccessfulLogin}/>} />
          <Route path='/googleSignUp' element={<GoogleSignUp handleSuccessfulLogin={handleSuccessfulLogin}/>} />
          <Route path='/LoginBasic' element={<LoginBasic handleSuccessfulLogin={handleSuccessfulLogin}/>} />
          <Route path='/signup' element={<Signup handleSuccessfulLogin={handleSuccessfulLogin}/>} />
          {currentUser && <Route path='/userProfile' element={<UserProfile userId={currentUser.id}/>} />}
          {currentUser && <Route path="/admin" element={<AdminNav adminId={currentUser.id} />} />}
          {currentUser && <Route path="/getUsers" element={<ListUsers />} />}
          {currentUser && <Route path="/getAdmins" element={<ListAdmins />} />}
          {currentUser && <Route path='/home' element={<Home userId={currentUser.id} isAdmin={currentUser.role === "ADMIN"}/>}></Route>}
          {currentUser && <Route path="/cart" element={<ShoppingCart userId={currentUser.id}/>}></Route>}
          {currentUser && <Route path="/orders" element={<Orders userId={currentUser.id} admin={currentUser.role === "ADMIN"}/>}></Route>}
          <Route path="/inventory" element={<MedicineInventory/>}></Route>
        </Routes>
        {customAlert}
      </div>
  );
}

export default App;

// import React from 'react';
// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import GoogleSignIn from './components/login';
// import GoogleSignUp from './components/googleSignUp';
// import LoginBasic from './components/LoginBasic';
// import Signup from './components/signup';
// import AccountSettings from './components/user_profile/UserProfile';
// import ListOfMediciens from './components/listOfMediciens';
// import MedicineInventory from './components/Inventory/Inventory';

// function App() {
//   return (
//     <div className='App'>
//     <MedicineInventory/>
//     </div>
//   );
// }

// export default App;