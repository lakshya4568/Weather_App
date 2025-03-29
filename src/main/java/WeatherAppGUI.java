import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {
    private JSONObject weatherData;
    private boolean isDarkTheme = true;

    // UI Components
    private final JPanel mainPanel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton themeToggleButton;
    private JLabel weatherConditionImage;
    private JLabel temperatureText;
    private JLabel weatherDescriptionText;
    private JLabel humidityText;
    private JLabel windspeedText;
    private JLabel statusLabel;
    private JPanel searchPanel;
    private JPanel weatherPanel;
    private JPanel detailsPanel;

    // Colors
    private final Color darkBgColor = new Color(25, 30, 35);
    private final Color lightBgColor = new Color(240, 245, 250);
    private final Color darkAccentColor = new Color(45, 50, 60);
    private final Color lightAccentColor = new Color(220, 225, 235);
    private final Color darkTextColor = new Color(230, 230, 230);
    private final Color lightTextColor = new Color(40, 40, 40);
    private final Color primaryColor = new Color(66, 133, 244);
    
    public WeatherAppGUI() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel first
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        
        // Then apply theme
        applyTheme(isDarkTheme);

        // Initialize and add components
        initComponents();

        // Add the main panel to the frame
        add(mainPanel);
    }

    private void initComponents() {
        // Create a container panel with padding
        JPanel containerPanel = new JPanel(new BorderLayout(0, 20));
        containerPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        containerPanel.setOpaque(false);

        // Create search panel
        createSearchPanel();
        containerPanel.add(searchPanel, BorderLayout.NORTH);

        // Create weather info panel
        createWeatherPanel();
        containerPanel.add(weatherPanel, BorderLayout.CENTER);

        // Create details panel
        createDetailsPanel();
        containerPanel.add(detailsPanel, BorderLayout.SOUTH);

        // Add container to main panel
        mainPanel.add(containerPanel, BorderLayout.CENTER);

        // Add status label to the bottom
        statusLabel = new JLabel("Ready", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        // Create a rounded panel for search components
        RoundedPanel searchBoxPanel = new RoundedPanel(20, isDarkTheme);
        searchBoxPanel.setLayout(new BorderLayout(0, 0));
        searchBoxPanel.setBorder(new EmptyBorder(5, 15, 5, 5));

        // Search field
        searchTextField = new JTextField(15);
        searchTextField.setBorder(null);
        searchTextField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchTextField.setOpaque(false);

        // Search button
        searchButton = new JButton();
        ImageIcon searchIcon = loadIcon("src/assets/search.png", 20, 20);
        searchButton.setIcon(searchIcon);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setPreferredSize(new Dimension(40, 40));

        // Theme toggle button
        themeToggleButton = new JButton();
        ImageIcon themeIcon = loadIcon(isDarkTheme ? "src/assets/light_mode.png" : "src/assets/dark_mode.png", 24, 24);
        themeToggleButton.setIcon(themeIcon);
        themeToggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeToggleButton.setFocusPainted(false);
        themeToggleButton.setBorderPainted(false);
        themeToggleButton.setContentAreaFilled(false);
        themeToggleButton.setPreferredSize(new Dimension(45, 40));

        // Add components to search box panel
        searchBoxPanel.add(searchTextField, BorderLayout.CENTER);
        searchBoxPanel.add(searchButton, BorderLayout.EAST);

        // Add search box panel and theme toggle to main search panel
        searchPanel.add(searchBoxPanel, BorderLayout.CENTER);
        searchPanel.add(themeToggleButton, BorderLayout.EAST);

        // Add search functionality
        addSearchFunctionality();

        // Add theme toggle functionality
        addThemeToggleFunctionality();
    }

    private void createWeatherPanel() {
        weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));
        weatherPanel.setOpaque(false);

        // App logo/weather condition image panel
        JPanel imageContainerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imageContainerPanel.setOpaque(false);

        // Weather image with circular background
        CircularImagePanel circularImagePanel = new CircularImagePanel("src/assets/earth.jpeg", 185);
        weatherConditionImage = new JLabel();
        weatherConditionImage.setPreferredSize(new Dimension(185, 185));
        circularImagePanel.add(weatherConditionImage, BorderLayout.CENTER);
        imageContainerPanel.add(circularImagePanel);

        // Temperature text
        temperatureText = new JLabel("WEATHER APP", SwingConstants.CENTER);
        temperatureText.setFont(new Font("Segoe UI", Font.BOLD, 48));
        temperatureText.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Weather condition text
        weatherDescriptionText = new JLabel("Enter a location to get started", SwingConstants.CENTER);
        weatherDescriptionText.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        weatherDescriptionText.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to weather panel with spacing
        weatherPanel.add(Box.createVerticalGlue());
        weatherPanel.add(imageContainerPanel);
        weatherPanel.add(Box.createVerticalStrut(25));
        weatherPanel.add(temperatureText);
        weatherPanel.add(Box.createVerticalStrut(10));
        weatherPanel.add(weatherDescriptionText);
        weatherPanel.add(Box.createVerticalGlue());
    }

    private void createDetailsPanel() {
        detailsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        detailsPanel.setOpaque(false);

        // Humidity Panel
        RoundedPanel humidityPanel = new RoundedPanel(20, isDarkTheme);
        humidityPanel.setLayout(new BorderLayout(15, 5));
        humidityPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Wind Panel
        RoundedPanel windPanel = new RoundedPanel(20, isDarkTheme);
        windPanel.setLayout(new BorderLayout(15, 5));
        windPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Humidity components
        JLabel humidityImageLabel = new JLabel(loadIcon("src/assets/humidity.png", 40, 40));
        humidityText = new JLabel("<html><b style='font-size:16pt'>--</b><br><span style='font-size:11pt;color:#777777'>Humidity</span></html>");
        humidityText.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Wind components
        JLabel windImageLabel = new JLabel(loadIcon("src/assets/windspeed.png", 40, 40));
        windspeedText = new JLabel("<html><b style='font-size:16pt'>--</b><br><span style='font-size:11pt;color:#777777'>Wind Speed</span></html>");
        windspeedText.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        // Add components to panels
        humidityPanel.add(humidityImageLabel, BorderLayout.WEST);
        humidityPanel.add(humidityText, BorderLayout.CENTER);

        windPanel.add(windImageLabel, BorderLayout.WEST);
        windPanel.add(windspeedText, BorderLayout.CENTER);

        // Add panels to details panel
        detailsPanel.add(humidityPanel);
        detailsPanel.add(windPanel);
    }

    private void addSearchFunctionality() {
        searchButton.addActionListener(e -> searchWeather());

        // Also allow searching by pressing Enter
        searchTextField.addActionListener(e -> searchWeather());
    }

    private void searchWeather() {
        String userInput = searchTextField.getText().trim();

        // Validate input
        if (userInput.isEmpty()) {
            statusLabel.setText("Please enter a location");
            return;
        }

        // Show loading status
        statusLabel.setText("Fetching weather data...");
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        weatherDescriptionText.setText("Loading...");

        // Disable search while fetching
        searchButton.setEnabled(false);
        searchTextField.setEnabled(false);

        // Retrieve weather data in a separate thread to keep UI responsive
        SwingWorker<JSONObject, Void> worker = new SwingWorker<>() {
            @Override
            protected JSONObject doInBackground() {
                return WeatherApp.getWeatherData(userInput);
            }

            @Override
            protected void done() {
                try {
                    weatherData = get();
                    updateWeatherDisplay();
                    statusLabel.setText("Weather data updated");
                } catch (Exception ex) {
                    weatherDescriptionText.setText("Error fetching data");
                    statusLabel.setText("Error: Could not fetch weather data");
                    ex.printStackTrace();
                } finally {
                    // Re-enable search
                    searchButton.setEnabled(true);
                    searchTextField.setEnabled(true);
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        };

        worker.execute();
    }

    private void updateWeatherDisplay() {
        if (weatherData == null) {
            statusLabel.setText("Location not found");
            weatherDescriptionText.setText("Location not found");
            return;
        }

        // Update weather image based on condition
        String weatherCondition = (String) weatherData.get("weather_condition");
        weatherConditionImage.setIcon(loadIconForWeather(weatherCondition));

        // Update temperature
        double temperature = (double) weatherData.get("temperature");
        temperatureText.setText(String.format("%.1f°C", temperature));

        // Update weather condition
        weatherDescriptionText.setText(weatherCondition);

        // Update humidity
        long humidity = (long) weatherData.get("humidity");
        humidityText.setText(String.format("<html><b style='font-size:16pt'>%d%%</b><br><span style='font-size:11pt;color:#777777'>Humidity</span></html>", humidity));

        // Update windspeed
        double windspeed = (double) weatherData.get("windspeed");
        windspeedText.setText(String.format("<html><b style='font-size:16pt'>%.1f km/h</b><br><span style='font-size:11pt;color:#777777'>Wind Speed</span></html>", windspeed));
    }

    private ImageIcon loadIconForWeather(String condition) {
        String imagePath;

        switch (condition) {
            case "Clear":
                imagePath = "src/assets/clear.png";
                break;
            case "Cloudy":
                imagePath = "src/assets/cloudy.png";
                break;
            case "Rain":
                imagePath = "src/assets/rain.png";
                break;
            case "Snow":
                imagePath = "src/assets/snow.png";
                break;
            default:
                imagePath = "src/assets/earth.jpeg";
                break;
        }

        return loadIcon(imagePath, 180, 180);
    }

    private void addThemeToggleFunctionality() {
        themeToggleButton.addActionListener(e -> {
            isDarkTheme = !isDarkTheme;
            applyTheme(isDarkTheme);
            updateThemeButton();
            updateUIComponents();
        });
    }

    private void updateThemeButton() {
        ImageIcon icon = loadIcon(
                isDarkTheme ? "src/assets/light_mode.png" : "src/assets/dark_mode.png",
                24, 24
        );
        themeToggleButton.setIcon(icon);
    }

    private void updateUIComponents() {
        // Update component colors based on theme
        Color textColor = isDarkTheme ? darkTextColor : lightTextColor;
        temperatureText.setForeground(textColor);
        weatherDescriptionText.setForeground(textColor);
        statusLabel.setForeground(textColor);
        searchTextField.setForeground(textColor);

        // Update background colors
        mainPanel.setBackground(isDarkTheme ? darkBgColor : lightBgColor);

        // Update the UI for rounded panels
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void applyTheme(boolean dark) {
        try {
            if (dark) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                mainPanel.setBackground(darkBgColor);
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
                mainPanel.setBackground(lightBgColor);
            }
            SwingUtilities.updateComponentTreeUI(this);
            updateUIComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageIcon loadIcon(String resourcePath, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            if (image != null) {
                // Scale the image to requested size
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            }
        } catch (IOException e) {
            System.out.println("Could not find the resource: " + resourcePath);
        }

        // Return a default icon if image not found
        return createFallbackIcon(resourcePath, width, height);
    }

    private ImageIcon createFallbackIcon(String resourcePath, int width, int height) {
        BufferedImage fallbackImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fallbackImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Different colors based on icon type
        if (resourcePath.contains("light_mode")) {
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(4, 4, width - 8, height - 8);
        } else if (resourcePath.contains("dark_mode")) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillOval(4, 4, width - 8, height - 8);
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillArc(8, 4, width - 16, height - 8, 45, 180);
        } else if (resourcePath.contains("search")) {
            g2d.setColor(Color.GRAY);
            g2d.drawOval(4, 4, width - 16, height - 16);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawLine(width - 10, height - 10, width - 4, height - 4);
        } else {
            // Generic fallback
            g2d.setColor(isDarkTheme ? new Color(60, 60, 60) : new Color(200, 200, 200));
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(isDarkTheme ? new Color(80, 80, 80) : new Color(150, 150, 150));
            g2d.drawString(resourcePath.substring(resourcePath.lastIndexOf('/') + 1), 5, height / 2);
        }

        g2d.dispose();
        return new ImageIcon(fallbackImage);
    }

    // Rounded panel class
    class RoundedPanel extends JPanel {
        private final int radius;
        private final boolean isDark;

        public RoundedPanel(int radius, boolean isDark) {
            super();
            this.radius = radius;
            this.isDark = isDark;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color bgColor = isDarkTheme ? darkAccentColor : lightAccentColor;
            g2.setColor(bgColor);

            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        }
    }

    // Circular image panel
    class CircularImagePanel extends JPanel {
        private BufferedImage image;
        private int size;

        public CircularImagePanel(String imagePath, int size) {
            super(new BorderLayout());
            this.size = size;
            setOpaque(false);
            setPreferredSize(new Dimension(size, size));

            try {
                this.image = ImageIO.read(new File(imagePath));
            } catch (IOException e) {
                System.out.println("Could not load image: " + imagePath);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw circular background
            Color bgColor = isDarkTheme ? darkAccentColor : lightAccentColor;
            g2.setColor(bgColor);
            g2.fillOval(0, 0, size, size);

            // Draw circular border
            g2.setColor(isDarkTheme ? darkBgColor.brighter() : lightBgColor.darker());
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(0, 0, size - 1, size - 1);

            g2.dispose();
        }
    }
}
