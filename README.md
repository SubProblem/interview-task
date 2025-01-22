## This project consists of two applications:

1. **Producer**: A service responsible for producing messages to Kafka topics.
2. **Consumer**: A service responsible for consuming messages from Kafka topics and storing them in PostgreSQL.

Both applications are using Docker images for running Kafka and PostgreSQL services.

## Prerequisites

Before running the applications, ensure that you have the following tools installed:

- Docker
- Docker Compose
- PostgreSQL (Running within Docker)
- Apache Kafka (Running within Docker)

## Getting Started

### 1. Run the Docker-Compose

```
docker-compose up
```

### 2. Install Common Package

To build and install the **common package** (which contains shared constants), use the following command

- On macOS/Linux, use the following command:

  ```bash
  mvn clean install

- On Windows, use the following command:

    ```bash
  .\mvnw clean install
    ```