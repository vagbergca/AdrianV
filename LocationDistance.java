package FinalProject;

import java.util.Locale;
import java.util.Scanner;

public class LocationDistance {

    //Create an array of Locations that all methods can reference
    static Location[] cities = {
            new Location("New York City", 40.6635, -73.9387),
            new Location("Los Angeles", 34.0194, -118.4108),
            new Location("Chicago", 41.8376, -87.6818),
            new Location("Houston", 29.7866, -95.3909),
            new Location("Phoenix", 35.5722, -112.0901),
            new Location("Philadelphia", 40.0094, -75.1333),
            new Location("San Antonio", 29.4724, -98.5251),
            new Location("San Diego", 32.8153, -117.1350),
            new Location("Dallas", 32.7933, -96.7665),
            new Location("San Jose", 37.2967, -121.8189)
    };

    public static void main(String[] args) {
        //Declare variables to hold the coordinates of the two locations later in the program
        double lat1 = 0;
        double long1 = 0;
        double lat2 = 0;
        double long2 = 0;

        //Declare a Scanner
        Scanner input = new Scanner(System.in);
        //Declare String variable to hold the sentinel value for the subsequent while-loop
        String exitProgram = "Yes";

        /* While-loop that runs as long as the user does not enter "NO" at the end when asked if they
        wish to continue */
        while (!exitProgram.equalsIgnoreCase("NO")) {

            //Explain the program and ask the user for the kind of locations they wish to measure between
            System.out.println("Would you like to measure the distance between two of the 10 most populous " +
                    "US cities,\nbetween two coordinate pairs, or between one city and one coordinate pair?");
            System.out.print("\nEnter a 1 for two cities, a 2 for one city and one coordinate pair. " +
                            "\nAny other integer will result in two coordinate pairs: ");
            int selection = input.nextInt();

            //Case between two cities
            if (selection == 1) {
                System.out.println("\nThe 10 most populous US cities are:\n" +
                        "New York City\nLos Angeles\nChicago\nHouston\nPhoenix" +
                        "\nPhiladelphia\nSan Antonio\nSan Diego\nDallas\nSan Jose");
                //Get first city name from user
                System.out.print("Enter the first city: ");
                //Extra nextLine() to consume the new line character
                input.nextLine();
                String inputCity1 = input.nextLine();

                //Check if input is a valid name. Prompt user to reenter until it is valid
                while (!verifyCity(inputCity1)) {
                    System.out.print("Invalid city name. Try again: ");
                    inputCity1 = input.nextLine();
                }
                //Get second city name from user
                System.out.print("Enter the second city: ");
                String inputCity2 = input.nextLine();

                //Check if input is a valid name. Prompt user to reenter until it is valid
                while (!verifyCity(inputCity2)) {
                    System.out.print("Invalid city name. Try again: ");
                    inputCity2 = input.nextLine();
                }

                //Ask user which scale they would like the distance in
                System.out.print("\nWhich scale would you like the distance in?" +
                        " Enter yards, miles, meters, or kilometers: ");
                String scale = input.next();

                //Validate the entered scale, prompt user to reenter until correct
                while (!verifyScale(scale)) {
                    System.out.print("Invalid scale. Try again: ");
                    scale = input.next();
                }

                /* Retrieve the coordinates of the two cities and store them in the
                lat & long variables */
                for (int i = 0; i < cities.length; i++) {
                    if (inputCity1.equalsIgnoreCase(cities[i].getName())) {
                        lat1 = cities[i].getLatitude();
                        long1 = cities[i].getLongitude();
                    }
                    if (inputCity2.equalsIgnoreCase(cities[i].getName())) {
                        lat2 = cities[i].getLatitude();
                        long2 = cities[i].getLongitude();
                    }
                }

                //Calculate the distance and convert
                double distance = convertDistance(getDistance(lat1, long1, lat2, long2), scale);

                //Display the results
                System.out.printf(Locale.US, "\nThe distance between %s and %s is " +
                        "%,.0f %s\n", inputCity1, inputCity2, distance, scale.toLowerCase());
            }

            //Case between a city and a coordinate pair
            else if (selection == 2) {
                System.out.println("\nThe 10 most populous US cities are:\n" +
                        "New York City\nLos Angeles\nChicago\nHouston\nPhoenix" +
                        "\nPhiladelphia\nSan Antonio\nSan Diego\nDallas\nSan Jose");
                //Get first city name from user
                System.out.print("Enter the city: ");
                //Extra nextLine() to consume the new line character
                input.nextLine();
                String inputCity1 = input.nextLine();

                //Check if input is a valid name. Prompt user to reenter until it is valid
                while (!verifyCity(inputCity1)) {
                    System.out.print("Invalid city name. Try again: ");
                    inputCity1 = input.nextLine();
                }

                //Get the coordinate pair from the user
                System.out.print("Enter the degrees latitude of your other location\n" +
                        "-90 to 90, positive for degrees north (defaults to 0 otherwise): ");
                lat2 = input.nextDouble();
                System.out.print("Enter the degrees longitude of your other location\n" +
                        "-180 to 180, positive for degrees east (defaults to 0 otherwise): ");
                long2 = input.nextDouble();

                /* Create a Location with specified latitude and longitude to verify coordinates are
                within the allowed values */
                Location location1 = new Location(lat2, long2);

                //Ask user which scale they would like the distance in
                System.out.print("\nWhich scale would you like the distance in?" +
                        " Enter yards, miles, meters, or kilometers: ");
                String scale = input.next();

                //Validate the entered scale, prompt user to reenter until correct
                while (!verifyScale(scale)) {
                    System.out.print("Invalid scale. Try again: ");
                    scale = input.next();
                }

                /* Retrieve the coordinates of the city and store them in the
                lat1 & long1 variables */
                for (int i = 0; i < cities.length; i++) {
                    if (inputCity1.equalsIgnoreCase(cities[i].getName())) {
                        lat1 = cities[i].getLatitude();
                        long1 = cities[i].getLongitude();
                    }
                }

                //Calculate the distance and convert
                double distance = convertDistance(getDistance(lat1, long1, location1.getLatitude(),
                        location1.getLongitude()), scale);

                //Display the results
                System.out.printf(Locale.US, "The distance between %s and (%.4f, %.4f) is " +
                        "%,.0f %s\n", inputCity1, location1.getLatitude(), location1.getLongitude(),
                        distance, scale.toLowerCase());
            }

            // Case with two coordinate pairs
            else {
                //Get the first coordinate pair from the user
                System.out.print("Enter the degrees latitude of your first location\n" +
                        "-90 to 90, positive for degrees north (defaults to 0 otherwise): ");
                lat1 = input.nextDouble();
                System.out.print("Enter the degrees longitude of your first location\n" +
                        "-180 to 180, positive for degrees east (defaults to 0 otherwise): ");
                long1 = input.nextDouble();

                /* Create a Location with specified latitude and longitude to verify coordinates are
                within the allowed values */
                Location location1 = new Location(lat1, long1);

                //Get the second coordinate pair from the user
                System.out.print("Enter the degrees latitude of your other location\n" +
                        "-90 to 90, positive for degrees north (defaults to 0 otherwise): ");
                lat2 = input.nextDouble();
                System.out.print("Enter the degrees longitude of your other location\n" +
                        "-180 to 180, positive for degrees east (defaults to 0 otherwise): ");
                long2 = input.nextDouble();

                /* Create a Location with specified latitude and longitude to verify coordinates are
                within the allowed values */
                Location location2 = new Location(lat2, long2);

                //Ask user which scale they would like the distance in
                System.out.print("\nWhich scale would you like the distance in?" +
                        " Enter yards, miles, meters, or kilometers: ");
                String scale = input.next();

                //Validate the entered scale. Prompt user to reenter until correct
                while (!verifyScale(scale)) {
                    System.out.print("Invalid scale. Try again: ");
                    scale = input.next();
                }

                //Calculate the distance and convert
                double distance = convertDistance(getDistance(location1.getLatitude(), location1.getLongitude(),
                        location2.getLatitude(), location2.getLongitude()), scale);

                //Display the results
                System.out.printf(Locale.US, "The distance between (%.4f, %.4f) and (%.4f, %.4f) is " +
                        "%,.0f %s\n", location1.getLatitude(), location1.getLongitude(), location2.getLatitude(),
                        location2.getLongitude(), distance, scale.toLowerCase());
            }

            //Ask user if they wish to go again, or if they want to exit
            System.out.print("Do you want to do another measurement? Enter YES/NO: ");
            exitProgram = input.next();
        }
    }

    /**
     * Uses the Haversine formula to calculate the distance between two coordinate points
     * on the surface of the Earth
     * @param lat1 A double for the first latitude, ranging from -90 to 90 degrees
     * @param long1 A double for the first longitude, ranging from -180 to 180 degrees
     * @param lat2 A double for the second latitude, ranging from -90 to 90 degrees
     * @param long2 A double for the second longitude, ranging from -180 to 180 degrees
     * @return A double that is the distance in kilometers between the two points
     */
    public static double getDistance(double lat1, double long1, double lat2, double long2) {

        final int EARTH_RADIUS = 6371;

        // Haversine formula
        double a = Math.pow(Math.sin(Math.toRadians(lat1 - lat2) / 2), 2) +
                (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.pow(Math.sin(Math.toRadians(long1 - long2) / 2), 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    /**
     * Converts the distance from kilometers to miles, yards, or meters.
     * Returns -1 if invalid scale
     * @param kilometerDistance A double for the kilometer distance you wish converted
     * @param scale A String representing the scale you wish to convert to
     * @return A double representing the distance in the new scale
     */
    public static double convertDistance(double kilometerDistance, String scale) {
        //Declare variables and essential constants
        double distance;
        final double MILES_PER_KILOMETER = 0.621371;
        final double METERS_PER_KILOMETER = 1000;
        final double YARDS_PER_KILOMETER = 1093.61;

        //Compare the input scale to valid scales and convert appropriately
        if (scale.equalsIgnoreCase("miles")) {
            distance = kilometerDistance * MILES_PER_KILOMETER;
        }
        else if (scale.equalsIgnoreCase("yards")) {
            distance = kilometerDistance * YARDS_PER_KILOMETER;
        }
        else if (scale.equalsIgnoreCase("meters")) {
            distance = kilometerDistance * METERS_PER_KILOMETER;
        }
        else if (scale.equalsIgnoreCase("kilometers")) {
            distance = kilometerDistance;
        }
        else {
            distance = -1.0;
        }
        return distance;
    }

    /**
     * Verifies that the input city String is one of the 10 valid options
     * @param city A String of the desired city name
     * @return A boolean true or false showing if the input String is valid
     */
    public static boolean verifyCity(String city) {
        if (city.equalsIgnoreCase(cities[0].getName()) || city.equalsIgnoreCase(cities[1].getName()) ||
                city.equalsIgnoreCase(cities[2].getName()) || city.equalsIgnoreCase(cities[3].getName()) ||
                city.equalsIgnoreCase(cities[4].getName()) || city.equalsIgnoreCase(cities[5].getName()) ||
                city.equalsIgnoreCase(cities[6].getName()) || city.equalsIgnoreCase(cities[7].getName()) ||
                city.equalsIgnoreCase(cities[8].getName()) || city.equalsIgnoreCase(cities[9].getName())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Verifies that the input scale String is one of the 4 valid options
     * @param scale A String of the desired scale
     * @return A boolean true or false showing if the input String is valid
     */
    public static boolean verifyScale(String scale) {
        if (scale.equalsIgnoreCase("yards") ||
                scale.equalsIgnoreCase("miles") ||
                scale.equalsIgnoreCase("meters") ||
                scale.equalsIgnoreCase("kilometers")) {
            return true;
        }
        else {
            return false;
        }
    }

}

//Class called Location to construct Location objects
class Location {

    //Declare attributes of the class
    private double latitude;
    private double longitude;
    private String name;

    /**
     * Parameterized constructor, creates a location with a chosen name,
     * latitude, and longitude
     * @param name A String of the Location's name
     * @param latitude A double representing the latitude in degrees from -90 to 90
     * @param longitude A double representing the longitude in degrees from -180 to 180
     */
    public Location(String name, double latitude, double longitude) {
        this.name = name;
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Parameterized constructor that sets no name, and calls the three-arg constructor
     * with null for name
     * @param latitude A double representing the latitude in degrees from -90 to 90
     * @param longitude A double representing the longitude in degrees from -180 to 180
     */
    public Location(double latitude, double longitude) {
        this(null, latitude, longitude);
    }

    /**
     * Sets the name of the Location
     * @param name A String of the Location's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of a Location
     * @return A String of the Location's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the latitude of the Location, given that the input is between
     * -90 and 90. Sets latitude to 0 otherwise
     * @param latitude A double representing the latitude in degrees from -90 to 90
     */
    public void setLatitude(double latitude) {
        if (latitude >= -90 && latitude <= 90) {
            this.latitude = latitude;
        }
        else {
            this.latitude = 0;
        }
    }

    /**
     * Gets the latitude of the Location
     * @return A double representing the latitude in degrees from -90 to 90
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Sets the longitude of the Location, given that the input is between
     * -180 and 180. Sets longitude to 0 otherwise
     * @param longitude A double representing the longitude in degrees from -180 to 180
     */
    public void setLongitude(double longitude) {
        if (longitude >= -180 && longitude <= 180) {
            this.longitude = longitude;
        }
        else {
            this.longitude = 0;
        }
    }

    /**
     * Gets the longitude of the Location
     * @return A double representing the longitude in degrees from -180 to 180
     */
    public double getLongitude() {
        return this.longitude;
    }
}