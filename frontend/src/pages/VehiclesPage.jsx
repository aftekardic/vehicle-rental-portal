import React, { useState, useEffect } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  List,
  ListItem,
  ListItemText,
  ListItemSecondaryAction,
  IconButton,
  Divider,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

function VehiclesPage() {
  const [vehicles, setVehicles] = useState([]);
  const [newVehicle, setNewVehicle] = useState({
    type: "",
    price: "",
    availability: "",
  });

  useEffect(() => {
    // Fetch the vehicles from the database (use a real API endpoint)
    setVehicles([
      {
        id: 1,
        type: "Sedan",
        price: "100",
        availability: "2024-07-01 to 2024-07-10",
      },
      {
        id: 2,
        type: "SUV",
        price: "150",
        availability: "2024-07-05 to 2024-07-15",
      },
    ]);
  }, []);

  const handleAddVehicle = () => {
    // Add new vehicle to the database (use a real API endpoint)
    setVehicles([...vehicles, { id: vehicles.length + 1, ...newVehicle }]);
    setNewVehicle({ type: "", price: "", availability: "" });
  };

  const handleDeleteVehicle = (id) => {
    // Delete vehicle from the database (use a real API endpoint)
    setVehicles(vehicles.filter((vehicle) => vehicle.id !== id));
  };

  return (
    <Box sx={{ p: 3 }}>
      <Typography variant="h4" component="h1" gutterBottom>
        Manage Your Vehicles
      </Typography>
      <Box component="form" sx={{ mb: 2 }}>
        <TextField
          label="Vehicle Type"
          value={newVehicle.type}
          onChange={(e) =>
            setNewVehicle({ ...newVehicle, type: e.target.value })
          }
          fullWidth
          sx={{ mb: 2 }}
        />
        <TextField
          label="Price"
          value={newVehicle.price}
          onChange={(e) =>
            setNewVehicle({ ...newVehicle, price: e.target.value })
          }
          fullWidth
          sx={{ mb: 2 }}
        />
        <TextField
          label="Availability"
          value={newVehicle.availability}
          onChange={(e) =>
            setNewVehicle({ ...newVehicle, availability: e.target.value })
          }
          fullWidth
          sx={{ mb: 2 }}
        />
        <Button variant="contained" onClick={handleAddVehicle}>
          Add Vehicle
        </Button>
      </Box>
      <Divider />
      <List>
        {vehicles.map((vehicle) => (
          <ListItem key={vehicle.id}>
            <ListItemText
              primary={vehicle.type}
              secondary={`Price: ${vehicle.price}, Availability: ${vehicle.availability}`}
            />
            <ListItemSecondaryAction>
              <IconButton
                edge="end"
                onClick={() => handleDeleteVehicle(vehicle.id)}
              >
                <DeleteIcon />
              </IconButton>
            </ListItemSecondaryAction>
          </ListItem>
        ))}
      </List>
    </Box>
  );
}

export default VehiclesPage;
