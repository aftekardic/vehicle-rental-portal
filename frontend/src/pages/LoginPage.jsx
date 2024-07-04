import React, { useEffect, useState } from "react";
import {
  Container,
  Box,
  Typography,
  TextField,
  Button,
  Link,
} from "@mui/material";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import api from "../services/api";
import { getUserRoles, parseJwt } from "../services/jwtParser";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  const handleLogin = async () => {
    let hasError = false;
    if (!email) {
      setEmailError(true);
      hasError = true;
    } else {
      setEmailError(false);
    }

    if (!password) {
      setPasswordError(true);
      hasError = true;
    } else {
      setPasswordError(false);
    }

    if (hasError) return;

    try {
      const response = await api.post("/auth/login", {
        email: email,
        password: password,
      });
      const { data } = response;
      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);
      localStorage.setItem("userEmail", email);
      localStorage.setItem(
        "userRoles",
        getUserRoles(parseJwt(data.accessToken))
      );

      toast.success(data.message, {
        onClose: () => {
          navigate("/");
        },
      });
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  useEffect(() => {
    localStorage.getItem("accessToken") !== null && navigate("/");
  }, [navigate]);

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <Box component="form" noValidate sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoComplete="email"
            autoFocus
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            error={emailError}
            helperText={emailError ? "Email is required *" : ""}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            autoComplete="current-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            error={passwordError}
            helperText={passwordError ? "Password is required *" : ""}
          />
          <Button
            type="button"
            fullWidth
            variant="contained"
            color="primary"
            sx={{ mt: 3, mb: 2 }}
            onClick={handleLogin}
          >
            Sign In
          </Button>
          <Box display="flex" justifyContent="flex-start">
            <Link href="/" variant="body2">
              Don't have an account? Go to home page
            </Link>
          </Box>
        </Box>
      </Box>
    </Container>
  );
}

export default LoginPage;
