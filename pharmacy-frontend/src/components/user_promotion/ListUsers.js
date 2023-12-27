import React, { Component, Fragment } from "react";
import AdminService from "../../services/AdminService";
import "./ListUsersAndAdmins.css"; // Import the CSS file
import { TextField, Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from "@mui/material";
import CustomAlert from '../Alert/CustomAlert';

class ListUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            searchQuery: '',
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
        console.log("userId in ListUsers:", userId);
        this.fetchUsers(userId);
    }

    fetchUsers(userId) {
        AdminService.getUsers(userId)
            .then((res) => {
                this.setState({ users: res.data });
            })
            .catch((error) => {
                console.error("Error fetching users:", error);
                // Handle the error, possibly by showing an alert to the user
            });
    }


    handleSearchInputChange = (event) => {
        this.setState({ searchQuery: event.target.value });
    };

    handleSearch = () => {
        const { userId } = this.props; // Use the adminId from props
        const { searchQuery } = this.state;
    
        // Make sure to handle empty search query appropriately
        if (!searchQuery.trim()) {
            // If the search query is empty, fetch all users
            this.fetchUsers(userId);
            return;
        }
    
        console.log("search query: " + searchQuery);
        // Call the API to search users by username
        AdminService.searchByUsername(userId, searchQuery).then((res) => {
            console.log("data: ", res.data);
            const searchedUser = res.data;
            if (searchedUser) {
                this.setState({ users: [searchedUser] }); // Set the user in an array
            } else {
                // Handle case where no user is found
                this.showAlert("There exist no user with this username!!");
                this.setState({ searchQuery: '' });
            }
        });
    };

    handlePromote = (userId) => {
        const { userId: adminId } = this.props;
        const isConfirmed = window.confirm("Are you sure you want to promote this user to admin?");

        // If the admin confirms, proceed with the promotion
        if (isConfirmed) {
            console.log("userID: ", userId);
            // Call the promoteUserToAdmin function with the adminId and userId
            AdminService.promoteUserToAdmin(adminId, userId)
                .then((response) => {
                    // Handle the success response if needed
                    console.log('User promoted to admin successfully!', response);
                    this.setState({ searchQuery: '' });
                    this.fetchUsers(adminId); // Assuming you have a fetchUsers function to update the user list
                })
                .catch((error) => {
                    // Handle the error appropriately
                    console.error('Error promoting user to admin:', error);
                });
        }
    };

    
    render() {
        const { userId } = this.props; // Get userId from props
        return (
            <Fragment>
                <div className="user-list-container">
                    <h2 className="text-center">Users List</h2>
    
                    <div className="search-container">
                        <div className="search-bar-container">
                            <TextField
                                className="search-bar"
                                label="Search by Username"
                                variant="outlined"
                                value={this.state.searchQuery}
                                onChange={this.handleSearchInputChange}
                            />
                        </div>
    
                        <Button className="search-button" variant="contained" onClick={this.handleSearch}>
                            Search
                        </Button>
                    </div>
    
                    <TableContainer component={Paper}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell className="text-center">Profile Picture</TableCell>
                                    <TableCell className="text-center">Username</TableCell>
                                    <TableCell className="text-center">Phone number</TableCell>
                                    <TableCell className="text-center">Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {this.state.users.map((user) => (
                                    <TableRow key={user.userId}>
                                        <TableCell className="user-profile-box">
                                            <img src={user.profilePicture} className="profile-image" alt="profile" />
                                        </TableCell>
                                        <TableCell>{user.username}</TableCell>
                                        <TableCell>{user.phoneNumber}</TableCell>
                                        <TableCell>
                                            <Button
                                                className="promote-button"
                                                variant="contained"
                                                onClick={() => this.handlePromote(user.userId)}
                                            >
                                                Promote
                                            </Button>
                                        </TableCell>
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

export default ListUsers;
