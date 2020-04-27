/******************************************************************************************************************
 * LocationGUI
 * ****************************************************************************************************************
 * GUI for the CMSC 255 Final Project. It allows the user to select two locations from drop-down menus
 * that they wish to measure the distance between. If they choose "Own coordinates", they will have to enter those.
 * The GUI the displays a world map with a dashed line drawn between the two locations, and below it prints to the
 * user how far apart those two points are
 *
 * Adrian Vagberg, Eeshan Singh, Mel Sofroniou, Ogaga Obrimah
 * April 27, 2020
 * CMSC 255-901
 *****************************************************************************************************************/
package FinalProject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LocationGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //Declare important nodes
        Button button;
        Scene scene1;
        Stage window = primaryStage;
        window.setTitle("Distance Finder");

        //Create a welcome message, and explain the task of the program
        Label welcome = new Label("Welcome! Please select two locations that you wish" +
                " to measure the distance between.");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        /*
        Create a drop down menu of the names of all cities in the Location.cities array, plus an
        option to enter their own coordinates, so that the user can choose what location to start from
         */
        ComboBox<String> startCityDropDown = new ComboBox<>();
        startCityDropDown.getItems().add("Own coordinates");
        for (int i = 0; i < Location.cities.length; i++) {
            startCityDropDown.getItems().add(Location.cities[i].getName());
        }
        startCityDropDown.setPromptText("Where would you like to start from?");

        /*
        Text fields and prompts so that the user can enter their input. If they choose an existing city,
        the coordinates will automatically be entered. If they choose to enter own coordinates, a grey
        prompt text will appear in the text box
         */
        Label latitude1 = new Label("Latitude (-90 to 90 degrees, positive for degrees North):");
        TextField lat1Input = new TextField();
        lat1Input.setMaxWidth(232);
        Label longitude1 = new Label("Longitude (-180 to 180 degrees, positive for degrees East):");
        TextField long1Input = new TextField();
        long1Input.setMaxWidth(232);
        startCityDropDown.setOnAction(e -> {
            /*
            A while loop so that the appropriate coordinates are entered into the text boxes if the user
            chooses any of the pre-existing cities. It runs until it either finds a match, or until the
            length of the Location.cities array. The user cannot edit the coordinates if they choose an
            existing city. If they choose "own coordinates" they will instead see example coordinates.
             */
            boolean matched = false;
            int i = 0;
            while (!matched & i < Location.cities.length) {
                if (startCityDropDown.getValue().equals(Location.cities[i].getName())) {
                    lat1Input.setText("" + Location.cities[i].getLatitude());
                    long1Input.setText("" + Location.cities[i].getLongitude());
                    lat1Input.setEditable(false);
                    long1Input.setEditable(false);
                    matched = true;
                }
                else {
                    i++;
                }
            }
            if (!matched) {
                lat1Input.clear();
                long1Input.clear();
                lat1Input.setPromptText("e.g. 44.3617");
                long1Input.setPromptText("e.g. -76.8104");
                lat1Input.setEditable(true);
                long1Input.setEditable(true);
            }

        });

        /*
        Create a drop down menu of the names of all cities in the Location.cities array, plus an
        option to enter their own coordinates, so that the user can choose what location to end at
         */
        ComboBox<String> endCityDropDown = new ComboBox<>();
        endCityDropDown.getItems().add("Own coordinates");
        for (int i = 0; i < Location.cities.length; i++) {
            endCityDropDown.getItems().add(Location.cities[i].getName());
        }
        endCityDropDown.setPromptText("Where would you like to finish?");

        /*
        Text fields and prompts so that the user can enter their input. If they choose an existing city,
        the coordinates will automatically be entered. If they choose to enter own coordinates, a grey
        prompt text will appear in the text box
         */
        Label latitude2 = new Label("Latitude (-90 to 90 degrees, positive for degrees North):");
        TextField lat2Input = new TextField();
        lat2Input.setMaxWidth(232);
        Label longitude2 = new Label("Longitude (-180 to 180 degrees, positive for degrees East):");
        TextField long2Input = new TextField();
        long2Input.setMaxWidth(232);
        endCityDropDown.setOnAction(e -> {
            /*
            A while loop so that the appropriate coordinates are entered into the text boxes if the user
            chooses any of the pre-existing cities. It runs until it either finds a match, or until the
            length of the Location.cities array. The user cannot edit the coordinates if they choose an
            existing city. If they choose "own coordinates" they will instead see example coordinates.
             */
            boolean matched = false;
            int i = 0;
            while (!matched & i < Location.cities.length) {
                if (endCityDropDown.getValue().equals(Location.cities[i].getName())) {
                    lat2Input.setText("" + Location.cities[i].getLatitude());
                    long2Input.setText("" + Location.cities[i].getLongitude());
                    lat2Input.setEditable(false);
                    long2Input.setEditable(false);
                    matched = true;
                }
                else {
                    i++;
                }
            }
            if (!matched) {
                lat2Input.clear();
                long2Input.clear();
                lat2Input.setPromptText("e.g. 44.3617");
                long2Input.setPromptText("e.g. -76.8104");
                lat2Input.setEditable(true);
                long2Input.setEditable(true);
            }
        });

        //Drop down menu for the user to choose their scale
        ComboBox<String> scaleDropDown = new ComboBox<>();
        scaleDropDown.setPromptText("Which scale would you like the results in?");
        scaleDropDown.getItems().addAll("Kilometers", "Meters", "Miles", "Yards");

        /*
        Create a button that takes the user to the window that displays a map, or shows an error message if
        the user did not enter sufficient information
         */
        button = new Button("Show me!");
        button.setOnAction(e -> {
            //If any of the drop down menus have not had a value entered, an error message will be displayed
            if (startCityDropDown.getValue() == null || endCityDropDown.getValue() == null ||
            scaleDropDown.getValue() == null) {
                displayErrorBox();
            }
            else {
                /*
                Find out which Location objects the chosen starting and ending locations match
                using while loops, and two "blank" locations
                */
                Location startingLocation = new Location(0,0);
                Location endingLocation = new Location(0, 0);

                //Use a boolean sentinel value to break out of the loop once a match is found
                boolean isMatched = false;
                int count = 0;
                while (!isMatched && count < Location.cities.length) {
                    //Match starting location
                    if (startCityDropDown.getValue().equals(Location.cities[count].getName())) {
                        startingLocation.setName(Location.cities[count].getName());
                        startingLocation.setLatitude(Location.cities[count].getLatitude());
                        startingLocation.setLongitude(Location.cities[count].getLongitude());
                        isMatched = true;
                    }
                    count++;
                }
                //Otherwise, if unmatched (i.e. user entered own coordinates) do this:
                if (!isMatched) {
                    /*
                    Try-catch block that sets the coordinates to (0,0) in case the input could
                    not be parsed to a double
                    */
                    double latitude, longitude;
                    try {
                        latitude = Double.parseDouble(lat1Input.getText());
                        longitude = Double.parseDouble(long1Input.getText());
                    }
                    catch (NumberFormatException ex) {
                        latitude = 0;
                        longitude = 0;
                    }
                    startingLocation.setLatitude(latitude);
                    startingLocation.setLongitude(longitude);

                    /*
                    The setters for latitude and longitude verify that the coordinates are valid
                    We then use the validated coordinates to set the name as "(lat, long)"
                    */
                    startingLocation.setName("(" + startingLocation.getLatitude() + ", " +
                            startingLocation.getLongitude() + ")");
                }

                //Reset count and the boolean sentinel value to break out of the loop once a match is found
                isMatched = false;
                count = 0;
                while (!isMatched && count < Location.cities.length) {
                    //Match ending location
                    if (endCityDropDown.getValue().equals(Location.cities[count].getName())) {
                        endingLocation.setName(Location.cities[count].getName());
                        endingLocation.setLatitude(Location.cities[count].getLatitude());
                        endingLocation.setLongitude(Location.cities[count].getLongitude());
                        isMatched = true;
                    }
                    count++;
                }
                //Otherwise, if unmatched (i.e. user entered own coordinates) do this:
                if (!isMatched) {
                    /*
                    Try-catch block that sets the coordinates to (0,0) in case the input could
                    not be parsed to a double
                    */
                    double latitude, longitude;
                    try {
                        latitude = Double.parseDouble(lat2Input.getText());
                        longitude = Double.parseDouble(long2Input.getText());
                    }
                    catch (NumberFormatException ex) {
                        latitude = 0;
                        longitude = 0;
                    }
                    endingLocation.setLatitude(latitude);
                    endingLocation.setLongitude(longitude);

                    /*
                    The setters for latitude and longitude verify that the coordinates are valid
                    We then use the validated coordinates to set the name as "(lat, long)"
                    */
                    endingLocation.setName("(" + endingLocation.getLatitude() + ", " +
                            endingLocation.getLongitude() + ")");
                }

                //Call the displayMap method to show the user the new window with the map and the results
                displayMap(startingLocation, endingLocation, scaleDropDown.getValue());
            }
        });
        button.setPrefSize(80, 50);

        //Create a BorderPane layout and place the welcome label top center, and the button bottom center
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15, 10, 15, 10));
        layout.setTop(welcome);
        layout.setBottom(button);
        BorderPane.setAlignment(welcome, Pos.TOP_CENTER);
        BorderPane.setAlignment(button, Pos.BOTTOM_CENTER);

        //A GridPane pane to put in the center region of the BorderPane
        GridPane centerPane = new GridPane();
        centerPane.setPadding(new Insets(40, 40, 40, 60));

        //Set vertical and horizontal separation between nodes
        centerPane.setVgap(10);
        centerPane.setHgap(8);

        //Declare which row and column each node goes in
        centerPane.setConstraints(startCityDropDown, 0, 0);
        centerPane.setConstraints(latitude1, 0, 1);
        centerPane.setConstraints(lat1Input, 1, 1);
        centerPane.setConstraints(longitude1, 0, 2);
        centerPane.setConstraints(long1Input, 1, 2);
        centerPane.setConstraints(endCityDropDown, 0, 4);
        centerPane.setConstraints(latitude2, 0, 5);
        centerPane.setConstraints(lat2Input, 1, 5);
        centerPane.setConstraints(longitude2, 0, 6);
        centerPane.setConstraints(long2Input, 1, 6);
        centerPane.setConstraints(scaleDropDown, 0, 7);

        centerPane.getChildren().addAll(startCityDropDown, latitude1, lat1Input, longitude1, long1Input,
                endCityDropDown, latitude2, lat2Input, longitude2, long2Input, scaleDropDown);

        //Place the GridPane in the center pane of the BorderPane layout
        layout.setCenter(centerPane);

        //Create scene1
        scene1 = new Scene(layout, 800, 450);

        //Display scene1 first
        window.setScene(scene1);
        window.show();
    }

    /**
     * Method that opens a new window that shows a world map with a line drawn between the two locations
     * as well as prints the result for the user below the map. Window must be closed before returning
     * to the previous window.
     *
     * @param startLocation A Location object for the first location
     * @param endLocation A Location object for the second location
     * @param scale A String representing the scale the user wants the result displayed in
     */
    public static void displayMap(Location startLocation, Location endLocation, String scale) {
        Stage mapWindow = new Stage();
        mapWindow.setTitle("Map");

        //Block the previous window as long as this window is open
        mapWindow.initModality(Modality.APPLICATION_MODAL);

        //Create a background consisting of an equirectangular world map from Wikipedia
        BackgroundImage bgImage = new BackgroundImage(new Image(
                "https://upload.wikimedia.org/wikipedia/commons/8/83/Equirectangular_projection_SW.jpg",
                720, 360, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bg = new Background(bgImage);
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setBackground(bg);

        /*
        Create exit button and the text that displays the result using the getDistance and
        convertDistance methods in a formatted String to include thousand separators in the distance
        */
        Button exitButton = new Button("Go back");
        String resultString = String.format(
                "%s and %s are %,d %s apart", startLocation.getName(), endLocation.getName(),
                convertDistance(startLocation.getDistance(endLocation), scale), scale.toLowerCase()
        );
        Text result = new Text(resultString);
        result.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //Convert from lat/long coordinates to Java coordinates so that they match a 720x360 grid
        double startX = 2 * startLocation.getLongitude() + 360;
        double startY = 180 - (2 * startLocation.getLatitude());
        double endX = 2 * endLocation.getLongitude() + 360;
        double endY = 180 - (2 * endLocation.getLatitude());

        //Create a dashed line between the starting and ending location
        Line line = new Line(startX, startY, endX, endY);
        line.setStrokeWidth(2);
        line.setStroke(Color.GOLD);
        line.getStrokeDashArray().addAll(5d);
        pane.getChildren().add(line);

        //Align the text and button vertically in the center of the bottom of the screen
        VBox bottom = new VBox(25);
        bottom.setPadding(new Insets(10, 10, 10, 10));
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(result, exitButton);
        pane.setBottom(bottom);

        //Once the exitButton is clicked, it closes the mapWindow
        exitButton.setOnAction(e -> mapWindow.close());

        //Show the mapWindow, return to caller only when it is closed
        Scene mapScene = new Scene(pane, 720, 500);
        mapWindow.setScene(mapScene);
        mapWindow.showAndWait();
    }

    /**
     * A method that creates a popup window telling the user they entered insufficient info.
     * Has to be closed before user can go back to previous window
     */
    public static void displayErrorBox() {
        //Create stage that has be closed before previous stage can handle new actions
        Stage errorWindow = new Stage();
        errorWindow.setTitle("Error");
        errorWindow.initModality(Modality.APPLICATION_MODAL);

        //Create error message that is underlined and in red
        Text errorMessage = new Text("You have to select two locations and a scale.");
        errorMessage.setFont(Font.font("Times New Roman", 16));
        errorMessage.setStroke(Color.RED);
        errorMessage.setUnderline(true);

        //Place error message in middle of popup window
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(20, 20, 20, 20));
        BorderPane.setAlignment(errorMessage, Pos.CENTER);
        pane.setCenter(errorMessage);

        //Show window and return to caller only when it is closed
        Scene errorScene = new Scene(pane, 350, 150);
        errorWindow.setScene(errorScene);
        errorWindow.showAndWait();
    }

    /**
     * Converts the distance from kilometers to miles, yards, or meters.
     * Returns -1 if invalid scale
     * @param kilometerDistance A double for the kilometer distance you wish converted
     * @param scale A String representing the scale you wish to convert to
     * @return A double representing the distance in the new scale
     */
    public static long convertDistance(double kilometerDistance, String scale) {
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

        //Cast to a long (in case the user picked two very far apart locations and a small scale
        return (long) distance;
    }

}
