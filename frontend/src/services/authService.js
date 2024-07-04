import api from "./api";
import { getExp, parseJwt } from "./jwtParser";

const checkTokenValidity = () => {
  const token = localStorage.getItem("accessToken");
  if (token) {
    const tokenExpiration = getExp(parseJwt(token));
    const now = Math.floor(Date.now() / 1000);
    if (tokenExpiration - now < 300) {
      renewToken();
    }
  }
};

const renewToken = async () => {
  try {
    const refreshToken = localStorage.getItem("refreshToken");
    if (refreshToken) {
      const { data } = await api.post("/auth/refresh-token", { refreshToken });
      localStorage.setItem("accessToken", data.accessToken);
    } else {
      window.location.href = "/";
    }
  } catch (error) {
    console.error("Token renewal failed", error);
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("userRoles");
    localStorage.removeItem("userEmail");

    window.location.href = "/login";
  }
};

export { checkTokenValidity };
