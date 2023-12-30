import React, { useState } from "react";
import { Link } from "react-router-dom";
import { AppBar, Toolbar, Button, Typography } from "@mui/material";
import ListAdmins from "./user_promotion/ListAdmins";
import ListUsers from "./user_promotion/ListUsers";

const AdminNav = ({userId = 1}) => {
  const [showAdminList, setShowAdminList] = useState(false);

  const handleToggleAdminList = () => {
    setShowAdminList(true);
  };

  const handleToggleUserList = () => {
    setShowAdminList(false);
  };

  return (
    <div>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Admin Navigation Bar
          </Typography>
          <div className="button-container">
            <Button
              color="inherit"
              variant="contained"
              onClick={handleToggleUserList}
              style={{ marginRight: '8px', backgroundColor: '#4CAF50', color: 'white' }}
            >
              Show Users List
            </Button>
            <Button
              color="inherit"
              variant="contained"
              onClick={handleToggleAdminList}
              style={{ backgroundColor: '#2196F3', color: 'white' }}
            >
              Show Admins List
            </Button>
          </div>
        </Toolbar>
      </AppBar>
      {showAdminList ? <ListAdmins userId={userId} /> : <ListUsers userId={userId} />}
    </div>
  );
};

export default AdminNav;
