export const userPermissionRoles = (roles) => {
  const accessToken = localStorage.getItem("accessToken");
  const userRoles = localStorage.getItem("userRoles");

  return (
    accessToken === null || roles.some((role) => userRoles?.includes(role))
  );
};
