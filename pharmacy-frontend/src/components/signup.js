import { Avatar, Button, Grid, Paper, TextField, Typography } from "@mui/material";
import React, { useMemo, useState } from "react";
import AddCircleOutlineOutlinedIcon from '@mui/icons-material/AddCircleOutlineOutlined';
import Select from "react-select";
import countryList from "react-select-country-list";

const BaseUri = 'http://localhost:8088/user'

const paperStyle = {
    padding: '30px 20px',
    width: '600px',
    margin: '100px auto'
}
const headerStyle = {
    margin: '5px',
    color: 'purple',
}
const avatarStyle = {
    backgroundColor: '#1bbd7e'
}
const textFieldStyle = {
    align: 'left',
    margin: '5px',
}
const selectStyle = {
    control: (provided) => ({
        ...provided,
        margin: '5px',
        width: '295px',
        padding: '10px',
    })
}


function Signup() {
    const [country, setCountry] = useState("")
    const options = useMemo(() => countryList().getData(), [])
    const countryHandler = (country) => {
        setCountry(country);
    }

    const [username, setUN] = useState("")
    const [pass, setPass] = useState("")
    const [confPass, setConfPass] = useState("")
    const [stAdd, setStAddr] = useState("")
    const [zip, setZip] = useState("")
    const [phone, setPhone] = useState("")
    const [city, setCity] = useState("")

    const handleClick = async (e) => {
        e.preventDefault();

        const validPhone = /^\d{11}$/.test(phone) || phone === "";
        const passEqualsConfPass = (pass === confPass)
        const passCheck = /^.{8,16}$/.test(pass);
        const validUsername = /^[a-zA-Z][a-zA-Z0-9_]{5,29}$/.test(username);
        const validZip = /^\d{3,5}$/.test(zip) || zip === "";

        let user = {}
        if (passEqualsConfPass && validPhone && passCheck  && validZip && validUsername) {
            console.log(username, pass, confPass, stAdd, zip, phone, country.label);
            const countryName = country.label;
            user = {"username":username, "password": pass, "streetAddress": stAdd, "country": countryName, "zipCode": zip, "phoneNumber":phone };
            console.log(user);

            const response = fetch(`${BaseUri}/addUser`, {                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user)
            })
            
            if((await response).ok){
                alert('Welcome to Pharmacista '+ user.username + '! Horray!!')
            } else if((await response).status == 422) {
                alert("Username is already taken. Choose another one!")
            }

        } 
        else if (!validUsername) {
            alert("Username is at least 6 and at most 30 characters (letters or numbers) with _ only as special character. Spaces are not allowed. Start with an alphabet!");        } 
        else if (!passEqualsConfPass) {
            console.log("Password and Confirm Password are not the same")
            // Re-enter the password
            alert("Password and Confirm Password are not the same");
            setConfPass("");

        } 
        else if (!passCheck) {
            alert("Password must be at least 8 characters and at most 16 character");
            setPass("");
            setConfPass("");
        } 
        else if (!validPhone) {
            alert("Phone number must be 11 digits!");
        } 
        else if (!validZip) {
            alert("Zip code must be a from 3 to 5 digits");
        }
        
    }


    return (
        // username, password, confirm password,street address, city, country, zip code, phone number
        <Grid>
            <Paper elevation={20} style={paperStyle}>
                <Grid align='center'>
                    <Avatar style={avatarStyle}>
                        <AddCircleOutlineOutlinedIcon />
                    </Avatar>
                    <h2 style={headerStyle}>Sign Up</h2>
                    <Typography variant="caption">Please fill in this form to Sign Up to Pharmacista</Typography>
                </Grid>
                <form>
                    <Grid container spacing={2}>
                        <Grid item xs={6}>
                            {/* <Typography variant="caption">Username is at least 5 and at most 30 alphanumeric character with "_"  only as special character</Typography> */}
                            <TextField fullWidth label="Username*" placeholder="Enter a valid username" style={textFieldStyle}
                                value={username} onChange={(e) => setUN(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField fullWidth label="Phone Number" placeholder="Enter your phone number" style={textFieldStyle}
                                value={phone} onChange={(e) => setPhone(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField fullWidth label="Password*" placeholder="Enter a strong password" type="password" style={textFieldStyle}
                                value={pass} onChange={(e) => setPass(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField fullWidth label="Confirm Password*" placeholder="Re-enter your password" type="password" style={textFieldStyle}
                                value={confPass} onChange={(e) => setConfPass(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField fullWidth label="Street Address" placeholder="Enter your address correctly" style={textFieldStyle}
                                value={stAdd} onChange={(e) => setStAddr(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField fullWidth label="Zip Code" placeholder="Enter the zip code of your city" style={textFieldStyle}
                                value={zip} onChange={(e) => setZip(e.target.value)} />
                        </Grid>
                        <Grid item xs={6}>
                            <Select options={options} country={country} onChange={countryHandler} styles={selectStyle} placeholder="Select your country" />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField fullWidth label="City" placeholder="Enter your city" style={textFieldStyle}
                                value={city} onChange={(e) => setCity(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <Button type="submit" variant="contained" color="primary" onClick={handleClick}>
                                Sign Up
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Paper>
        </Grid>
    )
}

export default Signup;