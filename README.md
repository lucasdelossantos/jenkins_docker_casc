# Jenkins Docker CASC

This repository contains the necessary files and configurations to set up Jenkins using Docker and Configuration as Code (CASC).

## Prerequisites

Before getting started, make sure you have the following installed:

- Docker
- Docker Compose

## Getting Started

To get started with Jenkins Docker CASC, follow these steps:

1. Clone this repository to your local machine.
2. Navigate to the repository directory.
3. Run the following command to start Jenkins:

  ```bash
  docker-compose up -d
  ```

4. Access Jenkins by opening a web browser and navigating to `http://localhost:8080`.
5. Log into Jenkins using admin:admin

## Configuration

All Jenkins configurations are managed using CASC. The main job seed is collected from [jenkins_job_dsl](https://github.com/lucasdelossantos/jenkins_jobs_dsl) repo.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Automated Builds
Automated builds for pushes to CircleCI project and uploads new Docker image to [jenkins_casc](https://hub.docker.com/repository/docker/lucasdls/jenkins-casc/general) on DockerHub. 