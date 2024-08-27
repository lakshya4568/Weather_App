import javax.swing.*;

public class AppLauncher {

    public static void main(String[] args) {
        /* invokeLater method will call the runnable objects
            it's useful for swing gui's like ours because it makes updates
            to the GUI more thread safe
         */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //  display our weather app gui
                 new WeatherAppGUI().setVisible(true);
            }
        } ); // this whole block of code, implements our gui to work on Event Dispatch Thread
        // it is important for maintaining  the responsiveness and stability of a swing application's UI

        // System.out.println(WeatherApp.getLocationData("Tokyo"));
        System.out.println(WeatherApp.getCurrentTime());
    }
}
