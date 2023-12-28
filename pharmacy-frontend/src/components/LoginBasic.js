import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Grid, Paper,Avatar, TextField, Button, Typography} from "@mui/material";
import { useState } from "react";
import { Link } from 'react-router-dom'
import GoogleSignIn from './login';
import CustomAlert from './Alert/CustomAlert';

// TODO remove, this demo shouldn't need to reset the theme.

const defaultTheme = createTheme();

export default function LoginBasic ({handleSuccessfulLogin}) {

    const BaseUri = 'http://localhost:8088/user'
    const [userNameinput,setUserName]=useState('')
    const [passwordInput,setPassword]=useState('')
    const [customAlert, setCustomAlert] = useState(null);

    const showAlert = (message) => {
      return setCustomAlert(<CustomAlert message={message} onClose={() => setCustomAlert(null)} />);
    };
    

    const signInButton = async (e) => {
      e.preventDefault(); // Prevent the default form submission behavior
      if (userNameinput === "" || passwordInput === "") {
        showAlert("Please fill all required fields");
    }else{
        const response = await fetch(`${BaseUri}/checkUser?`+ new URLSearchParams({
            userName: userNameinput,
            password:passwordInput
        }))
        const data = await response.text()
        console.log(data)
        if(data==='USER_FOUND_CORRECT_PASSWORD'){
            const registeredUser = await fetch(`${BaseUri}/getUserByName?`+ new URLSearchParams({
                userName: userNameinput,
                password:passwordInput
            }))
            const userData = await registeredUser.json()
            console.log(userData)
            handleSuccessfulLogin(userNameinput)
        } else if (data === 'USER_FOUND_INCORRECT_PASSWORD') {
          showAlert("Wrong password");
        } else if (data === 'USER_NOT_FOUND') {
          showAlert("You don't have an account");
        } else if (data === 'INVALID_INPUT') {
          showAlert("Invalid input");
        }
    }
}
return (
    <ThemeProvider theme={defaultTheme}>
      <Grid container component="main" sx={{ height: '100vh' }}>
        <CssBaseline />
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            backgroundImage: 'url(https://www.pharmacyplanet.com/media/wysiwyg/is-online-pharmacy-safe-for-buying-erectile-dysfunction-medications.jpeg)',
            backgroundRepeat: 'no-repeat',
            backgroundColor: (t) =>
              t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
            backgroundSize: 'cover',
            backgroundPosition: 'center',
          }}
        />
        <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
          <Box
            sx={{
              my: 20,
              mx: 4,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            {/* <Typography sx={{ m: 1 }} component="h1" variant="h5">
              Welcome back to Pharmacista
            </Typography> */}
            <Box component="form" noValidate onSubmit={signInButton} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                name="Username"
                autoComplete="Username"
                autoFocus
                label='Username' placeholder="Enter username" fullWidth required value={userNameinput} onChange={(e)=>setUserName(e.target.value)} 
              />
              <TextField
                margin="normal"
                name="password"
                id="password"
                autoComplete="current-password"
                label='Password' placeholder="Enter Password" fullWidth required  type="password" value={passwordInput} onChange={(e)=>setPassword(e.target.value)}
              />
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                  >
                    Sign In
                  </Button>
                </Grid>
                <Grid item xs={6} mt={3}>
                  <GoogleSignIn handleSuccessfulLogin={handleSuccessfulLogin}/>
                </Grid>
              </Grid>
              <Grid container>
                <Grid item xs>
                  <Typography> Don't have an account?  
                      <Link to="/signup">Sign up</Link>
                  </Typography>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Grid>
      </Grid>
      {customAlert}
    </ThemeProvider>
  );
}