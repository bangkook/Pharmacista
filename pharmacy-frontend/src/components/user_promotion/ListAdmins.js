import React, { Component, Fragment } from "react";
import { TextField, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material";
import AdminService from "../../services/AdminService";
import CustomAlert from '../Alert/CustomAlert';

class ListAdmins extends Component {
    constructor(props) {
        super(props);
        this.state = {
            admins: [],
            searchQuery: '',
            customAlert: null,
        };
    }

    showAlert = (message) => {
        this.setState({ customAlert: <CustomAlert message={message} onClose={this.hideAlert} /> });
    };

    hideAlert = () => {
        this.setState({ customAlert: null });
    };

    componentDidMount() {
        const { userId } = this.props;
        console.log("userId in ListAdmins:", userId);
        this.fetchAdmins(userId);  // Ensure that userId is passed to fetchAdmins
    }
    

    fetchAdmins(adminId) {
        AdminService.getAdmins(adminId)
            .then((res) => {
                this.setState({ admins: res.data });
            })
            .catch((error) => {
                console.error("Error fetching admins:", error);
                // Handle the error, possibly by showing an alert to the user
            });
    }

    handleSearchInputChange = (event) => {
        this.setState({ searchQuery: event.target.value });
    };

    handleSearch = () => {
        const { userId } = this.props;
        console.log("adminId in ListAdmins (handleSearch):", userId);
        const { searchQuery } = this.state;
    
        if (!searchQuery.trim()) {
            this.fetchAdmins(userId);
            return;
        }
    
        AdminService.searchByUsername(userId, searchQuery)
            .then((res) => {
                const searchedAdmin = res.data;
                if (searchedAdmin) {
                    this.setState({ admins: [searchedAdmin] });
                } else {
                    this.showAlert("There exist no user with this username!");
                    this.setState({ searchQuery: '' });
                }
            })
            .catch((error) => {
                console.error("Error searching admins:", error);
                // Handle the error, possibly by showing an alert to the user
            });
    };

    render() {
        return (
            <Fragment>
                <div className="user-list-container">
                    <h2 className="text-center">Admins List</h2>

                    <div className="search-container">
                        
                            <TextField
                                className="search-bar"
                                label="Search by Username"
                                variant="outlined"
                                value={this.state.searchQuery}
                                onChange={this.handleSearchInputChange}
                            />
                            <Button className="search-button" variant="contained" onClick={this.handleSearch}>
                            Search
                        </Button>
                       

                        
                    </div>

                    <div className="fixed-header">
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell className="text-center">Profile Picture</TableCell>
                                    <TableCell className="text-center">Username</TableCell>
                                    <TableCell className="text-center">Phone number</TableCell>
                                </TableRow>
                            </TableHead>
                        </Table>
                    </div>
                    <TableContainer component={Paper} className="scrollable-paper">
                        <Table>
                            <TableBody>
                                {this.state.admins.map((user) => (
                                    <TableRow key={user.userId}>
                                        <TableCell className="profile-pic">
                                            <img src={user.profilePicture} className="profile-image" alt="profile" />
                                        </TableCell>
                                        <TableCell>{user.username}</TableCell>
                                        <TableCell>{user.phoneNumber}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </div>
                {this.state.customAlert}
            </Fragment>
        );
    }
}

export default ListAdmins;
