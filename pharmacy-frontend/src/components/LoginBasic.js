import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Grid, Paper,Avatar, TextField, Button, Typography} from "@mui/material";
import { useState } from "react";
import { Link } from 'react-router-dom'
import GoogleSignIn from './login';

// TODO remove, this demo shouldn't need to reset the theme.

const defaultTheme = createTheme();

export default function LoginBasic ({handleSuccessfulLogin}) {

    const BaseUri = 'http://localhost:8088/user'
    const [userNameinput,setUserName]=useState('')
    const [passwordInput,setPassword]=useState('')

  const signInButton= async (e)=>{
    if(userNameinput==='' ||passwordInput==='' ){
        alert("Please fill all required fileds")
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
            alert("Wrong password");
        } else if (data === 'USER_NOT_FOUND') {
            alert("You don't have an account");
        } else if (data === 'INVALID_INPUT') {
            alert("Invalid input");
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
            backgroundImage: 'url(https://deeplor.s3.us-west-2.amazonaws.com/matting_original/2023/12/25/cbfbc137ba814895bd2879c12c914a2a.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20231225T125811Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAROYXHKZUSZONTWIG%2F20231225%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Signature=98a369c5490c18ec9cfb602881dc1a0559427522765972cb4aca80aa1b61f5f7)',
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
            <Typography sx={{ m: 1 }} component="h1" variant="h5">
              Welcome back to Pharmacista
            </Typography>
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
    </ThemeProvider>
  );
}