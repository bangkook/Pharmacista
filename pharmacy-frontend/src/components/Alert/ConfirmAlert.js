// ConfirmAlert.js
import React from 'react';
import { Button, Dialog, Paper, Typography } from '@mui/material';

const ConfirmAlert = ({ message, onConfirm, onCancel, open }) => {
  const handleOk = () => {
    // Make sure onConfirm is a function before calling it
    if (typeof onConfirm === 'function') {
      onConfirm();
    }

    // Close the modal
    handleClose();
  };

  const handleCancel = () => {
    // Make sure onCancel is a function before calling it
    if (typeof onCancel === 'function') {
      onCancel();
    }

    // Close the modal
    handleClose();
  };

  const handleClose = () => {
    // Close the modal
    onCancel();
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="xs" fullWidth>
      <Paper
        elevation={3}
        style={{
          padding: '20px',
          borderRadius: '8px',
          boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
          position: 'relative',
          zIndex: 9999,
        }}
      >
        <Typography variant="body1">{message}</Typography>
        <Button onClick={handleOk} variant="contained" color="primary" style={{ marginTop: '10px' }}>
          OK
        </Button>
        <Button onClick={handleCancel} variant="contained" color="secondary" style={{ marginTop: '10px', marginLeft: '10px' }}>
          Cancel
        </Button>
      </Paper>
    </Dialog>
  );
};

export default ConfirmAlert;
