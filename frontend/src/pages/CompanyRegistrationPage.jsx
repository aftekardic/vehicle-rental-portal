import React, { useEffect, useState } from "react";
import { Box, Button, TextField, Typography, Grid } from "@mui/material";
import Autocomplete from "@mui/material/Autocomplete";
import { cities } from "../services/GlobalVars";
import api from "../services/api";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function CompanyRegistrationPage() {
  const navigate = useNavigate();
  const [companyName, setCompanyName] = useState("");
  const [city, setCity] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = {
      name: companyName,
      city: city,
      email: email,
      password: password,
    };

    try {
      const response = await api.post("/auth/register-company", formData);
      toast.success(response.data, {
        onClose: () => {
          navigate("/login");
        },
      });
    } catch (error) {
      toast.error(error.response.data);
    }
  };
  useEffect(() => {
    localStorage.getItem("accessToken") !== null && navigate("/");
  }, [navigate]);

  return (
    <Box sx={{ flexGrow: 1, p: 3 }}>
      <Typography variant="h4" component="div" gutterBottom>
        Register Your Company
      </Typography>
      <form onSubmit={handleSubmit}>
        <Grid container spacing={2}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Company Name"
              variant="outlined"
              required
              value={companyName}
              onChange={(e) => setCompanyName(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <Autocomplete
              fullWidth
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
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Email"
              type="email"
              variant="outlined"
              required
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Password"
              type="password"
              variant="outlined"
              required
              value={password}
              onChange={(e) => setPassword(e.target.value)}
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

export default CompanyRegistrationPage;
