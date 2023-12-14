import axios from 'axios';

const ADMIN_SERVICES_BASE_URL = `http://localhost:8088/admin`;

class AdminService {
    getUsers(adminId) {
        return axios.get(`${ADMIN_SERVICES_BASE_URL}/${adminId}/manageUsers`);
    }

    getAdmins(adminId) {
        return axios.get(`${ADMIN_SERVICES_BASE_URL}/${adminId}/getAdmins`);
    }

    searchByUsername(adminId, username) {
        return axios.get(`${ADMIN_SERVICES_BASE_URL}/searchUser/${adminId}/${username}`);
    }

    promoteUserToAdmin(adminId, userId) {
        return axios.put(`${ADMIN_SERVICES_BASE_URL}/promote-to-admin/${adminId}/${userId}`)
        .then(response => response.data)
        .catch(error => {
            throw error; // Handle the error appropriately
        });
    }
}

export default new AdminService();