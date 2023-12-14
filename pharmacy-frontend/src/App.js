import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import GoogleSignIn from './components/login';
import GoogleSignUp from './components/googleSignUp';
import LoginBasic from './components/LoginBasic';
import Signup from './components/signup';
import UserProfile from './components/user_profile/UserProfile'
import Home from './components/home';

const BaseUri = 'http://localhost:8088/user'

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
        alert('User data retrieved successfully');
        navigate("/userProfile")
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
          <Route path='/home' element={<Home/>} />
          <Route path="/admin" element={<AdminNav />} />
          <Route path="/4/findUsers" element={<ListUsers />} />
          <Route path="/4/findAdmins" element={<ListAdmins />} />
          {1 && <Route path='/userProfile' element={<UserProfile userId={1}/>} />}
        </Routes>
      </div>
  );
}

export default App;