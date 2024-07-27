<img width="842" alt="image" src="https://github.com/user-attachments/assets/162a4ee0-949d-4357-a3c7-be131a5dc097"># Vehicle Rental Portal

The portal includes many features e.g. registration, secure login, rent a vehicle, manage vehicle, view ...

### Key Features

---

1. **User/Company Registration and Login:**
   - **Registration:** New users must register as a user or company by using Keycloak. In this way, the membership system has been made more secure.
     <img width="1099" alt="image" src="https://github.com/user-attachments/assets/cf8ca350-af87-4d45-bbd1-d1b877b0be7b">
     <img width="1099" alt="image" src="https://github.com/user-attachments/assets/aa26bedc-1417-46f8-a9e8-12a92af5d365">


   - **Login:** Registered customers can log in by entering their credentials. Keycloak is activated on login, generating a temporary token unique to the user. Until expiration, the token is periodically renewed by refresh-token.
     <img width="498" alt="image" src="https://github.com/user-attachments/assets/21526fe5-012f-4b10-bb90-d16eaf5b10ec">

2. **Rent a Vehicle**
   - **Search vehicle:** Users can search and list vehicles.
     <img width="842" alt="image" src="https://github.com/user-attachments/assets/69a21909-71b8-47da-b175-21dc573d6494">

   - **Display Reservations:** Users can display their reservations.
   - **Update Informations:** Users and companies can update their informations.
     <img width="842" alt="image" src="https://github.com/user-attachments/assets/2ba9c142-ff28-42e5-a3e5-4a689c3a48b8">
     <img width="842" alt="image" src="https://github.com/user-attachments/assets/bf0bcf23-a7a3-4384-a68b-67f213a02f25">

   - **Add Vehicle:** Compaines can add and delete vehicle.
     <img width="842" alt="image" src="https://github.com/user-attachments/assets/a7310514-120f-4725-b9b2-9a314d92577a">

3. **Manage Vehicle**
   -  
4. **Validation and Error Handling:**
   - The system ensures that withdrawals are completed only if the client has sufficient funds. Insufficient funds will result in the transaction being rejected and an error message. Completed transactions are also returned to the user as an information message. Like these...
     <img width="397" alt="image" src="https://github.com/user-attachments/assets/24a5c048-2013-4472-bf41-5f334b423460">
     <img width="397" alt="image" src="https://github.com/user-attachments/assets/f64f1ace-e4aa-42fa-8b90-d78b152f5f27">
  
### Prerequisites

---

- Java SDK 17
- Maven
- Docker
- Node minimum version → v21.6.1
- PostgreSQL

### Installation

---

#### Cloning the Repository

Once Git is installed, clone the repository to your local machine:

1. Open a terminal or command prompt.

2. Navigate to the directory where you want to clone the repository.

3. Run the following command:
```
https://github.com/aftekardic/vehicle-rental-portal.git
```

4. Navigate into the cloned repository:

```
cd vehicle-rental-portal
```

#### Running the Application

1. Open the project in your IDE.

2.  Run the application:

    - **Backend**
      
      - Additional Setup

         1. Configure your database settings in backend/src/main/resources/application.properties.

         2. Ensure you have the necessary environment variables set up for database access and other configurations.

      - Run backend application
        ```
        cd backend
        mvn clean install
        mvn spring-boot:run
        ```
        or you can run from BackendApplication.java run button.

    - **Frontend**

      - Additional Setup

         1. Firstly you can set the .env file for frontend requests.

         2. Open the .env file in the frontend home directory and configure it according to your needs. After you can run the frontend application.

      - Run frontend application
        ```
        cd frontend
        npm run start
        ```

### API Usage

---

If you are using backend only, you should first get a token from the /authenticate endpoint, then you can use other endpoints by adding `Bearer <your_token>` to the Authorization of the Header.

#### Auth Controller
- POST /auth/login: Logs in the user.
- POST /auth/register-company: Registers a new company.
- POST /auth/register-user: Registers a new user.
- POST /auth/logout: Logs out the user.
- POST /auth/refresh-token: Refreshes the authentication token.

#### Company Controller
- GET /company/info/{userEmail}: Retrieves company information by user email.
- PUT /company/update/{userEmail}: Updates company information by user email.

#### Keycloak User Controller
- PUT /kc/updateUser/{userEmail}: Updates Keycloak user information by user email.

#### Reservation Controller
- GET /reservation/reserved/{userEmail}: Retrieves reserved vehicle information by user email.
- POST /reservation/book: Books an available vehicle.
- DELETE /reservation/delete/{id}: Deletes a reservation.

#### User Controller
- GET /user/info/{userEmail}: Retrieves user information by user email.
- PUT /user/update/{userEmail}: Updates user information by user email.

#### Vehicle Controller
- GET /vehicle/all: Retrieves all vehicles.
- POST /vehicle/specific-all: Retrieves all specific vehicles based on given criteria.
- GET /vehicle/company-all: Retrieves all vehicles of a company by company email.
- POST /vehicle/add: Adds a new vehicle.
- DELETE /vehicle/delete/{id}: Deletes a vehicle by ID.

### Security Configurations

---

This project includes security configurations to ensure proper authentication and authorization for accessing various endpoints. Here's an overview of the security components and their functionalities:

1. **Internal Server Exception:**

2. **Auth Exception:**
   
3. **KeycloakJwtRolesConverter:**
   - Implements Converter<Jwt, Collection<GrantedAuthority>> for Spring Security.
   - Extracts roles from JWT provided by Keycloak and converts them into a collection of GrantedAuthority.
   - Validates roles within the JWT using realm_access and resource_access fields.
   - Constructs appropriate GrantedAuthority objects based on the keycloak.client-id property.

4. **Web Security Configuration:**
   - The WebSecurityConfiguration class is a Spring configuration that manages security within the application. It enables web security with annotations and defines access rules using HttpSecurity. The configuration disables CSRF protection for stateless APIs and specifies roles (hasAnyRole) required to access endpoints like /api/v1/cv/**, /api/v1/user/**, and /api/v1/chat/**.

   - Endpoints under /auth/** are exempt from authentication requirements (permitAll). Error handling for authentication issues (authenticationEntryPoint) and access denial (accessDeniedHandler) is also configured.

   - JWT tokens issued by Keycloak are validated using a JwtDecoder configured with the token issuer URL (tokenIssuerUrl). The configuration converts JWT claims into Spring Security GrantedAuthority objects using JwtAuthenticationConverter and DelegatingJwtGrantedAuthoritiesConverter.

   - Additionally, CORS (Cross-Origin Resource Sharing) is enabled to allow requests from http://localhost:3000, supporting credentials, headers, and methods as specified.

   - This setup ensures that the application's endpoints are protected based on defined roles, integrates seamlessly with Keycloak for token validation, and manages cross-origin requests securely.
   
**_Feel free to customize these configurations according to your project's specific requirements and security policies. If you have any questions or need further assistance, please let me know!_**

## Conclusion

In essence, the CV Project exemplifies a secure and user-centric application environment where advanced functionalities are supported by robust security measures. By leveraging Keycloak for authentication, implementing precise access controls, and integrating error handling mechanisms, the project ensures both user data protection and operational efficiency. This comprehensive approach not only enhances user trust but also underscores the project's commitment to delivering a secure and reliable service.

If you need further details or adjustments to the conclusion, feel free to let me know!
