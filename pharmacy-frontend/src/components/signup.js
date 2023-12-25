import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import Box from '@mui/material/Box';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Button, Grid, Paper, TextField, Typography } from "@mui/material";
import { useMemo, useState } from "react";
import { Link} from 'react-router-dom';
import GoogleSignUp from './googleSignUp';
import Select from "react-select";
import countryList from "react-select-country-list";

const BaseUri = 'http://localhost:8088/user';
const defaultTheme = createTheme();

const selectStyle = {
    control: (provided) => ({
      ...provided,
      margin: '5px',
      width: '295px',
      padding: '10px',
    })
  };
  
export default function Signup({ handleSuccessfulLogin }) {
  const [country, setCountry] = useState("");
  const options = useMemo(() => countryList().getData(), []);
  const countryHandler = (country) => {
    setCountry(country);
  };

  const [username, setUN] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [retypePassword, setRetypePassword] = useState("");
  const [streetAddress, setStreetAddress] = useState("");
  const [city, setCity] = useState("");
  const [zipCode, setZipCode] = useState("");

  const handleClick = async (e) => {
    e.preventDefault();

    const validPhone = /^\d{11}$/.test(phone) || phone === "";
    const passEqualsConfPass = password === retypePassword;
    const passCheck = /^.{8,16}$/.test(password);
    const validUsername = /^[a-zA-Z][a-zA-Z0-9_]{5,29}$/.test(username);
    const validZip = /^\d{3,5}$/.test(zipCode) || zipCode === "";

    let user = {};
    if (passEqualsConfPass && validPhone && passCheck && validZip && validUsername) {
      console.log(username, email, phone, password, retypePassword, streetAddress, city, country.label, zipCode);
      const countryName = country.label;
      user = {
        username,
        email,
        phone,
        password,
        streetAddress,
        city,
        country: countryName,
        zipCode,
      };
      console.log(user);

      const response = fetch(`${BaseUri}/addUser`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
      });

      if ((await response).ok) {
        alert('Welcome to Pharmacista ' + user.username + '! Horray!!');
        handleSuccessfulLogin(user.username);
      } else if ((await response).status === 422) {
        alert("Username is already taken. Choose another one!");
      }
    } else if (!validUsername) {
      alert("Username is at least 6 and at most 30 characters (letters or numbers) with _ only as special character. Spaces are not allowed. Start with an alphabet!");
    } else if (!passEqualsConfPass) {
      console.log("Password and Confirm Password are not the same");
      // Re-enter the password
      alert("Password and Confirm Password are not the same");
      setRetypePassword("");
    } else if (!passCheck) {
      alert("Password must be at least 8 characters and at most 16 characters");
      setPassword("");
      setRetypePassword("");
    } else if (!validPhone) {
      alert("Phone number must be 11 digits!");
    } else if (!validZip) {
      alert("Zip code must be from 3 to 5 digits");
    }
  };

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
              my: 10,
              mx: 4,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            <Typography sx={{ m: 1 }} component="h1" variant="h5">
              Welcome to Pharmacista
            </Typography>
            <Box component="form" noValidate onSubmit={handleClick} sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
              />

              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="Username*"
                    placeholder="Enter a valid username"
                    style={{ margin: '5px' }}
                    value={username}
                    onChange={(e) => setUN(e.target.value)}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="Phone Number"
                    placeholder="Enter your phone number"
                    style={{ margin: '5px' }}
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="Password*"
                    placeholder="Enter a strong password"
                    type="password"
                    style={{ margin: '5px' }}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="Confirm Password*"
                    placeholder="Re-enter your password"
                    type="password"
                    style={{ margin: '5px' }}
                    value={retypePassword}
                    onChange={(e) => setRetypePassword(e.target.value)}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="Street Address"
                    placeholder="Enter your address correctly"
                    style={{ margin: '5px' }}
                    value={streetAddress}
                    onChange={(e) => setStreetAddress(e.target.value)}
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="Zip Code"
                    placeholder="Enter the zip code of your city"
                    style={{ margin: '5px' }}
                    value={zipCode}
                    onChange={(e) => setZipCode(e.target.value)}
                  />
                </Grid>
                <Grid item xs={6}>
                  <Select
                    options={options}
                    country={country}
                    onChange={countryHandler}
                    styles={selectStyle}
                    placeholder="Select your country"
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    fullWidth
                    label="City"
                    placeholder="Enter your city"
                    style={{ margin: '5px' }}
                    value={city}
                    onChange={(e) => setCity(e.target.value)}
                  />
                </Grid>
              </Grid>
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  {/* Place the "Sign Up" button and GoogleSignUp button beside each other */}
                  <Grid container spacing={2}>
                    <Grid item xs>
                      <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                      >
                        Sign Up
                      </Button>
                    </Grid>
                    <Grid item xs >
                    <Box mt={3}> {/* Adjust the margin or spacing here */}
                        <GoogleSignUp handleSuccessfulLogin={handleSuccessfulLogin} />
                      </Box>
                    </Grid>
                  </Grid>
                </Grid>
                {/* Adjust the Grid item properties as needed */}
                <Grid item xs>
                  <Typography> Already have an account?
                        <Link  href="#" variant="body2" to='/LoginBasic'>
                        Log in
                        </Link>
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
