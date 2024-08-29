import com.formdev.flatlaf.FlatDarkLaf;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// inherit the Jframe class from the swing packages to our gui class
public class WeatherAppGUI extends  JFrame {
    private JSONObject weatherData; // this will hold our weatherdata
    // it's json object type beacuse the method that we are going to call will return json object




    // create the constructor of the same class
    public WeatherAppGUI() {
        super("Weather App");

        // configure gui to end the program's process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set the size of the gui in pixels
        setSize(450, 650);
        // load the gui at the center of the screen
        setLocationRelativeTo(null);

        // make out layout manager null to manually position our components within the gui app
        // like buttons, text fields, images etc.
        setLayout(null);

        // prevent any resize of our gui
        setResizable(false);

        // adding gui components
        guiComponents();
        //  darkTheme();
    }

    private void guiComponents() {
        // search field
        JTextField searchTextField = new JTextField();

        // set the location and size of our component
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setBorder(BorderFactory.createLineBorder(Color.black));

        // change the font style and size
        searchTextField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        add(searchTextField);

        // moving the button to the last in gui components as it will get updated when moving the code
        // that are underneath  when updating the gui;



        // weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/clear.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // temperature text
        JLabel temperatureText = new JLabel("10 ˚C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // font class can take these values for getting font style (name, Font style -> Bold, italics and Size(int));
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // weather condition Description
        JLabel weatherDescriptionText = new JLabel("Sunny", SwingConstants.CENTER);
        weatherDescriptionText.setBounds(0, 390, 450, 54);
        weatherDescriptionText.setFont(new Font("Calibre", Font.BOLD, 20));
        add(weatherDescriptionText);

        // humidity image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);
        //humidityText
        JLabel humidityText = new JLabel(
               "<html><b>Humidity</b> 100%</html>"
        ); // we can also put the html components within swing components
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Calibre", Font.BOLD, 16));
        add(humidityText);

        // Windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);
        // Windspeed Text
        JLabel windspeedText = new JLabel(
         "<html><b>Windspeed</b> 15km/h</html>"
        );
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Calibre", Font.BOLD, 15));
        add(windspeedText);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        // change the cursor to hand the cursor when over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 15, 47, 45);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // to add functionality to our button, we will need to add an action listener
        // we will place our updated gui code, in the action performed
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                // we will remove all whitespace so all there is left are the characters
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                // retrieve the weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui components

                // updating weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition, we will update the weather image using switch cases

                switch (weatherCondition) {
                    case "Clear" -> weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                    case "Cloudy" -> weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                    case "Rain" -> weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                    case "Snow" -> weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                    default -> weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " ˚C");

                // update weather condition text
                weatherDescriptionText.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
        add(searchButton);

    }

    private ImageIcon loadImage(String resourcePath) {
        //  create the Icon for icon
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Could not find the resource");
        return null;
    }

    private void darkTheme() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
