import React, { useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import VehiclesPage from "./pages/VehiclesPage";
import RezervationPage from "./pages/RezervationPage";
import UserProfilePage from "./pages/UserProfilePage";
import Layout from "./components/Layout";
import UserRegistirationPage from "./pages/UserRegistirationPage";
import CompanyRegistrationPage from "./pages/CompanyRegistrationPage";
import NotFoundPage from "./pages/NotFoundPage";
import LoginPage from "./pages/LoginPage";
import { checkTokenValidity } from "./services/authService";

function App() {
  useEffect(() => {
    const interval = setInterval(() => {
      checkTokenValidity();
    }, 60000);
    return () => clearInterval(interval);
  }, []);

  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/company-registration"
        element={<CompanyRegistrationPage />}
      />
      <Route path="/user-registration" element={<UserRegistirationPage />} />
      <Route path="/" element={<Layout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/vehicles" element={<VehiclesPage />} />
        <Route path="/reservations" element={<RezervationPage />} />
        <Route path="/profile" element={<UserProfilePage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Route>
    </Routes>
  );
}

export default App;
