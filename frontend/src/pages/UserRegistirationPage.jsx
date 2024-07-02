import React from "react";
import { Box, Button, TextField, Typography, Grid } from "@mui/material";

function UserRegistirationPage() {
  const handleSubmit = (event) => {
    event.preventDefault();
    // Handle form submission
    console.log("User registered!");
  };

  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Typography variant="h4" component="div" gutterBottom>
        Register as a User
      </Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Full Name"
              variant="outlined"
              required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Email"
              type="email"
              variant="outlined"
              required
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Password"
              type="password"
              variant="outlined"
              required
            />
          </Grid>
          <Grid item xs={12}>
            <Button variant="contained" color="primary" type="submit">
              Register
            </Button>
          </Grid>
        </Grid>
      </form>
    </Box>
  );
}

export default UserRegistirationPage;
