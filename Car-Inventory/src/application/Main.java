//Saud Ahmed
//4/17/2023
//Purpose: The purpose of the file is to implement NuHashMap and use of Hash code.

package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nusoft.utils.NuHashMap;
import java.util.Random;

public class Main extends Application {
	NuHashMap<String, Vehicle> vehicleMap = new NuHashMap<>(); // Assuming the key is the VIN number
    private int currentPosition = 0;
    char profileChar;

    @Override
    public void start(Stage primaryStage) {
        // Load vehicleData from file and display the first vehicle
    	try (Scanner scanner = new Scanner(new File("VEHICLE_DATA.txt"))) {
    	    while (scanner.hasNextLine()) {
    	        String[] fields = scanner.nextLine().split("\t");
    	        String vin = fields[0];
    	        int modelYear = Integer.parseInt(fields[1]);
    	        String make = fields[2];
    	        String model = fields[3];
    	        String color = fields[4];
    	        Vehicle vehicle = new Vehicle(vin, modelYear, make, model, color);
    	        vehicleMap.put(vin, vehicle);
    	    }
    	} catch (FileNotFoundException e) {
    	    e.printStackTrace();
    	}
    	
    	Random rand = new Random();
    	
    	//When the program starts running
        Vehicle currentVehicle = vehicleMap.get(getVINAtIndex(currentPosition));
        Label vinLabel = new Label ("VIN: " + currentVehicle.getVin());
        Label makeLabel = new Label("Make: " + currentVehicle.getMake());
        Label modelLabel = new Label("Model: " + currentVehicle.getModel());
        Label yearLabel = new Label("Year: " + currentVehicle.getModelYear());
        Label colorLabel = new Label("Color: " + currentVehicle.getColor());
        
        
        //Contact Information
        //1. Intial with Circle
        Circle circle = new Circle();
        circle.setRadius(55); //set the radius of the circle
        circle.setFill(Color.BLANCHEDALMOND);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1);

        profileChar = currentVehicle.getMake().charAt(0);
        Text text = new Text(String.valueOf(profileChar));
        text.setFont(Font.font(40)); //set the font size of the text
        circle.setFill(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        text.setFill(Color.BLACK);
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(circle, text);
        stackPane.setPadding(new Insets(10, 10, 10, 10));
        
        
        //Title
        Label CarTitle = new Label (currentVehicle.getMake() + " " + currentVehicle.getModel() + "\n" + "#" + currentVehicle.getVin());
        CarTitle.setStyle("-fx-font-family: 'Helvetica'; -fx-font-size: 18; -fx-font-weight: bold;");
        CarTitle.setAlignment(Pos.CENTER);
    	

        // Create "Next" button
        Button nextButton = new Button(">");
        nextButton.setMinSize(40, 20);
        nextButton.setOnAction(e -> {
            if (currentPosition < vehicleMap.size() - 1) {
                currentPosition++;
                Vehicle nextVehicle = vehicleMap.get(getVINAtIndex(currentPosition));
                vinLabel.setText("VIN: " + nextVehicle.getVin());
        	    makeLabel.setText("Make: " + nextVehicle.getMake());
        	    modelLabel.setText("Model: " + nextVehicle.getModel());
        	    yearLabel.setText("Year: " + nextVehicle.getModelYear());
        	    colorLabel.setText("Color: " + nextVehicle.getColor());
        	    CarTitle.setText(nextVehicle.getMake() + " "+ nextVehicle.getModel() + " \n#" + nextVehicle.getVin());
        	    profileChar = nextVehicle.getMake().charAt(0);
                text.setText(String.valueOf(profileChar));
                circle.setFill(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            }
        });
        
        
        // Create "Previous" button
        Button previousButton = new Button("<");
        previousButton.setMinSize(40, 20);
        previousButton.setOnAction(e -> {
            if (currentPosition > 0) {
                currentPosition--;
                Vehicle previousVehicle = vehicleMap.get(getVINAtIndex(currentPosition));
                vinLabel.setText("VIN: " + previousVehicle.getVin());
        	    makeLabel.setText("Make: " + previousVehicle.getMake());
        	    modelLabel.setText("Model: " + previousVehicle.getModel());
        	    yearLabel.setText("Year: " + previousVehicle.getModelYear());
        	    colorLabel.setText("Color: " + previousVehicle.getColor());
        	    CarTitle.setText(previousVehicle.getMake() + " " + previousVehicle.getModel() + "\n#" + previousVehicle.getVin());
        	    profileChar = previousVehicle.getMake().charAt(0);
                text.setText(String.valueOf(profileChar));
                circle.setFill(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));

            }
        });
        
        

        ComboBox<String> searchBox = new ComboBox<>();
        searchBox.setPromptText("Select a car...");
        for (Vehicle vehicle : vehicleMap.values()) {
            String carInfo = vehicle.getMake() + " " + vehicle.getModel() + ", #" + vehicle.getVin();
            searchBox.getItems().add(carInfo);
        }


        ButtonBase searchButton = new Button("Search");
		searchButton.setOnAction(e -> {
            String selectedCar = searchBox.getSelectionModel().getSelectedItem();
            if (selectedCar == null) {
                // Show an error message if no car is selected
                Alert alert = new Alert(AlertType.ERROR, "Please select a car.");
                alert.showAndWait();
                return;
            }
            String[] carInfo = selectedCar.split(", #");
            String vin = carInfo[1];
            Vehicle matchingVehicle = vehicleMap.get(vin);
            if (matchingVehicle != null) {
                vinLabel.setText("VIN: " + matchingVehicle.getVin());
                makeLabel.setText("Make: " + matchingVehicle.getMake());
                modelLabel.setText("Model: " + matchingVehicle.getModel());
                yearLabel.setText("Year: " + matchingVehicle.getModelYear());
                colorLabel.setText("Color: " + matchingVehicle.getColor());
                CarTitle.setText(matchingVehicle.getMake() + " "+ matchingVehicle.getModel() + " \n#" + matchingVehicle.getVin());
                profileChar = matchingVehicle.getMake().charAt(0);
                text.setText(String.valueOf(profileChar));
                circle.setFill(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
            } else {
                // Show an error message if no matching vehicle is found
                Alert alert = new Alert(AlertType.ERROR, "No matching vehicle found.");
                alert.showAndWait();
            }
        });


        // Create "Update" button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
        	Vehicle currentPostion = vehicleMap.get(getVINAtIndex(currentPosition));
            // create fields for the user to update information
            TextField makeField = new TextField(currentPostion.getMake());
            TextField modelField = new TextField(currentPostion.getModel());
            TextField yearField = new TextField(Integer.toString(currentPostion.getModelYear()));
            TextField colorField = new TextField(currentPostion.getColor());

            // create a VBox to hold the fields
            VBox fieldsVBox = new VBox();
            fieldsVBox.getChildren().addAll(
                new Label("Make: "), makeField,
                new Label("Model: "), modelField,
                new Label("Year: "), yearField,
                new Label("Color: "), colorField
            );
            fieldsVBox.setSpacing(10);
            fieldsVBox.setAlignment(Pos.CENTER_LEFT);

            // create a dialog box to show the fields
            Alert updateDialog = new Alert(AlertType.CONFIRMATION);
            updateDialog.setTitle("Update Vehicle Information");
            updateDialog.setHeaderText("Enter the updated information for the vehicle:");
            updateDialog.getDialogPane().setContent(fieldsVBox);
            updateDialog.initModality(Modality.APPLICATION_MODAL);

            // show the dialog box and wait for the user to update the information
            updateDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // if the user clicked the "Update" button, update the hash ma
                	currentPostion.setMake(makeField.getText());
                	currentPostion.setModel(modelField.getText());
                	currentPostion.setModelYear(Integer.parseInt(yearField.getText()));
                	currentPostion.setColor(colorField.getText());

                    // display the current updated data
                    makeLabel.setText("Make: " + currentPostion.getMake());
                    modelLabel.setText("Model: " + currentPostion.getModel());
                    yearLabel.setText("Year: " + currentPostion.getModelYear());
                    colorLabel.setText("Color: " + currentPostion.getColor());
                    CarTitle.setText(currentPostion.getMake()+1 + " " + currentPostion.getModel() + "\n#" + currentPostion.getVin());
            	    profileChar = currentPostion.getMake().charAt(0);
                    text.setText(String.valueOf(profileChar));
                }
            });
        });



        // Create "Delete" button
        Button deleteButton = new Button("Delete");
        deleteButton.setMinSize(40, 20);
        deleteButton.setOnAction(e -> {
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Are you sure you want to delete this vehicle record?");
            confirmation.setContentText("This action cannot be undone.");
            confirmation.initModality(Modality.APPLICATION_MODAL);
            confirmation.initOwner(primaryStage);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String vinToDelete = getVINAtIndex(currentPosition);
                    vehicleMap.remove(vinToDelete);
                    Alert success = new Alert(AlertType.INFORMATION);
                    success.setTitle("Deletion Successful");
                    success.setHeaderText(null);
                    success.setContentText("The vehicle record has been successfully deleted.");
                    success.initModality(Modality.APPLICATION_MODAL);
                    success.initOwner(primaryStage);
                    success.showAndWait();
                    if (currentPosition > 0) {
                        currentPosition--;
                    }
                    Vehicle nextVehicle = vehicleMap.get(getVINAtIndex(currentPosition));
                    vinLabel.setText("VIN: " + nextVehicle.getVin());
                    makeLabel.setText("Make: " + nextVehicle.getMake());
                    modelLabel.setText("Model: " + nextVehicle.getModel());
                    yearLabel.setText("Year: " + nextVehicle.getModelYear());
                    colorLabel.setText("Color: " + nextVehicle.getColor());
                    CarTitle.setText(nextVehicle.getMake() + " "+ nextVehicle.getModel() + " \n#" + nextVehicle.getVin());
                    profileChar = nextVehicle.getMake().charAt(0);
                    text.setText(String.valueOf(profileChar));
                    circle.setFill(Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
                }
            });
        });
       

        // Add buttons to HBox
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(previousButton, deleteButton, nextButton);

        // Add labels and buttons to VBox
        VBox vehicleInfoBox = new VBox();
        vehicleInfoBox.setAlignment(Pos.CENTER_LEFT);
        vehicleInfoBox.setSpacing(10);
        vehicleInfoBox.setPadding(new Insets(10, 10, 10, 10));
        vehicleInfoBox.getChildren().addAll(vinLabel, makeLabel, modelLabel, yearLabel, colorLabel, buttonBox);

        HBox SearchBox = new HBox(10, searchBox, searchButton);
        SearchBox.setAlignment(Pos.CENTER);
        
        HBox buttons = new HBox (10, updateButton, deleteButton);
        buttons.setAlignment(Pos.CENTER);
        
        HBox container = new HBox(15, previousButton, CarTitle , nextButton);
        container.setAlignment(Pos.CENTER);
        
        VBox carDetails = new VBox(10, vinLabel, makeLabel, modelLabel, yearLabel, colorLabel, buttons);
        
        
        
        // Add buttons to a VBox or other container
        VBox vbox = new VBox(10, SearchBox, stackPane, container, carDetails);
        vbox.setAlignment(Pos.CENTER);

        // Add buttonsContainer to the root pane or scene
	    

        // BorderPane for the main content
        BorderPane Root = new BorderPane(vbox);
        Root.setPadding(new Insets(10));
        
        // Create the scene and show the stage
        Scene Scene = new Scene(Root, 450, 450);
        primaryStage.setTitle("Fast Car Lookup");
        primaryStage.setScene(Scene);
        primaryStage.show();
        // ...
    }

    // Helper method to get the VIN number of a vehicle at a given index in the NuHashMap
    private String getVINAtIndex(int index) {
        int i = 0;
        for (String vin : vehicleMap.keySet()) {
            if (i == index) {
                return vin;
            }
            i++;
        }
        return null; // Should not happen if index is within range
    }

}

