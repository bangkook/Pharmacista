import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import GoogleSignIn from './components/login';
import GoogleSignUp from './components/googleSignUp';
import LoginBasic from './components/LoginBasic';
import Signup from './components/signup';
import AccountSettings from './components/user_profile/UserProfile'
import ListUsers from './components/user_promotion/ListUsers';
import ListAdmins from './components/user_promotion/ListAdmins';
import AdminNav from './components/AdminNav';

function App() {
  return (
    // <Router>
    //   <div className='App'>
    //     <Routes>
    //       <Route path='/googleLogin' element={<GoogleSignIn/>} />
    //       <Route path='/googleSignUp' element={<GoogleSignUp/>} />
    //       <Route path='/LoginBasic' element={<LoginBasic/>} />
    //       <Route path='/signup' element={<Signup/>} />
    //       <Route path='/Account' element={<AccountSettings/>} />
    //     </Routes>
    //   </div>
    // </Router>

    // -separator
    // <div className='App'>
      <Router>
        <div className='App'>
        
          <Routes>
            <Route path="/admin" element={<AdminNav />} />
            <Route path="/4/findUsers" element={<ListUsers />} />
            <Route path="/4/findAdmins" element={<ListAdmins />} />
          </Routes>
        </div>
      </Router>

  );
}

export default App;