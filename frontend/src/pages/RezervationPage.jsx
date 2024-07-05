import React, { useState, useEffect } from "react";
import {
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Divider,
  Paper,
} from "@mui/material";
import CancelIcon from "@mui/icons-material/Cancel";
import api from "../services/api";
import { toast } from "react-toastify";
import { userPermissionRoles } from "../services/permissionService";

function RezervationPage() {
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    const getReservations = async () => {
      if (
        localStorage.getItem("userRoles").includes("USER") &&
        localStorage.getItem("accessToken")
      ) {
        try {
          const response = await api.get(
            `/reservation/reserved/${localStorage.getItem("userEmail")}`
          );
          setReservations(response.data);
        } catch (error) {
          toast.error("Failed to fetch reservations.");
        }
      }
    };
    getReservations();
  }, []);

  const handleCancelReservation = async (id) => {
    try {
      const response = await api.delete(`/reservation/delete/${id}`);
      setReservations(
        reservations.filter((reservation) => reservation.id !== id)
      );
      toast.success(response.data);
    } catch (error) {
      toast.error(error.response.data);
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      {userPermissionRoles(["USER", "TEST"]) ? (
        <>
          <Typography variant="h4" component="h1" gutterBottom>
            View Your Reservations
          </Typography>
          <Divider />
          <List>
            {reservations.map((reservation) => (
              <Paper key={reservation.id} sx={{ my: 2, p: 2 }}>
                <ListItem>
                  <ListItemText
                    primary={`Vehicle: ${reservation.vehicleType}, User: ${reservation.userName} ${reservation.userSurname}`}
                    secondary={`Dates: ${reservation.startDate} to ${reservation.endDate}`}
                  />
                  <ListItemText
                    primary={`Company: ${reservation.companyName}`}
                    secondary={`City: ${reservation.companyCity}, Email: ${reservation.companyEmail}`}
                  />
                  <ListItemText
                    primary={`Price: $${reservation.vehicleDailyPrice}`}
                    secondary={`Services: ${reservation.vehicleAdditionalServices.join(
                      ", "
                    )}`}
                  />
                  <ListItemSecondaryAction>
                    <IconButton
                      edge="end"
                      onClick={() => handleCancelReservation(reservation.id)}
                    >
                      <CancelIcon />
                    </IconButton>
                  </ListItemSecondaryAction>
                </ListItem>
              </Paper>
            ))}
          </List>
        </>
      ) : (
        <div> You do not have a permission. Please log in.</div>
      )}
    </Box>
  );
}

export default RezervationPage;
