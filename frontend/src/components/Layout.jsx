import React from "react";
import { Outlet, Link as RouterLink } from "react-router-dom";
import {
  AppBar,
  Box,
  Drawer,
  Grid,
  List,
  ListItem,
  ListItemText,
  Toolbar,
  Typography,
  IconButton,
  ListItemIcon,
} from "@mui/material";
import HomeIcon from "@mui/icons-material/Home";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import EventIcon from "@mui/icons-material/Event";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";

const Layout = ({}) => {
  const handleSignOut = () => {
    // Implement sign out logic here
    console.log("Signing out...");
  };
  return (
    <Box sx={{ display: "flex" }}>
      {/* AppBar */}
      <AppBar
        position="fixed"
        sx={{
          zIndex: (theme) => theme.zIndex.drawer + 1,
          backgroundColor: "#333",
          color: "#fff",
        }}
      >
        <Toolbar>
          <Typography variant="h6" noWrap>
            Car Rental Portal
          </Typography>
        </Toolbar>
      </AppBar>

      {/* Drawer */}
      <Drawer variant="permanent" sx={{ width: 180, flexShrink: 0 }}>
        <Toolbar />
        <Box sx={{ flexGrow: 1 }}>
          <List>
            <ListItem button component={RouterLink} to="/">
              <ListItemIcon>
                <HomeIcon color="#333" />
              </ListItemIcon>
              <ListItemText primary="Home" />
            </ListItem>
            <ListItem button component={RouterLink} to="/vehicles">
              <ListItemIcon>
                <DirectionsCarIcon color="#333" />
              </ListItemIcon>
              <ListItemText primary="Vehicles" />
            </ListItem>
            <ListItem button component={RouterLink} to="/reservations">
              <ListItemIcon>
                <EventIcon color="#333" />
              </ListItemIcon>
              <ListItemText primary="Reservation" />
            </ListItem>
            <ListItem button component={RouterLink} to="/profile">
              <ListItemIcon>
                <AccountCircleIcon color="#333" />
              </ListItemIcon>
              <ListItemText primary="Profile" />
            </ListItem>
          </List>
        </Box>
        {/* Sign Out Button */}
        <Box
          sx={{
            borderTop: "1px solid rgba(255, 255, 255, 0.12)",
            display: "flex",
            justifyContent: "center",
            marginBottom: 2,
          }}
        >
          <IconButton onClick={handleSignOut} color="#333">
            <ExitToAppIcon />
          </IconButton>
        </Box>
      </Drawer>

      {/* Main Content */}
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Outlet />
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
};

export default Layout;
