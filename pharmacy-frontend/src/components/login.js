import { Grid, Paper,Avatar, TextField, Button, Typography ,Link} from "@mui/material";
import React, { useState } from "react";
import LockIcon from '@mui/icons-material/Lock';

const Login=()=>{
    const BaseUri = 'http://localhost:8088/user'
    const paperStyle={padding: 20 , height:'50 vh', width:340 , margin:"150px auto" }
    const lockIconStyle={backgroundColor:'#b18fbf'}
    const textmargin={ margin:"10px 0px"}
    const buttoncolor={ backgroundColor:'#b18fbf', margin:"10px 0"}
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
            const data = await response.json()
            console.log(data)
            if(data===1){
                const registeredUser = await fetch(`${BaseUri}/getUserByName?`+ new URLSearchParams({
                userName: userNameinput,
                password:passwordInput
                }))
                const userData = await registeredUser.json()
                console.log(userData)
            }else if(data===0){
                alert("wrong password")
            }else{
                alert("You don't have an account")
            }
            }
        
    }

    return(
        <Grid>
            <Paper elevation={10} style={paperStyle} >
                <Grid align='center'>    
                    <Avatar style={lockIconStyle}><LockIcon/></Avatar>
                    <h2>Log-in</h2>
                </Grid>
                
                <div style={textmargin}>
                <TextField label='Username' placeholder="Enter username" fullWidth required variant="standard" value={userNameinput} onChange={(e)=>setUserName(e.target.value)}  />
                </div>
                <div style={textmargin}>
                <TextField label='Password' placeholder="Enter Password" fullWidth required  type="password" variant="standard" value={passwordInput} onChange={(e)=>setPassword(e.target.value)} />
                </div>
                <Button type="submit" variant="contained" style={buttoncolor} fullWidth onClick={signInButton}>Log-in</Button>
                <Typography> Do you have an account 
                    <Link href="#"> Sign up</Link>
                </Typography>
            </Paper>
        </Grid>
    )
}

export default Login