// Front-end component (e.g., GoogleSignUp.js)
import React from 'react';
import { useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';

export default function GoogleSignUp() {
  const handleGoogleLogin = async (response) => {
    try {
      const userInfoResponse = await axios.get('https://www.googleapis.com/oauth2/v3/userinfo', {
        headers: {
          Authorization: `Bearer ${response.access_token}`,
        },
      });

      const userInfo = {
        email: userInfoResponse.data.email,
        picture: userInfoResponse.data.picture,
      };

      // Send the user information to the server
      await sendUserInfoToServer(userInfo);

      console.log('User information:', userInfoResponse.data);
    } catch (error) {
      console.error('Error fetching user information:', error);
    }
  };

  const sendUserInfoToServer = async (userInfo) => {
    try {
        const serverResponse = await axios.post('http://localhost:8088/login/oauth2/', userInfo);
        console.log('Server response:', serverResponse.data);
    } catch (error) {
        console.error('Error sending user information to server:', error);
    }
    
};

  const login = useGoogleLogin({
    onSuccess: handleGoogleLogin,
  });

  return (
    <div className="center-container">
      <button type="button" className="google-sign-in-button" onClick={login}>
        Sign up with Google
      </button>
    </div>
  );
}