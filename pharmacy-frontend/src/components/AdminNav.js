import React from "react";

import { Link } from "react-router-dom";
import ListAdmins from "./user_promotion/ListAdmins";
import ListUsers from "./user_promotion/ListUsers";
import { Button } from "@mui/material";

const AdminNav = ({adminId = 4}) => {
    return (
      <div className="admin-nav">
        <h1>Admin Navigation Bar</h1>
        <div className="button-container">
          <Link to="/getUsers" element={<ListUsers/>}>
            <Button className="button" variant="contained">Get Users List</Button>
          </Link>
          <Link to="/getAdmins" element={<ListAdmins/>}>
            <Button className="button" variant="contained">Get Admins List</Button>
          </Link>
        </div>
      </div>
    );
  };

export default AdminNav;