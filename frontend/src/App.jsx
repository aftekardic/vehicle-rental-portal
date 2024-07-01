import React from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import VehiclesPage from "./pages/VehiclesPage";
import RezervationPage from "./pages/RezervationPage";
import UserProfilePage from "./pages/UserProfilePage";
import Layout from "./components/Layout";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route path="/home" element={<HomePage />} />
        <Route path="/vehicles" element={<VehiclesPage />} />
        <Route path="/reservation" element={<RezervationPage />} />
        <Route path="/profile" element={<UserProfilePage />} />
      </Route>
    </Routes>
  );
}

export default App;
