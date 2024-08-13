import javax.imageio.ImageIO;
import  javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// inherit the Jframe class from the swing packages to our gui class
public class WeatherAppGUI extends  JFrame {
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
           addGuiComponents();
    }

    private void addGuiComponents() {
        // search field
        JTextField searchTextField = new JTextField();

        // set the location and size of our component
        searchTextField.setBounds(15, 15, 351, 45);
        //searchTextField.setBorder(10);

        // change the font style and size
        searchTextField.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        add(searchTextField);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        // change the cursor to hand the cursor when over this button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
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
}
