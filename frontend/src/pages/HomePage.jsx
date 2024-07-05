import React, { useState } from "react";
import { Box, Button, Typography, Grid, TextField } from "@mui/material";
import { Link as RouterLink } from "react-router-dom";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { userPermissionRoles } from "../services/permissionService";
import { cities } from "../services/GlobalVars";
import Autocomplete from "@mui/material/Autocomplete";
import api from "../services/api";
import { toast } from "react-toastify";

function HomePage() {
  const [city, setCity] = useState("");
  const [type, setType] = useState("");
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [vehicles, setVehicles] = useState([]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = {
      city,
      type,
      startDate,
      endDate,
    };
    try {
      const response = await api.post("/vehicle/specific-all", formData);
      setVehicles(response.data); // Assuming response.data is an array of vehicles
    } catch (error) {
      toast.error("Error fetching vehicles");
    }
  };

  const handleBookNow = async (vehicleId) => {
    const formdata = {
      vehicle_id: vehicleId,
      user_email: localStorage.getItem("userEmail"),
      start_date: startDate,
      end_date: endDate,
    };
    const response = await api.post("/reservation/book", formdata);
    toast.success(response.data);
  };

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
            <form onSubmit={handleSubmit}>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={4}>
                  <Autocomplete
                    value={city}
                    onChange={(event, newValue) => {
                      setCity(newValue);
                    }}
                    options={cities}
                    renderInput={(params) => (
                      <TextField {...params} label="City" variant="outlined" />
                    )}
                    filterOptions={(options, params) => {
                      const filtered = options.filter(
                        (option) =>
                          option
                            .toLowerCase()
                            .indexOf(params.inputValue.toLowerCase()) !== -1
                      );

                      return filtered;
                    }}
                    getOptionLabel={(option) => option}
                  />
                </Grid>
                <Grid item xs={12} sm={4}>
                  <TextField
                    fullWidth
                    label="Car Type"
                    variant="outlined"
                    value={type}
                    onChange={(e) => setType(e.target.value)}
                  />
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
              <Button
                type="submit"
                variant="contained"
                color="primary"
                sx={{ mt: 2 }}
              >
                Search
              </Button>
            </form>
          </Box>
        </>
      )}

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
      {userPermissionRoles(["USER", "TEST"]) && (
        <Box sx={{ my: 3 }}>
          <Typography variant="h5" gutterBottom>
            Available Vehicles
          </Typography>
          <Grid container spacing={3}>
            {vehicles.map((vehicle) => (
              <Grid item xs={12} sm={6} md={4} key={vehicle.id}>
                <Box border={1} p={2} borderRadius={4}>
                  <Typography variant="subtitle1">
                    {vehicle.companyName}
                  </Typography>
                  <Typography variant="body2">{vehicle.type}</Typography>
                  <Typography variant="body2">
                    Daily Price: {vehicle.dailyPrice}
                  </Typography>
                  <Typography variant="body2">
                    Availability Dates: {vehicle.availabilityDates.join(", ")}
                  </Typography>
                  <Typography variant="body2">
                    Additional Services: {vehicle.additionalServices.join(", ")}
                  </Typography>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => handleBookNow(vehicle.id)}
                    sx={{ mt: 2 }}
                  >
                    Book Now
                  </Button>
                </Box>
              </Grid>
            ))}
          </Grid>
        </Box>
      )}
    </Box>
  );
}

export default HomePage;
