import React, { useState } from "react";
import { Box, Button, Typography, Grid, TextField } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { userPermissionRoles } from "../services/permissionService";

function HomePage() {
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Typography variant="h4" component="div" gutterBottom>
        Welcome to Car Rental Portal
      </Typography>
      {userPermissionRoles(["USER", "TEST"]) && (
        <>
          <Typography variant="body1" gutterBottom>
            Find and book your perfect rental car.
          </Typography>

          <Box sx={{ my: 3 }}>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={4}>
                <TextField fullWidth label="City" variant="outlined" />
              </Grid>
              <Grid item xs={12} sm={4}>
                <TextField fullWidth label="Car Type" variant="outlined" />
              </Grid>
              <Grid item xs={12} sm={4}>
                <Box display="flex" alignItems="center">
                  <DatePicker
                    label="Start"
                    value={startDate}
                    onChange={(date) => setStartDate(date)}
                    renderInput={(params) => (
                      <TextField {...params} fullWidth />
                    )}
                    format="DD/MM/YYYY"
                  />
                  <Typography variant="body1" sx={{ mx: 2 }}>
                    -
                  </Typography>
                  <DatePicker
                    label="End"
                    value={endDate}
                    onChange={(date) => setEndDate(date)}
                    renderInput={(params) => (
                      <TextField {...params} fullWidth />
                    )}
                    minDate={startDate}
                    format="DD/MM/YYYY"
                  />
                </Box>
              </Grid>
            </Grid>
            <Button variant="contained" color="primary" sx={{ mt: 2 }}>
              Search
            </Button>
          </Box>
        </>
      )}

      {/* Links to Other Pages */}
      <Grid container spacing={2}>
        {userPermissionRoles([]) && (
          <>
            <Grid item xs={12} sm={6}>
              <Button
                component={RouterLink}
                to="/company-registration"
                variant="outlined"
                fullWidth
              >
                Register Your Company
              </Button>
            </Grid>
            <Grid item xs={12} sm={6}>
              <Button
                component={RouterLink}
                to="/user-registration"
                variant="outlined"
                fullWidth
              >
                Register as a User
              </Button>
            </Grid>
          </>
        )}
        {userPermissionRoles(["COMPANY", "TEST"]) && (
          <Grid item xs={12} sm={6}>
            <Button
              component={RouterLink}
              to="/vehicles"
              variant="outlined"
              fullWidth
            >
              Manage Your Vehicles
            </Button>
          </Grid>
        )}

        {userPermissionRoles(["USER", "TEST"]) && (
          <Grid item xs={12} sm={6}>
            <Button
              component={RouterLink}
              to="/reservations"
              variant="outlined"
              fullWidth
            >
              View Your Reservations
            </Button>
          </Grid>
        )}
      </Grid>
    </Box>
  );
}
export default HomePage;
