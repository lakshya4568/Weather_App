import netscape.javascript.JSObject;
import org.jetbrains.annotations.NotNull;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

/*
Retrieving the weather data from API - this backend logic will
fetch the latest weather data from the external API and return it to the GUI
and display to user
 */
public class WeatherApp {
    // fetch weather for the given location data
    public static JSObject getWeatherData(String locationName) {
        // get location coordinate using the geolocation API
        JSONArray locationData = getLocationData(locationName);

        //extract  latitude and longitude from the location data
        final String apiURL = getString(locationData);

        try {
            // call the api and get the response
            HttpURLConnection connection = fetchApiResponse(apiURL);

            // check for response status
            // 200 means connection successful
            if (connection.getResponseCode() != 200) {
                System.out.println("COULD NOT CONNECT to api");
                return null;
            } else {
                StringBuilder hourly = new StringBuilder();
                Scanner sc = new Scanner(connection.getInputStream());

                while (sc.hasNext()) {
                    hourly.append(sc.nextLine());
                }
                sc.close();
                connection.disconnect();
                JSONParser parser = new JSONParser();
                JSONObject resultHour = (JSONObject) parser.parse(hourly.toString());

                // extract the hourly data from the JSON response
                JSONArray hourlydata = (JSONArray) resultHour.get("Hour");

                if (hourlydata != null && !hourlydata.isEmpty()) {
                    return (JSObject) hourlydata.get(0);
                } else {
                    // handle the case where the array is empty or null
                    return null;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static @NotNull String getString(JSONArray locationData) {
        assert locationData != null;
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        // build API request URL  with location coordinates
        String apiURL = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude +
                "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Asia%2FSingapore ";
        return apiURL;
    }

    @org.jetbrains.annotations.Nullable
    private static String api_key() {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.properties")) {
            prop.load(input);
            return prop.getProperty("api_key");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String weather_api() {
        Properties config = new Properties();
        try (FileInputStream input = new FileInputStream("src/config.properties")) {
            config.load(input);
            return config.getProperty("weather_api");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* to use weather forecast api we need to give
    the longitude and latitude data which can be found
    using their Geolocation API,
    that's why we will be creating another API call where it will take in and entered location, and returns the
    latitude and longitude data
     */

    // retrieves geographic coordinates for given location name
    public static JSONArray getLocationData(String locationName) {
        // replace any whitespace in location name to + adhere to APIs request format
        // so when giving city name = "new york", it standards to new+york;
        locationName = locationName.replaceAll(" ", "+");

        // build API url with location parameter
        String URLString = "https://api.api-ninjas.com/v1/geocoding?city=" + locationName;

        try {
            // call the api and get the response
            HttpURLConnection connection = fetchApiResponse(URLString);

            // check response status
            // 200 means successful connection
            // please read all the https status code in readme
            if (connection.getResponseCode() != 200) {
                System.out.println("Error : Could not connect to API");
                return null;
            } else {
                // store the API results
                StringBuilder resultJson = new StringBuilder();
                Scanner sc = new Scanner(connection.getInputStream());

                // read and store the resulting json data into our string builder
                while (sc.hasNext()) {
                    resultJson.append(sc.nextLine());
                }
                sc.close();
                //close the url connection
                connection.disconnect();

                // parse the Json String into a JSON Object
                JSONParser parser = new JSONParser();
                JSONArray resultsJsonArray = (JSONArray) parser.parse(String.valueOf(resultJson));

                // get the first object from the array (if needed)
                if (resultsJsonArray != null && !resultsJsonArray.isEmpty()) {
                    return resultsJsonArray;
                } else {
                    // handle the case where the array is empty or null
                    return null;
                }
                // get the list of location data the API generated from the location name
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // couldn't find the location
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String URLString) {
        try {
            // attempt to create a connection
            URL url = new URL(URLString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // set request method to get
            // methods : GET, PUT, DELETE, POST
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-API-Key", api_key());
            connection.addRequestProperty("ACCEPT", "application/json");
            //connect to our API
            connection.connect();
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
        }
        // could not make connection
        return null;
    }
}
