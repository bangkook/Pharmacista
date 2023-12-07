// Front-end component (e.g., GoogleSignUp.js)
import React from 'react';

export default function Inventory() {

  return (
    <div className="center-container">
      <button type="button" className="google-sign-in-button" onClick={SignUp}>
        Sign up with Google
      </button>
    </div>
  );
}