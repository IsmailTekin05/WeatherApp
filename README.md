# Java Weather App

A simple Java-based weather application that fetches real-time weather data and displays it in a user-friendly interface.

## Features

- Fetch real-time weather data from an API
- Display temperature, humidity, and weather conditions
- User-friendly interface
- Error handling for API requests

## Technologies Used

- **Java** (Core application logic)
- **Spring Boot** (Backend framework, if applicable)
- **OpenWeatherMap API** (Weather data provider)
- **Maven** (Dependency management)

## Installation

### Prerequisites
- Java 17 or later
- Maven

### Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/IsmailTekin05/java-weather-app.git
   cd java-weather-app
   ```
2. Install dependencies:
   ```sh
   mvn clean install
   ```
   or
   ```sh
   ./gradlew build
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
   or
   ```sh
   java -jar target/java-weather-app.jar
   ```

## Configuration

1. Obtain an API key from [OpenWeatherMap](https://openweathermap.org/api).
2. Set up your API key in the application properties file:
   ```properties
   weather.api.key=YOUR_API_KEY_HERE
   ```

## Usage

- Start the application and enter a city name to get weather details.
- The app fetches real-time weather data and displays temperature, humidity, and conditions.

## Contribution

Contributions are welcome! Feel free to open an issue or submit a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
