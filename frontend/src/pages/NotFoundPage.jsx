import React from "react";
import { Container, Typography, Button } from "@mui/material";

function NotFoundPage() {
  return (
    <Container
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        minHeight: "100vh",
        textAlign: "center",
      }}
    >
      <Typography
        variant="h1"
        sx={{
          fontSize: "4rem",
          fontWeight: "bold",
          marginBottom: 2,
        }}
      >
        404
      </Typography>
      <Typography
        variant="h5"
        sx={{
          fontSize: "1.5rem",
          marginBottom: 4,
        }}
      >
        Page Not Found
      </Typography>
      <Button
        variant="contained"
        color="primary"
        href="/"
        sx={{
          marginTop: 2,
        }}
      >
        Go to Home
      </Button>
    </Container>
  );
}

export default NotFoundPage;
