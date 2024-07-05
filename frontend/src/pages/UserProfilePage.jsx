import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Paper,
  Grid,
  Divider,
  IconButton,
} from "@mui/material";
import LockOpenOutlinedIcon from "@mui/icons-material/LockOpenOutlined";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import api from "../services/api";
import { toast } from "react-toastify";
import { userPermissionRoles } from "../services/permissionService";
import { cities } from "../services/GlobalVars";
import Autocomplete from "@mui/material/Autocomplete";
import { useNavigate } from "react-router-dom";

function UserProfilePage() {
  const navigate = useNavigate();
  const [count, setCount] = useState(0);
  const [changePw, setChangePw] = useState(false);
  const [user, setUser] = useState({
    name: "",
    surname: "",
    email: "",
    password: "",
  });

  const [company, setCompany] = useState({
    name: "",
    email: "",
    city: "",
    password: "",
  });

  useEffect(() => {
    const getUserInformations = async () => {
      if (localStorage.getItem("userRoles")?.includes("USER")) {
        const response = await api.get(
          `/user/info/${localStorage.getItem("userEmail")}`
        );
        setUser(response.data);
      } else if (localStorage.getItem("userRoles")?.includes("COMPANY")) {
        const response = await api.get(
          `/company/info/${localStorage.getItem("userEmail")}`
        );
        setCompany(response.data);
      }
    };
    getUserInformations();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (localStorage.getItem("userRoles").includes("USER")) {
      setUser((prevUser) => ({
        ...prevUser,
        [name]: value,
      }));
    } else if (localStorage.getItem("userRoles").includes("COMPANY")) {
      setCompany((prevCompany) => ({
        ...prevCompany,
        [name]: value,
      }));
    }
  };

  const handleSave = async () => {
    if (localStorage.getItem("userRoles")?.includes("USER")) {
      const response = await api.put(
        `/user/update/${localStorage.getItem("userEmail")}`,
        user
      );
      if (response.status === 200) {
        const responseKc = await api.put(
          `/kc/updateUser/${localStorage.getItem("userEmail")}`,
          user
        );
        toast.success(responseKc.data, {
          onClose: () => {
            navigate("/login");
          },
        });
        await api.post("/auth/logout");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("userRoles");
        localStorage.removeItem("userEmail");
      } else {
        toast.error("An error occured when update user...");
      }
    } else if (localStorage.getItem("userRoles")?.includes("COMPANY")) {
      const response = await api.put(
        `/company/update/${localStorage.getItem("userEmail")}`,
        company
      );
      if (response.status === 200) {
        const responseKc = await api.put(
          `/kc/updateUser/${localStorage.getItem("userEmail")}`,
          company
        );
        toast.success(responseKc.data, {
          onClose: () => {
            navigate("/login");
          },
        });
        await api.post("/auth/logout");
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("userRoles");
        localStorage.removeItem("userEmail");
      } else {
        toast.error("An error occured when update user...");
      }
    }
  };

  const handleChangeStatusOfChangePw = (e) => {
    setCount(count + 1);
    if (count % 2 === 0) {
      setChangePw(true);
    } else {
      setChangePw(false);
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        User Profile
      </Typography>
      <Paper sx={{ p: 3 }}>
        <Grid container spacing={3}>
          {userPermissionRoles(["USER", "TEST"]) ? (
            <>
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
                  label="Surname"
                  name="surname"
                  value={user.surname}
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
            </>
          ) : userPermissionRoles(["COMPANY", "TEST"]) ? (
            <>
              <Grid item xs={12}>
                <TextField
                  label="Company Name"
                  name="name"
                  value={company.name}
                  onChange={handleChange}
                  fullWidth
                />
              </Grid>
              <Grid item xs={12}>
                <Autocomplete
                  value={company.city}
                  onChange={(event, newValue) =>
                    handleChange({ target: { name: "city", value: newValue } })
                  }
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
                  label="Email"
                  name="email"
                  value={company.email}
                  onChange={handleChange}
                  fullWidth
                />
              </Grid>
            </>
          ) : (
            <div>You do not have a permission.</div>
          )}
          {userPermissionRoles(["USER", "COMPANY", "TEST"]) && (
            <Grid item xs={12}>
              <Button variant="contained" onClick={handleSave}>
                Save Changes
              </Button>
            </Grid>
          )}
        </Grid>
        <Divider sx={{ my: 3 }} />
        {userPermissionRoles(["USER", "COMPANY", "TEST"]) && (
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Typography variant="h6" component="h2">
                Change Password
              </Typography>
            </Grid>
            <Grid item xs={12} display={"flex"} gap={2}>
              <TextField
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="new-password"
                onChange={handleChange}
                disabled={!changePw}
              />
              <IconButton
                sx={{ textTransform: "none" }}
                onClick={handleChangeStatusOfChangePw}
              >
                {changePw ? <LockOpenOutlinedIcon /> : <LockOutlinedIcon />}
              </IconButton>
            </Grid>
          </Grid>
        )}
      </Paper>
    </Box>
  );
}

export default UserProfilePage;
