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
GET /api/v1/users
PUT /api/v1/users
DELETE /api/v1/users
GET /api/v1/booking
POST /api/v1/booking
GET /api/v1/booking/{{bookingId}}
DELETE /api/v1/booking/{{bookingId}}
POST /api/v1/login
POST /api/v1/register
GET /api/v1/rooms
POST /api/v1/rooms
GET /v1/rooms/{{roomId}}
PUT /api/v1/rooms/{{roomId}}
DELETE /api/v1/rooms/{{roomId}}



