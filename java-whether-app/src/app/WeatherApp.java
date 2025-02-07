package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {
    private static final int SPRITE_WIDTH = 2641 / 5;
    private static final int SPRITE_HEIGHT = 1813 / 4;
    private static final int TRIM_LEFT = 0;
    private static final int TRIM_TOP = 0;

    private JFrame frame;
    private CustomPanel panel;
    private static BufferedImage spriteSheet;
    private JButton cityButton;
    private BufferedImage selectedSprite;

    private int width = 750;
    private int height = 300;
    private Weather weather = Weather.Sunny;  // Default weather
    private String location = "London";
    private double temperatureCelsius = 0;
    private double temperatureFahrenheit = 0;

    public WeatherApp() {
        panel = new CustomPanel();
        frame = new JFrame("Weather App");
        setupUI();
    }

    private void setupUI() {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Weather App");
        frame.pack();
        frame.setSize(width, height);
        frame.setVisible(true);

        loadFrameIcon();

        // Initialize and position the button
        cityButton = new JButton(location);
        cityButton.setBounds(width / 2 + 125, height/ 4 - 30, 200, 30); // Position next to location
        cityButton.addActionListener(e -> showCitySelectionDialog());
        panel.add(cityButton);
    }


    private void loadFrameIcon() {
        try (InputStream imageStream = WeatherApp.class.getClassLoader().getResourceAsStream("assets/logo.png")) {
            if (imageStream != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageStream));
                frame.setIconImage(icon.getImage());
            } else {
                System.err.println("Image not found: logo.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSpriteSheet() {
        try (InputStream imageStream = WeatherApp.class.getClassLoader().getResourceAsStream("assets/default/weather.png")) {
            if (imageStream == null) {
                throw new IOException("weather.jpg not found!");
            }
            spriteSheet = ImageIO.read(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        loadSpriteSheet();
        if (spriteSheet == null) {
            System.err.println("Sprite sheet not loaded!");
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                fetchWeatherData();
                return null;
            }

            @Override
            protected void done() {
                updateUIWithWeather();
            }
        };
        worker.execute();
    }

    private void updateUIWithWeather() {
        if (spriteSheet == null) return;

        int croppedWidth = SPRITE_WIDTH - TRIM_LEFT;
        int croppedHeight = SPRITE_HEIGHT - TRIM_TOP;

        if (spriteSheet.getWidth() >= (TRIM_LEFT + croppedWidth) &&
                spriteSheet.getHeight() >= (TRIM_TOP + croppedHeight)) {
            selectedSprite = spriteSheet.getSubimage(TRIM_LEFT, TRIM_TOP, croppedWidth, croppedHeight);
        } else {
            System.err.println("Trimmed area exceeds image bounds!");
            return;
        }

        int row = weather.getRow();
        int col = weather.getCol();
        BufferedImage weatherIcon = getWeatherIcon(row, col);

        panel.setSelectedSprite(weatherIcon);
        panel.setTemperature(temperatureCelsius, temperatureFahrenheit);
        panel.setLocation(location);
        panel.setWeather(weather);

        // Update city button text to match the new location
        cityButton.setText(location); // Use the stored button reference

        panel.repaint();
    }

    private BufferedImage getWeatherIcon(int row, int col) {
        int x = col * SPRITE_WIDTH;
        int y = row * SPRITE_HEIGHT;

        if (x + SPRITE_WIDTH <= spriteSheet.getWidth() && y + SPRITE_HEIGHT <= spriteSheet.getHeight()) {
            return spriteSheet.getSubimage(x, y, SPRITE_WIDTH, SPRITE_HEIGHT);
        } else {
            System.err.println("Weather icon out of bounds, returning default.");
            return spriteSheet.getSubimage(0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
        }
    }

    private void fetchWeatherData() {
        try {
            String urlString = "https://api.open-meteo.com/v1/forecast?latitude=51.5085&longitude=-0.1257&hourly=temperature_2m,precipitation,weathercode";
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject hourly = jsonResponse.getJSONObject("hourly");
                temperatureCelsius = hourly.getJSONArray("temperature_2m").getDouble(0);
                temperatureFahrenheit = (temperatureCelsius * 9 / 5) + 32;

                int weatherCode = hourly.getJSONArray("weathercode").getInt(0);
                weather = Weather.toWeather(weatherCode);

            } else {
                System.out.println("API request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCitySelectionDialog() {
        String[] cities = {
                "Amsterdam, Netherlands", "Athens, Greece", "Bangkok, Thailand", "Barcelona, Spain",
                "Berlin, Germany", "Buenos Aires, Argentina", "Cape Town, South Africa", "Chicago, USA",
                "Copenhagen, Denmark", "Dubai, UAE", "Hong Kong, China", "Istanbul, Turkey",
                "Kuala Lumpur, Malaysia", "Lagos, Nigeria", "London, United Kingdom", "Madrid, Spain",
                "Melbourne, Australia", "Moscow, Russia", "Mumbai, India", "New York, USA",
                "Paris, France", "Rome, Italy", "San Francisco, USA", "Seoul, South Korea",
                "Singapore, Singapore", "Sydney, Australia", "Tokyo, Japan", "Toronto, Canada",
                "Vienna, Austria", "Zurich, Switzerland"
        };

        String cityAndCountry = (String) JOptionPane.showInputDialog(
                frame,
                "Select a city and country:",
                "City Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                cities,
                location
        );

        if (cityAndCountry != null && !cityAndCountry.isEmpty()) {
            String city = cityAndCountry.split(",")[0].trim();  // Extract city name
            updateWeatherForCity(city);
        }
    }

    private void updateWeatherForCity(String city) {
        try {
            // Encode the city name to handle spaces and special characters
            String encodedCity = java.net.URLEncoder.encode(city, "UTF-8");
            String geoUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + encodedCity + "&count=1";

            HttpURLConnection geoConnection = (HttpURLConnection) new URL(geoUrl).openConnection();
            geoConnection.setRequestMethod("GET");

            int geoResponseCode = geoConnection.getResponseCode();
            if (geoResponseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder geoResponse = new StringBuilder();
                try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(geoConnection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        geoResponse.append(line);
                    }
                }

                JSONObject geoJsonResponse = new JSONObject(geoResponse.toString());
                if (geoJsonResponse.getJSONArray("results").length() > 0) {
                    JSONObject cityData = geoJsonResponse.getJSONArray("results").getJSONObject(0);
                    double latitude = cityData.getDouble("latitude");
                    double longitude = cityData.getDouble("longitude");

                    // Now fetch the weather data using the latitude and longitude
                    String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m,weathercode";
                    HttpURLConnection weatherConnection = (HttpURLConnection) new URL(weatherUrl).openConnection();
                    weatherConnection.setRequestMethod("GET");

                    int weatherResponseCode = weatherConnection.getResponseCode();
                    if (weatherResponseCode == HttpURLConnection.HTTP_OK) {
                        StringBuilder weatherResponse = new StringBuilder();
                        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(weatherConnection.getInputStream()))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                weatherResponse.append(line);
                            }
                        }

                        JSONObject weatherJsonResponse = new JSONObject(weatherResponse.toString());
                        JSONObject hourly = weatherJsonResponse.getJSONObject("hourly");
                        temperatureCelsius = hourly.getJSONArray("temperature_2m").getDouble(0);
                        temperatureFahrenheit = (temperatureCelsius * 9 / 5) + 32;

                        int weatherCode = hourly.getJSONArray("weathercode").getInt(0);
                        weather = Weather.toWeather(weatherCode);
                        location = city;

                        // Update UI on the EDT
                        SwingUtilities.invokeLater(() -> updateUIWithWeather());
                    } else {
                        System.out.println("Weather API request failed with response code: " + weatherResponseCode);
                    }
                } else {
                    System.out.println("City not found in geocoding response.");
                }
            } else {
                System.out.println("Geocoding API request failed with response code: " + geoResponseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        WeatherApp app = new WeatherApp();
        app.start();
    }

    // Custom Panel for displaying weather
    class CustomPanel extends JPanel {
        private BufferedImage selectedSprite;
        private String location;
        private Weather weather;
        private double temperatureCelsius = 0;
        private double temperatureFahrenheit = 0;

        public void setSelectedSprite(BufferedImage sprite) {
            this.selectedSprite = sprite;
            repaint();
        }

        public void setTemperature(double temperatureCelsius, double temperatureFahrenheit) {
            this.temperatureCelsius = temperatureCelsius;
            this.temperatureFahrenheit = temperatureFahrenheit;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setWeather(Weather weather) {
            this.weather = weather;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (selectedSprite != null) {
                int leftHalfWidth = getWidth() / 2;
                g.drawImage(selectedSprite, 0, 0, leftHalfWidth, getHeight(), this);
            }

            g.setColor(Color.BLACK);
            Font font = new Font("Ariel", Font.BOLD, 21);
            g.setFont(font);

            String locationText = "Location: ";
            String temperatureText = "Temperature: " + String.format("%.1f", temperatureCelsius) + "°C (" + String.format("%.1f", temperatureFahrenheit) + "°F)";
            String weatherText = (weather != null) ? "Weather: " + weather : "Weather: Unknown";

            FontMetrics fm = g.getFontMetrics();

            int x = getWidth() / 2 + 20; // Starting position of the text
            int heightQuarter = getHeight() / 4;

            // Draw the location text
            g.drawString(locationText, x, heightQuarter);

            // Draw the other texts
            g.drawString(temperatureText, x, 2 * heightQuarter);
            g.drawString(weatherText, x, 3 * heightQuarter);
        }
    }
}
