import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import GoogleSignIn from './components/login';
import GoogleSignUp from './components/googleSignUp';
import LoginBasic from './components/LoginBasic';
import Signup from './components/signup';
import AccountSettings from './components/user_profile/UserProfile'

function App() {
  return (
    <Router>
      <div className='App'>
        <Routes>
          <Route path='/googleLogin' element={<GoogleSignIn/>} />
          <Route path='/googleSignUp' element={<GoogleSignUp/>} />
          <Route path='/LoginBasic' element={<LoginBasic/>} />
          <Route path='/signup' element={<Signup/>} />
          <Route path='/Account' element={<AccountSettings/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;