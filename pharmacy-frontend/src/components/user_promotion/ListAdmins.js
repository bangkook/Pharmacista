import React, { Component, Fragment } from "react";
import { TextField, Button } from "@mui/material";
import AdminService from "../../services/AdminService";
import "./ListUsersAndAdmins.css"; // Import the CSS file

class ListAdmins extends Component {
    constructor(props) {
        super(props);
        this.state = {
            admins: [],
            searchQuery: '',
        };
    }

    componentDidMount() {
        this.fetchAdmins();
    }

    fetchAdmins() {
        const adminId = 4; // Replace with the actual adminId, or get it from the state or props
        AdminService.getAdmins(adminId).then((res) => {
            this.setState({ admins: res.data });
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
            // If the search query is empty, fetch all admins
            this.fetchAdmins();
            return;
        }

        console.log("search query: " + searchQuery);
        // Call the API to search admins by username
        AdminService.searchByUsername(adminId, searchQuery).then((res) => {
            console.log("data: ", res.data);
            const searchedAdmin = res.data;
            if (searchedAdmin) {
                this.setState({ admins: [searchedAdmin] }); // Set the user in an array
            } else {
                // Handle case where no user is found
                alert("There exist no user with this username!!");
                this.setState({ searchQuery: '' });
            }
        });
    };

    render() {
        return (
            <Fragment>
                <div className="user-list-container">
                    <h2 className="text-center">Admins List</h2>
                    
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
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.admins.map((user) => (
                                    <tr key={user.userId}>
                                        <td className="profile-pic">
                                            <img src={user.profilePicture} className="profile-image"/>
                                        </td>
                                        <td>{user.username}</td>
                                        <td>{user.phoneNumber}</td>
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

export default ListAdmins;
