import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { ToastContainer } from "react-toastify";
const theme = createTheme();
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <LocalizationProvider dateAdapter={AdapterDayjs}>
    <ThemeProvider theme={theme}>
      <BrowserRouter>
        <React.StrictMode>
          <App />
          <ToastContainer
            position="top-right"
            autoClose={1000}
            hideProgressBar={false}
            newestOnTop={false}
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
          />
        </React.StrictMode>
      </BrowserRouter>
    </ThemeProvider>
  </LocalizationProvider>
);
