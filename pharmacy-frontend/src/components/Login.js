import React from 'react';
import '../App';
import { useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';


export default function GoogleSignIn() {
  const handleGoogleLogin = async (response) => {
    try {
      const userInfoResponse = await axios.get('https://www.googleapis.com/oauth2/v3/userinfo', {
        headers: {
          Authorization: `Bearer ${response.access_token}`,
        },
      });

      const email = userInfoResponse.data.email;

      // Send the user information to the server
      await sendUserInfoToServer(email);

      console.log('User information:', userInfoResponse.data);
    } catch (error) {
      console.error('Error fetching user information:', error);
    }
  };

  const sendUserInfoToServer = async (email) => {
    try {
      const serverResponse = await axios.get(`http://localhost:8088/user/login?email=${encodeURIComponent(email)}`);
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
        Sign in with Google
      </button>
    </div>
  );
}
