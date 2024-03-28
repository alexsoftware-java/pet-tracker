# Pet Tracking Application

The Pet Tracking Application is a Java Spring Boot project for managing and tracking pets (dogs and cats) using different types of trackers. It provides REST API endpoints to receive pet tracking data, store the data, and query stored data.

## Requirements

- Java 17
- Spring Boot 2.x
- Gradle

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your_username/pet-tracking-app.git
    ```

2. Navigate to the project directory:

    ```bash
    cd pet-tracking-app
    ```

3. Build the project using Gradle:

    ```bash
    ./gradlew build
    ```

4. Run the application:

    ```bash
    ./gradlew bootRun
    ```

## Usage

The following endpoints are available:

### 1. Receive Pet Data

Endpoint: `POST /api/pets`

Receives pet tracking data.

Example:

```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "petType": "CAT",
  "trackerType": "SMALL_CAT",
  "ownerId": 1,
  "inZone": true,
  "lostTracker": false
}' http://localhost:8080/api/pets
```

### 2. Get All Pets By Owner ID

Endpoint: GET /api/pets/owner/{ownerId}
Gets all pets for a specific owner ID.
Example:
```bash
curl -X GET http://localhost:8080/api/pets/owner/1
```

### 3. Get All Pets Outside Zone

Endpoint: GET /api/pets/outside
Gets all pets outside the power saving zone.

Example:
```
bash
curl -X GET http://localhost:8080/api/pets/outside
```