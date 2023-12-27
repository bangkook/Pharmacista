// CustomAlert.js
import React, { useState } from 'react';
import { Button, Dialog, DialogContent, Paper, Typography } from '@mui/material';

const CustomAlert = ({ message, onClose }) => {
    const [isVisible, setIsVisible] = useState(true);
    
  
    const handleClose = () => {
      // This alert will help you verify if the function is called
      setIsVisible(false); // Fix this line to hide the alert
      onClose();
    };
  
    return (
      <>
      <Dialog open={isVisible} onClose={handleClose} maxWidth="xs" fullWidth>

          <Paper
            elevation={3}
            style={{
              padding: '20px',
              borderRadius: '8px',
              boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
              position: 'relative', // Adjust position
              zIndex: 9999, // Ensure the alert is above the overlay
            }}
          >
            <Typography variant="body1">{message}</Typography>
            <Button onClick={handleClose} variant="contained" color="primary" style={{ marginTop: '10px' }}>
              Close
            </Button>
          </Paper>
   
      </Dialog>
      </>
    );
  };
  

export default CustomAlert;
