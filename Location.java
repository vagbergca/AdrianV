/****************************************************************************************************************
 * Location
 * **************************************************************************************************************
 * A class called Location to construct location objects with a name and latitude/longitude coordinates
 *
 * Adrian Vagberg, Eeshan Singh, Mel Sofroniou, Ogaga Obrimah
 * April 1, 2020
 * CMSC 255-901
 ***************************************************************************************************************/
package FinalProject;

public class Location {

    //Declare attributes of the class
    private double latitude;
    private double longitude;
    private String name;

    //Create an array of Locations that other classes can reference
    public static Location[] cities = {
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

    /**
     * Parameterized constructor, creates a location with a chosen name,
     * latitude, and longitude
     *
     * @param name      A String of the Location's name
     * @param latitude  A double representing the latitude in degrees from -90 to 90
     * @param longitude A double representing the longitude in degrees from -180 to 180
     */
    public Location(String name, double latitude, double longitude) {
        this.name = name;
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Parameterized constructor that receives no name and sets the name to the coordinate pair
     * and by calling the three-arg constructor
     *
     * @param latitude  A double representing the latitude in degrees from -90 to 90
     * @param longitude A double representing the longitude in degrees from -180 to 180
     */
    public Location(double latitude, double longitude) {
        this("(" + latitude + ", " + longitude + ")", latitude, longitude);
    }

    /**
     * Sets the name of the Location
     *
     * @param name A String of the Location's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of a Location
     *
     * @return A String of the Location's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the latitude of the Location, given that the input is between
     * -90 and 90. Sets latitude to 0 otherwise
     *
     * @param latitude A double representing the latitude in degrees from -90 to 90
     */
    public void setLatitude(double latitude) {
        if (latitude >= -90 && latitude <= 90) {
            this.latitude = latitude;
        } else {
            this.latitude = 0;
        }
    }

    /**
     * Gets the latitude of the Location
     *
     * @return A double representing the latitude in degrees from -90 to 90
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Sets the longitude of the Location, given that the input is between
     * -180 and 180. Sets longitude to 0 otherwise
     *
     * @param longitude A double representing the longitude in degrees from -180 to 180
     */
    public void setLongitude(double longitude) {
        if (longitude >= -180 && longitude <= 180) {
            this.longitude = longitude;
        } else {
            this.longitude = 0;
        }
    }

    /**
     * Gets the longitude of the Location
     *
     * @return A double representing the longitude in degrees from -180 to 180
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Uses the Haversine formula to calculate the distance between two locations using their
     * latitude/longitude coordinates
     *
     * @param aLocation Another location to measure the distance to
     * @return A double that is the distance in kilometers between the two points
     */
    public double getDistance(Location aLocation) {

        final int EARTH_RADIUS = 6371;

        // Haversine formula
        double a = Math.pow(Math.sin(Math.toRadians(this.latitude - aLocation.getLatitude()) / 2), 2) +
                (Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(aLocation.getLatitude())) *
                        Math.pow(Math.sin(Math.toRadians(this.longitude - aLocation.getLongitude()) / 2), 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

}
