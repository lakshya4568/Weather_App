import javax.swing.*;

import com.formdev.flatlaf.FlatDarkLaf;
public class AppLauncher {

    public static void main(String[] args) {
        // Check for required resources before starting the app
        ResourceChecker.checkAndCreateResources();
        FlatDarkLaf.setup();
        
        /* invokeLater method will call the runnable objects
            it's useful for swing gui's like ours because it makes updates
            to the GUI more thread safe
         */

        SwingUtilities.invokeLater(() -> {
            try {
                //  display our weather app gui
                WeatherAppGUI app = new WeatherAppGUI();
                app.setVisible(true);
                System.out.println("Weather App started successfully");
            } catch (Exception e) {
                System.err.println("Error starting the Weather App: " + e.getMessage());
                e.printStackTrace();
            }
        }); // this whole block of code, implements our gui to work on Event Dispatch Thread
        // it is important for maintaining  the responsiveness and stability of a swing application's UI
    }
}
