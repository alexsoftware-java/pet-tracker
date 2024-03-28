# Pet Tracking Application

The Pet Tracking Application is a pretty simple, 3-hours, Java Spring Boot project for managing and tracking pets (dogs and cats)

## Requirements

- Java 17
- Spring Boot 3.2
- Gradle

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/alexsoftware-java/pet-tracker.git
    ```

2. Navigate to the project directory:

    ```bash
    cd pet-trackier
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

You can check how to interact with the API using JSON payloads, and the full list of methods with examples via [Swagger UI](http://localhost:8080/swagger-ui/index.html).

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
curl http://localhost:8080/api/pets/owner/1
[
                              {
                                "petType": "CAT",
                                "trackerType": "SMALL_CAT",
                                "ownerId": 1,
                                "inZone": true,
                                "lostTracker": false
                              }
                            ]
```

### 3. Get All Pets Outside Zone

Endpoint: GET /api/pets/outside
Gets all pets outside the power saving zone.

Example:
```
bash
curl -X GET http://localhost:8080/api/pets/outside
```

Ensure that you include the appropriate headers, such as `Content-Type: application/json`, when making the request.

---------------------------

## Support

If you encounter any issues or have questions, please feel free to contact me at [alex.khlizov@gmail.com](mailto:alex.khlizov@gmail.com).