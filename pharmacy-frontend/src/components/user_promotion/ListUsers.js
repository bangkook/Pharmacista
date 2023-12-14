import React, { Component, Fragment } from "react";
import { TextField, Button } from "@mui/material";
import AdminService from "../../services/AdminService";
import "./ListUsersAndAdmins.css"; // Import the CSS file

class ListUsers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            users: [],
            searchQuery: '',
        };
    }

    componentDidMount() {
        this.fetchUsers();
    }

    fetchUsers() {
        const adminId = 4; // Replace with the actual adminId, or get it from the state or props
        AdminService.getUsers(adminId).then((res) => {
            this.setState({ users: res.data });
            // console.log(this.state.users);
        });
    }

    handleSearchInputChange = (event) => {
        this.setState({ searchQuery: event.target.value });
    };

    handleSearch = () => {
        const adminId = 4; // Replace with the actual adminId, or get it from the state or props
        const { searchQuery } = this.state;

        // Make sure to handle empty search query appropriately
        if (!searchQuery.trim()) {
            // If the search query is empty, fetch all users
            this.fetchUsers();
            return;
        }

        console.log("search query: " + searchQuery);
        // Call the API to search users by username
        AdminService.searchByUsername(adminId, searchQuery).then((res) => {
            console.log("data: ", res.data);
            const searchedUser = res.data;
            if (searchedUser) {
                this.setState({ users: [searchedUser] }); // Set the user in an array
            } else {
                // Handle case where no user is found
                alert("There exist no user with this username!!");
                this.setState({ searchQuery: '' });
            }
        });
    };

    handlePromote = (userId) => {
        const adminId = 4; // Replace with the actual adminId, or get it from the state or props
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
                    this.fetchUsers(); // Assuming you have a fetchUsers function to update the user list
                })
                .catch((error) => {
                    // Handle the error appropriately
                    console.error('Error promoting user to admin:', error);
                });
        }
    };


    render() {
        
        return (
            <Fragment>
                <div className="user-list-container">
                    <h2 className="text-center">Users List</h2>
                    
                    <div className="search-container">
                        <div className="search-bar-container">
                            <TextField className="search-bar"
                                label="Search by Username"
                                variant="outlined"
                                // fullWidth
                                value={this.state.searchQuery}
                                onChange={this.handleSearchInputChange}
                            />
                        </div>
                        
                        <Button className="search-button" variant="contained" onClick={this.handleSearch}>
                            Search
                        </Button>
                    </div>
                    
                    <div className="row">
                        <table className="table">
                            <thead className="header">
                                <tr>
                                <th className="profile-pic text-center">Profile Picture</th>
                                    <th className="text-center">Username</th>
                                    <th className="text-center">Phone number</th>
                                    <th className="text-center">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.users.map((user) => (
                                    <tr key={user.userId}>
                                        <td className="profile-pic"> 
                                            <img src={user.profilePicture} className="profile-image"/>
                                        </td>
                                        <td>{user.username}</td>
                                        <td>{user.phoneNumber}</td>
                                        <td>
                                            <Button className="promote-button" variant="contained" onClick={() => this.handlePromote(user.userId)}>Promote</Button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </Fragment>
        );
    }
}

export default ListUsers;
