import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, useNavigate} from 'react-router-dom';
import GoogleSignIn from './components/login';
import GoogleSignUp from './components/googleSignUp';
import LoginBasic from './components/LoginBasic';
import Signup from './components/signup';
import UserProfile from './components/user_profile/UserProfile'

const BaseUri = 'http://localhost:8088/user'
import ListUsers from './components/user_promotion/ListUsers';
import ListAdmins from './components/user_promotion/ListAdmins';
import AdminNav from './components/AdminNav';

function App() {
  const [currentUser, setCurrrentUser] = useState(null);
  const navigate = useNavigate();

  const handleSuccessfulLogin = async (username) => {
      console.log(username)
      try {
        const response = await fetch(`${BaseUri}/get-user-by-username/${username}`)
      const userData = await response.json()
      console.log(userData)

      if (response.ok) {
        setCurrrentUser(userData)
        navigate("/userProfile")
        alert('User data retrieved successfully');
      } else {
        alert('Error: Failed to retrieve user');
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
          <Route path="/admin" element={<AdminNav />} />
          <Route path="/4/findUsers" element={<ListUsers />} />
          <Route path="/4/findAdmins" element={<ListAdmins />} />
        </Routes>
      </div>
  );
}

export default App;