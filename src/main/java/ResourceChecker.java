import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceChecker {
    /**
     * Checks if necessary resources exist and creates directories if needed
     */
    public static void checkAndCreateResources() {
        // Ensure the assets directory exists
        Path assetsPath = Paths.get("src/assets");
        if (!Files.exists(assetsPath)) {
            try {
                Files.createDirectories(assetsPath);
                System.out.println("Created assets directory: " + assetsPath.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to create assets directory: " + e.getMessage());
            }
        }

        // Check if essential icon files exist
        String[] requiredAssets = {
                "earth.jpeg", "clear.png", "cloudy.png", "rain.png",
                "snow.png", "humidity.png", "windspeed.png", "search.png"
        };

        for (String asset : requiredAssets) {
            Path assetPath = Paths.get("src/assets/" + asset);
            if (!Files.exists(assetPath)) {
                System.out.println("Warning: Required asset missing: " + assetPath.toAbsolutePath());
                System.out.println("Please ensure all required assets are in the assets folder.");
            }
        }
    }
}
