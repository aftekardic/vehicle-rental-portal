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
import api from "../services/api";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import DeleteIcon from "@mui/icons-material/Delete";
import dayjs from "dayjs";
import "dayjs/locale/tr";
import { toast } from "react-toastify";
import { userPermissionRoles } from "../services/permissionService";

dayjs.locale("tr");

function VehiclesPage() {
  const [vehicles, setVehicles] = useState([]);
  const [newVehicle, setNewVehicle] = useState({
    companyEmail: localStorage.getItem("userEmail"),
    type: "",
    dailyPrice: "",
    availabilityStart: null,
    availabilityEnd: null,
    additionalServices: [],
    availabilityDates: [],
  });
  const [additionalService, setAdditionalService] = useState("");

  useEffect(() => {
    const getAllVehicles = async () => {
      if (
        localStorage.getItem("userRoles")?.includes("COMPANY") &&
        localStorage.getItem("accessToken")
      ) {
        const response = await api.get(
          `/vehicle/company-all?companyEmail=${localStorage.getItem(
            "userEmail"
          )}`
        );
        setVehicles(response.data);
      }
    };
    getAllVehicles();
  }, []);

  const setMinMaxDates = (dates) => {
    if (dates.length === 0) {
      return { availabilityStart: null, availabilityEnd: null };
    }

    let minDate = dayjs(dates[0]);
    let maxDate = dayjs(dates[0]);

    for (let i = 1; i < dates.length; i++) {
      let currentDate = dayjs(dates[i]);
      if (currentDate.isBefore(minDate)) {
        minDate = currentDate;
      }
      if (currentDate.isAfter(maxDate)) {
        maxDate = currentDate;
      }
    }

    return {
      availabilityStart: minDate.toDate(),
      availabilityEnd: maxDate.toDate(),
    };
  };

  const handleAddVehicle = async () => {
    if (localStorage.getItem("accessToken")) {
      const { availabilityStart, availabilityEnd } = setMinMaxDates(
        newVehicle.availabilityDates
      );

      const vehicleToAdd = {
        ...newVehicle,
        availabilityStart,
        availabilityEnd,
      };

      setVehicles([...vehicles, { id: vehicles.length + 1, ...vehicleToAdd }]);
      setNewVehicle({
        companyEmail: localStorage.getItem("userEmail"),
        type: "",
        dailyPrice: "",
        availabilityStart: null,
        availabilityEnd: null,
        additionalServices: [],
        availabilityDates: [],
      });

      const response = await api.post("/vehicle/add", vehicleToAdd);
      toast.success(response.data.message);
    }
  };

  const handleDeleteVehicle = async (id) => {
    if (localStorage.getItem("accessToken")) {
      const response = await api.delete(`/vehicle/delete/${id}`);
      setVehicles(vehicles.filter((vehicle) => vehicle.id !== id));
      toast.success(response.data);
    }
  };

  const handleAddAdditionalService = () => {
    if (localStorage.getItem("accessToken")) {
      setNewVehicle({
        ...newVehicle,
        additionalServices: [
          ...newVehicle.additionalServices,
          additionalService,
        ],
      });
      setAdditionalService("");
    }
  };

  const handleDeleteAdditionalService = (service) => {
    if (localStorage.getItem("accessToken")) {
      setNewVehicle({
        ...newVehicle,
        additionalServices: newVehicle.additionalServices.filter(
          (s) => s !== service
        ),
      });
    }
  };

  return (
    <Box sx={{ p: 3 }}>
      {userPermissionRoles(["COMPANY", "TEST"]) ? (
        <>
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
              label="Daily Price"
              value={newVehicle.dailyPrice}
              onChange={(e) =>
                setNewVehicle({ ...newVehicle, dailyPrice: e.target.value })
              }
              fullWidth
              sx={{ mb: 2 }}
            />
            <Box display="flex" alignItems="center" sx={{ mb: 2 }}>
              <DatePicker
                label="Available date start"
                value={newVehicle.availabilityStart}
                onChange={(date) =>
                  setNewVehicle({ ...newVehicle, availabilityStart: date })
                }
                renderInput={(params) => <TextField {...params} fullWidth />}
              />
              <Typography variant="body1" sx={{ mx: 2 }}>
                -
              </Typography>
              <DatePicker
                label="Available date end"
                value={newVehicle.availabilityEnd}
                onChange={(date) =>
                  setNewVehicle({ ...newVehicle, availabilityEnd: date })
                }
                renderInput={(params) => <TextField {...params} fullWidth />}
                minDate={newVehicle.availabilityStart}
              />
            </Box>
            <TextField
              label="Additional Service"
              value={additionalService}
              onChange={(e) => setAdditionalService(e.target.value)}
              fullWidth
              sx={{ mb: 2 }}
            />
            <Button
              variant="contained"
              onClick={handleAddAdditionalService}
              sx={{ mb: 2 }}
            >
              Add Additional Service
            </Button>
            <List>
              {newVehicle.additionalServices.map((service, index) => (
                <ListItem key={index}>
                  <ListItemText primary={service} />
                  <ListItemSecondaryAction>
                    <IconButton
                      edge="end"
                      onClick={() => handleDeleteAdditionalService(service)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </ListItemSecondaryAction>
                </ListItem>
              ))}
            </List>
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
                  secondary={`Daily Price: ${
                    vehicle.dailyPrice
                  }, Availability: ${dayjs(vehicle.availabilityStart).format(
                    "DD/MM/YYYY"
                  )} to ${dayjs(vehicle.availabilityEnd).format(
                    "DD/MM/YYYY"
                  )}, Additional Services: ${vehicle.additionalServices.join(
                    ", "
                  )}`}
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
        </>
      ) : (
        <div> You do not have a permission. Please log in.</div>
      )}
    </Box>
  );
}

export default VehiclesPage;
