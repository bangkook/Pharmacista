import React from "react";

import { Link } from "react-router-dom";
import ListAdmins from "./user_promotion/ListAdmins";
import ListUsers from "./user_promotion/ListUsers";
import { Button } from "@mui/material";
const AdminNav = () => {
    return (
      <div className="admin-nav">
        <h1>Admin Navigation Bar</h1>
        <div className="button-container">
          <Link to="/4/findUsers" element={<ListUsers/>}>
            <Button className="button" variant="contained">Manage Users</Button>
          </Link>
          <Link to="/4/findAdmins" element={<ListAdmins/>}>
            <Button className="button" variant="contained">View Admins</Button>
          </Link>
        </div>
      </div>
    );
  };

export default AdminNav;