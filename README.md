# Hotel Booking API

This is an API for hotel booking developed using Spring Boot, Spring Security, Spring Validation, Spring Web, PostgreSQL, Flyway, and JSON Web Tokens (JWT).

## Getting Started

These instructions will help you set up and run the application locally.

### Prerequisites

Make sure you have the following software installed on your machine:

- [Docker](https://www.docker.com/get-started)

### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/MrShims/HotelBooking-api.git
2. Navigate to the Project Directory:
    ```shell
    cd HotelBooking-api
3. Start the Application using Docker Compose:
   ```shell
   docker-compose up -d
 ### Testing the API   
You can use Swagger UI or Postman to test the API. Below are some of the available endpoints:
 ```shell 
GET /api/v1/users - get user profile.
PUT /api/v1/users - edit user profile.
DELETE /api/v1/users - delete user.

GET /api/v1/booking - get list of all booking. @RequestParam startDate required = false.
POST /api/v1/booking - create new booking.
GET /api/v1/booking/{{bookingId}} - get booking by id.
DELETE /api/v1/booking/{{bookingId}} - delete booking.

POST /api/v1/login - user authentication.
POST /api/v1/register - create new user.

GET /api/v1/rooms - get list of all rooms. RoomFilterDto for filter rooms.
POST /api/v1/rooms - create new room.
GET /v1/rooms/{{roomId}} - get room by id.
PUT /api/v1/rooms/{{roomId}} - edit room.
DELETE /api/v1/rooms/{{roomId}} - delete room.



