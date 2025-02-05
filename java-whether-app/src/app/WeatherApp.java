package app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class WeatherApp {
    // Frames and panels
    private JFrame frame;
    private CustomPanel panel;
    private static BufferedImage spriteSheet;
    private BufferedImage selectedSprite;

    private final int SPRITE_HEIGHT = 750;
    private final int SPRITE_WIDTH = 400;

    // Measurements
    private int height;
    private int width;

    // Weather
    private Weather weather;

    public WeatherApp() {
        width = 600; // Adjust size
        height = 250;
        weather = Weather.Sunny;

        panel = new CustomPanel();
        frame = new JFrame("Weather App");

        createPanel();
        createFrame();
    }

    private void createPanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setPreferredSize(new Dimension(width, height));
        panel.setBackground(weather.getColor());
        panel.setLayout(null);
    }

    private void createFrame() {
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Weather App");

        frame.pack();
        frame.setSize(width, height); // Set size after pack
        frame.setVisible(true);

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
        try (InputStream imageStream = WeatherApp.class.getClassLoader().getResourceAsStream("assets/default/weather.jpg")) {
            if (imageStream == null) {
                throw new IOException("weather.png not found!");
            }
            spriteSheet = ImageIO.read(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void start() {
        loadSpriteSheet();
        if (spriteSheet == null) {
            System.err.println("Sprite sheet not loaded!");
            return;
        }

        selectedSprite = spriteSheet.getSubimage(0, 0,SPRITE_WIDTH, SPRITE_HEIGHT);
        panel.setSelectedSprite(selectedSprite);
        panel.repaint();
    }

    public static void main(String[] args) {
        var w = new WeatherApp();
        w.start();
    }
}

class CustomPanel extends JPanel {
    private BufferedImage selectedSprite;

    public void setSelectedSprite(BufferedImage selectedSprite) {
        this.selectedSprite = selectedSprite;
        repaint(); // Force repaint when the image changes
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (selectedSprite != null) {
            g.drawImage(selectedSprite, 10, 10 , null);
        }
    }
}
