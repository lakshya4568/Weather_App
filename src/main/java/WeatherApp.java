// WeatherApp.java
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Scanner;

public class WeatherApp {
    public static JSONObject getWeatherData(String locationName) {

        // get location coordinates using the geolocation API
        JSONArray locationData = getLocationData(locationName);
        if (locationData == null || locationData.isEmpty()) {
            System.out.println("Location data is null or empty");
            return null;
        }
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude +
                "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Asia%2FSingapore";

        try {
            HttpURLConnection connection = fetchApiResponse(urlString);
            if (connection == null || connection.getResponseCode() != 200) {
                System.out.println("Could not connect to API");
                return null;
            }

            StringBuilder savedJSON = new StringBuilder();
            Scanner sc = new Scanner(connection.getInputStream());
            while (sc.hasNext()) {
                savedJSON.append(sc.nextLine());
            }
            sc.close();

            // close url connection
            connection.disconnect();

            // parse through the data
            JSONParser parser = new JSONParser();
            JSONObject resultHour = (JSONObject) parser.parse(String.valueOf(savedJSON));

            // retrieve hourly data
            JSONObject hourlyData = (JSONObject) resultHour.get("hourly");

            if (hourlyData == null) {
                System.out.println("Hourly data is null");
                return null;
            }

            // we want to get the current hour's data,
            // so we need to get the index of our current hour
            JSONArray time = (JSONArray) hourlyData.get("time");
            int index = findIndexOfCurrentTime(time);

            // get temperature
            JSONArray temperatureData = (JSONArray) hourlyData.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            // get weather code
            JSONArray weathercode = (JSONArray) hourlyData.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weathercode.get(index));

            // get humidity
            JSONArray relativeHumidity = (JSONArray) hourlyData.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            // get windspeed
            JSONArray windspeedData = (JSONArray) hourlyData.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            // build the weather json data object that we are going to access in our frontend
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private static int findIndexOfCurrentTime(JSONArray timeList) {
        String currentTime = getCurrentTime();

        // iterate through the time list and see which one matches our current state
        for (int i = 0; i < timeList.size(); i++) {
            String time = (String) timeList.get(i);
            if (time.equalsIgnoreCase(currentTime)) {
                // return the index
                return i;
            }
        }
        return 0;
    }

    public static String getCurrentTime() {

        // get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // format date and time to 2023-09-02T00:00 (this is the format the api uses)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        // format and print the current time and date
        return currentDateTime.format(formatter);
    }

    private static String convertWeatherCode(long WeatherCode) {
        String weatherCondition = "";
        if (WeatherCode == 0L) {
            weatherCondition = "Clear";
        } else if (WeatherCode > 0L && WeatherCode <= 3L) {
            weatherCondition = "Cloudy";
        } else if ((WeatherCode >= 51L && WeatherCode <= 67L) || (WeatherCode >= 80L && WeatherCode <= 99L)) {
            weatherCondition = "Rain";
        } else if (WeatherCode >= 71L && WeatherCode <= 77L) {
            weatherCondition = "Snow";
        }
        return weatherCondition;
    }

    public static JSONArray getLocationData(String locationName) {
        locationName = locationName.replaceAll(" ", "+");
        String URLString = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=10&language=en&format=json";

        try {
            HttpURLConnection connection = fetchApiResponse(URLString);
            if (connection == null || connection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            } else {

                StringBuilder resultJson = new StringBuilder();
                Scanner sc = new Scanner(connection.getInputStream());
                while (sc.hasNext()) {
                    resultJson.append(sc.nextLine());
                }
                sc.close();
                connection.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                return (JSONArray) resultsJsonObj.get("results");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String URLString) {
        try {
            URL url = new URL(URLString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // connect to our api
            connection.connect();
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return could not make connection
        return null;
    }
}