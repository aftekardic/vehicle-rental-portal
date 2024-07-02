import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Divider,
} from "@mui/material";
import CancelIcon from "@mui/icons-material/Cancel";

function RezervationPage() {
  const [reservations, setReservations] = useState([]);

  useEffect(() => {
    // Fetch the reservations from the database (use a real API endpoint)
    setReservations([
      {
        id: 1,
        vehicle: "Sedan",
        date: "2024-07-03 to 2024-07-05",
        user: "John Doe",
      },
      {
        id: 2,
        vehicle: "SUV",
        date: "2024-07-07 to 2024-07-10",
        user: "Jane Smith",
      },
    ]);
  }, []);

  const handleCancelReservation = (id) => {
    // Cancel reservation in the database (use a real API endpoint)
    setReservations(
      reservations.filter((reservation) => reservation.id !== id)
    );
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        View Your Reservations
      </Typography>
      <Divider />
      <List>
        {reservations.map((reservation) => (
          <ListItem key={reservation.id}>
            <ListItemText
              primary={`Vehicle: ${reservation.vehicle}, User: ${reservation.user}`}
              secondary={`Date: ${reservation.date}`}
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
        ))}
      </List>
    </Box>
  );
}

export default RezervationPage;
