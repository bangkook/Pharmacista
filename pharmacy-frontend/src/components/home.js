import React, { useState } from 'react';
import { ViewModule, ShoppingCart, Assignment, PeopleAlt, Storage,Favorite, ExitToApp  } from '@mui/icons-material';
import { Button, Avatar, List, ListItem, ListItemIcon, ListItemText, Typography } from '@mui/material';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import './home.css';
import UserProfile from './user_profile/UserProfile';
import OrderList from './orders_list/OrderList';
import Cart from './cart';
import Inventory from './Inventory/Inventory';
import MedicinesList from './listOfMediciens';
import AdminNav from './AdminNav'
import FavoritesList from './favorites_list/FavoritesList';
// import Login from './LoginBasic'

const Home = ({ userId = 1, isAdmin = false }) => {
  const [activePage, setActivePage] = useState('View Profile');

  const pharmacistaImage = 'https://cdn-icons-png.flaticon.com/512/4320/4320337.png';
  
  const handleLogout = () => {
    // Implement your logout logic here
    console.log("User logged out");
    window.location.href = '/LoginBasic';
  };


  const menuItemsAdmin = [
    {
      text: 'View Profile',
      icon: <AccountCircleIcon />,
    },
    {
      text: 'Medicines List',
      icon: <ViewModule />,
    },
    {
      text: 'View Cart',
      icon: <ShoppingCart />,
    },
    {
      text: 'View Orders',
      icon: <Assignment />,
    },
    {
      text: 'Manage Users',
      icon: <PeopleAlt />,
    },
    {
      text: 'Manage Inventory',
      icon: <Storage />,
    },
    {
      text: 'Favorites List',
      icon: <Favorite />,
    }
  ];

  const menuItemsUser = [
    {
      text: 'View Profile',
      icon: <AccountCircleIcon />,
    },
    {
      text: 'Medicines List',
      icon: <ViewModule />,
    },
    {
      text: 'View Cart',
      icon: <ShoppingCart />,
    },
    {
      text: 'View Orders',
      icon: <Assignment />,
    },
    {
      text: 'Favorites List',
      icon: <Favorite />,
    }
  ];

  const logoutItem = {
    text: 'Log Out',
    icon: <ExitToApp />,
    onClick: handleLogout,
  };
  
  var viewedList = ''
  if(isAdmin == false) {
    viewedList = menuItemsUser
  }else{
    viewedList = menuItemsAdmin
  }

  viewedList.push(logoutItem);
  const renderComponent = () => {
    switch (activePage) {
      case 'View Profile':
        return <UserProfile userId={userId} />;
      case 'View Cart':
        return <Cart userId={userId} />;
      case 'View Orders':
        return <OrderList userId={userId} admin = {isAdmin} />;
      case 'Manage Users':
        return <AdminNav userId={userId} />;
      case 'Manage Inventory':
        return <Inventory />;
      case 'Medicines List':
        return <MedicinesList userId={userId}/>;
      case 'Favorites List':
        return <FavoritesList userId={userId}/>;
      case 'Log Out':
        return handleLogout();
      default:
        return null;
    }
  };

  return (
    <div className="home">
      <div className="homein">
        <div className="left">
          <div className="usersidebar">
            <div className="flex-container">
              <div className="img-wrap">
                <Avatar alt="Pharmacista" src={pharmacistaImage} sx={{ width: 50, height: 50 }} />
              </div>
              <div className="separator"></div>

              <Typography variant="h6" className="name">
                Pharmacista
              </Typography>
              
            </div>
            <hr className="line" />
            {viewedList.map((item, index) => (
                  <div key={item.text}>
                    <Button
                      variant="text"
                      onClick={() => setActivePage(item.text)}
                      style={{
                        width: '100%',
                        backgroundColor: activePage === item.text ? 'lightblue' : 'transparent',
                        fontWeight: activePage === item.text ? 'bold' : 'normal',
                        position: 'static',
                        left: 'auto',
                      }}
                    >
                      <ListItem
                        button
                        style={{
                          color: item.text === 'Log Out' ? 'red' : 'black',
                          borderTop: item.text === 'Log Out' ? '1px solid grey' : 'none',
                        }}
                      >
                        <ListItemIcon>{item.icon}</ListItemIcon>
                        <ListItemText primary={item.text} />
                      </ListItem>
                    </Button>
                  </div>
                ))}
              
          </div>
        </div>
        <div className="right">{renderComponent()}</div>
      </div>
    </div>
  );
};
export default Home;