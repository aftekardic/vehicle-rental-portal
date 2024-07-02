import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Paper,
  Grid,
  Divider,
} from "@mui/material";

function UserProfilePage() {
  const [user, setUser] = useState({
    name: "",
    email: "",
    phone: "",
  });

  useEffect(() => {
    // Fetch the user profile from the database (use a real API endpoint)
    setUser({
      name: "John Doe",
      email: "john.doe@example.com",
      phone: "+123456789",
    });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({
      ...prevUser,
      [name]: value,
    }));
  };

  const handleSave = () => {
    // Save the updated user profile to the database (use a real API endpoint)
    console.log("Profile updated:", user);
  };

  const handleChangePassword = () => {
    // Implement change password logic here
    console.log("Change password");
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        User Profile
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              label="Name"
              name="name"
              value={user.name}
              onChange={handleChange}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              label="Email"
              name="email"
              value={user.email}
              onChange={handleChange}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              label="Phone"
              name="phone"
              value={user.phone}
              onChange={handleChange}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <Button variant="contained" onClick={handleSave}>
              Save Changes
            </Button>
          </Grid>
        </Grid>
        <Divider sx={{ my: 3 }} />
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Typography variant="h6" component="h2">
              Change Password
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <Button variant="outlined" onClick={handleChangePassword}>
              Change Password
            </Button>
          </Grid>
        </Grid>
      </Paper>
    </Box>
  );
}

export default UserProfilePage;
