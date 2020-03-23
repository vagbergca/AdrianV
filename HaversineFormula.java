import java.util.Scanner;

public class HaversineFormula {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Please enter two coordinate pairs, i.e. degrees latitude1 longitude1 latitude2 longitude2: ");
        double lat1 = input.nextDouble();
        double long1 = input.nextDouble();
        double lat2 = input.nextDouble();
        double long2 = input.nextDouble();

        System.out.println("The distance between (" + lat1 + ", " + long1 + ") and (" + lat2 + ", " + long2 +
                ") is " + getDistance(lat1, long1, lat2, long2) + " kilometers");

    }

    public static double getDistance(double lat1, double long1, double lat2, double long2) {

        final int EARTH_RADIUS = 6371;

        // Haversine formula
        double a = Math.pow(Math.sin((lat1 - lat2) / 2), 2) +
                (Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((long1 - long2) / 2), 2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance;
    }

}
