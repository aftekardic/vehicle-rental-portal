import React from "react";
import { Outlet, Link as RouterLink } from "react-router-dom";
import {
  AppBar,
  Box,
  Drawer,
  List,
  ListItem,
  ListItemText,
  Toolbar,
  Typography,
  IconButton,
  ListItemIcon,
  Link,
} from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import EventIcon from "@mui/icons-material/Event";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import LogoutIcon from "@mui/icons-material/Logout";
import LoginIcon from "@mui/icons-material/Login";
import { userPermissionRoles } from "../services/permissionService";
import api from "../services/api";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

function Layout() {
  const navigate = useNavigate();
  const handleSignOut = async () => {
    try {
      const response = await api.post("/auth/logout");
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("userRoles");
      localStorage.removeItem("userEmail");
      const { data } = response;

      toast.success(data, {
        onClose: () => {
          navigate(0);
        },
      });
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  return (
    <Box sx={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
      <AppBar
        position="fixed"
        sx={{
          zIndex: (theme) => theme.zIndex.drawer + 1,
          backgroundColor: "#333",
          color: "#fff",
        }}
      >
        <Toolbar>
          <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
            Car Rental Portal
          </Typography>
          {localStorage.getItem("accessToken") === null && (
            <Link
              component={RouterLink}
              to="/login"
              sx={{
                color: "inherit",
                textDecoration: "none",
                display: "flex",
                alignItems: "center",
              }}
            >
              <IconButton color="inherit">
                <LoginIcon />
              </IconButton>
            </Link>
          )}
        </Toolbar>
      </AppBar>

      <Drawer variant="permanent" sx={{ width: 180, flexShrink: 0 }}>
        <Toolbar />
        <Box sx={{ flexGrow: 1 }}>
          <List>
            <ListItem button component={RouterLink} to="/">
              <ListItemIcon>
                <HomeIcon sx={{ color: "#333" }} />
              </ListItemIcon>
              <ListItemText primary="Home" />
            </ListItem>
            {userPermissionRoles(["COMPANY", "TEST"]) && (
              <ListItem button component={RouterLink} to="/vehicles">
                <ListItemIcon>
                  <DirectionsCarIcon sx={{ color: "#333" }} />
                </ListItemIcon>
                <ListItemText primary="Vehicles" />
              </ListItem>
            )}
            {userPermissionRoles(["USER", "TEST"]) && (
              <ListItem button component={RouterLink} to="/reservations">
                <ListItemIcon>
                  <EventIcon sx={{ color: "#333" }} />
                </ListItemIcon>
                <ListItemText primary="Reservation" />
              </ListItem>
            )}
            <ListItem button component={RouterLink} to="/profile">
              <ListItemIcon>
                <AccountCircleIcon sx={{ color: "#333" }} />
              </ListItemIcon>
              <ListItemText primary="Profile" />
            </ListItem>
          </List>
        </Box>

        {localStorage.getItem("accessToken") !== null && (
          <Box
            sx={{
              borderTop: "1px solid rgba(255, 255, 255, 0.12)",
              display: "flex",
              justifyContent: "center",
              marginBottom: 2,
            }}
          >
            <IconButton onClick={handleSignOut} sx={{ color: "#333" }}>
              <LogoutIcon />
            </IconButton>
          </Box>
        )}
      </Drawer>

      <Box
        component="main"
        sx={{
          flexGrow: 1,
          paddingTop: 12,
          paddingRight: 6,
          paddingBottom: 12,
          paddingLeft: 28,
        }}
      >
        <Outlet />
      </Box>

      <Box
        component="footer"
        sx={{
          p: 2,
          mt: "auto",
          backgroundColor: "#333",
          color: "#fff",
          textAlign: "center",
        }}
      >
        <Typography variant="body2">
          &copy; {new Date().getFullYear()} Car Rental Portal. All rights
          reserved.
        </Typography>
      </Box>
    </Box>
  );
}

export default Layout;
