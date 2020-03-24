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

public class GUITest extends Application {

    //Declare important nodes
    Stage window;
    Button button;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Our first GUI");

        //Create a welcome message, and explain the task of the program
        Label welcome = new Label("Welcome! Please select two locations that you wish to measure the distance between.");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //Create drop down menu so the user can use which location to start from
        ComboBox<String> startCityDropDown = new ComboBox<>();
        startCityDropDown.getItems().addAll(
                "Own coordinates",
                "New York City",
                "Los Angeles",
                "Chicago",
                "Houston",
                "Phoenix",
                "Philadelphia",
                "San Antonio",
                "San Diego",
                "Dallas",
                "San Jose"
        );
        startCityDropDown.setPromptText("Where would you like to start from?");

        //Text fields and prompts so that the user can enter their input
        //If they choose an existing city, the coordinates will automatically be entered
        Label latitude1 = new Label("Latitude (-90 to 90 degrees, positive for degrees North):");
        TextField lat1Input = new TextField();
        lat1Input.setMaxWidth(232);
        Label longitude1 = new Label("Longitude (-180 to 180 degrees, positive for degrees East):");
        TextField long1Input = new TextField();
        long1Input.setMaxWidth(232);
        startCityDropDown.setOnAction(e -> {
            if (startCityDropDown.getValue().equals("Own coordinates")) {
                lat1Input.setPromptText("e.g. 44.3617");
                long1Input.setPromptText("e.g. -76.8104");
            }
            else {
                lat1Input.setText("40.6635");
                long1Input.setText("-118.4108");
            }
        });

        //Drop down menu so the user can choose which location to end at
        ComboBox<String> endCityDropDown = new ComboBox<>();
        endCityDropDown.getItems().addAll(
                "Own coordinates",
                "New York City",
                "Los Angeles",
                "Chicago",
                "Houston",
                "Phoenix",
                "Philadelphia",
                "San Antonio",
                "San Diego",
                "Dallas",
                "San Jose"
        );
        endCityDropDown.setPromptText("Where would you like to finish?");

        //Text fields and prompts so that the user can enter their input
        //If they choose an existing city, the coordinates will automatically be entered
        Label latitude2 = new Label("Latitude (-90 to 90 degrees, positive for degrees North):");
        TextField lat2Input = new TextField();
        lat2Input.setMaxWidth(232);
        Label longitude2 = new Label("Longitude (-180 to 180 degrees, positive for degrees East):");
        TextField long2Input = new TextField();
        long2Input.setMaxWidth(232);
        endCityDropDown.setOnAction(e -> {
            if (endCityDropDown.getValue().equals("Own coordinates")) {
                lat2Input.setPromptText("e.g. 44.3617");
                long2Input.setPromptText("e.g. -76.8104");
            }
            else {
                lat2Input.setText("40.6635");
                long2Input.setText("-118.4108");
            }
        });

        //Drop down menu for the user to choose their scale
        ComboBox<String> scaleDropDown = new ComboBox<>();
        scaleDropDown.setPromptText("Which scale would you like the results in?");
        scaleDropDown.getItems().addAll("Kilometers", "Meters", "Miles", "Yards");

        //Create a button that takes the user to the window that displays a map
        button = new Button("Show me!");
        button.setOnAction(e -> {
            if (startCityDropDown.getValue() == null || endCityDropDown.getValue() == null ||
            scaleDropDown.getValue() == null) {
                displayErrorBox();
            }
            else {
                displayMap(startCityDropDown.getValue(), endCityDropDown.getValue(), scaleDropDown.getValue());
            }
        });
        button.setPrefSize(80, 50);

        //Create a BorderPane layout
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(15, 10, 15, 10));
        layout.setTop(welcome);
        BorderPane.setAlignment(welcome, Pos.TOP_CENTER);
        BorderPane.setAlignment(button, Pos.BOTTOM_CENTER);

        //A GripPane pane to put in the center region of the BorderPane
        GridPane centerPane = new GridPane();
        centerPane.setPadding(new Insets(40, 40, 40, 60));
        centerPane.setVgap(10);
        centerPane.setHgap(8);
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

        layout.setCenter(centerPane);
        layout.setBottom(button);

        //Create scene1
        scene1 = new Scene(layout, 800, 450);

        //Display scene1 first
        window.setScene(scene1);
        window.show();
    }

    public static void displayMap(String location1, String location2, String scale) {
        Stage mapWindow = new Stage();
        mapWindow.setTitle("Map");

        //Block the previous window as long as this window is open
        mapWindow.initModality(Modality.APPLICATION_MODAL);

        //Create a background consisting of an equirectangular world map from Wikipedia
        BackgroundImage bgImage = new BackgroundImage(
                new Image("https://upload.wikimedia.org/wikipedia/commons/8/83/Equirectangular_projection_SW.jpg",
                        720, 360, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background bg = new Background(bgImage);
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setBackground(bg);

        //Create exit button and the text that displays the result (always one million atm)
        Button exitButton = new Button("Go back");
        Text result = new Text(location1 + " and " + location2 + " are one million " + scale + " apart");
        result.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        //Create a line (that will permanently be drawn between NYC and LA for now)
        Line line = new Line(212.1226, 98.673, 123.1784, 111.9612);
        line.setStrokeWidth(2);
        line.setStroke(Color.RED);
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

}

