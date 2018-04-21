package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainScreen {

	private static final int NUMBER_OF_ROOMS = 4;
	private static final int WIDTH = 900;
	private static final int HEIGHT = 600;
	private static MainScreen instance;
	private Stage primaryStage;
	private Button btnAddRoom, btnRemoveRoom, btnExit;
	private FlowPane roomArea;
	private List<RoomPane> rooms;
	
	public MainScreen() {
		instance = this;
		go();
	}

	public static MainScreen getInstance() {
		if (instance == null)
			return new MainScreen();
		return instance;
	}
	
	private void go() {
		
		rooms = new ArrayList<>();
	
		primaryStage = new Stage();
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.getIcons().add(new Image("/assets/icon.png" ));
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);
		
		VBox root = new VBox(5);
		
		roomArea  = new FlowPane(); 
		roomArea.setAlignment(Pos.BASELINE_CENTER); 
		
//		HBox root = new HBox(10);
//		root.setPadding(new Insets(10));
		roomArea.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");
		
//		for (RoomPane room: rooms) {
//			FlowPane.setMargin(room, new Insets(5));
//		}
		
		
		HBox controls = new HBox(10);
		Button btnToggle = new Button("On/Off");
		btnAddRoom = new Button("+ Room");
		btnRemoveRoom = new Button("- Room");
		btnExit= new Button("Exit");
		Pane space1 = new Pane();
		Pane space2 = new Pane();
		
		HBox.setHgrow(space1, Priority.ALWAYS);
		HBox.setHgrow(space2, Priority.ALWAYS);
		
		controls.getChildren().addAll(btnToggle, space1, btnAddRoom, btnRemoveRoom, space2, btnExit);
		
		VBox.setVgrow(roomArea, Priority.ALWAYS);
		root.getChildren().addAll(controls, roomArea);
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		
		primaryStage.setTitle("Intruder Detection System");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		createRooms(NUMBER_OF_ROOMS);
		setHandlers();
	}
	
	private void createRooms(int numberOfRooms) {
		for (int i = 0; i < numberOfRooms; i++) {
			createRoom();
		}
	}

	private void createRoom() {
		RoomPane newRoom = new RoomPane("Room " + Integer.toString(this.rooms.size()+1));
		newRoom.setStyle("-fx-background-color: LIMEGREEN; -fx-opacity: 0.8;");
		FlowPane.setMargin(newRoom, new Insets(5));
		this.rooms.add(newRoom);
		updateRooms();
	}
	
	private void removeRoom() {
		this.rooms.remove(0);
		updateRooms();
	}

	private void updateRooms() {
		for (RoomPane room: this.rooms) {
			int rows = (int) Math.floor(Math.sqrt(this.rooms.size()));
			double spacing = (rows+1) * 5 + 30;
			
			double wi = primaryStage.widthProperty().get();
			double he = primaryStage.heightProperty().get();
			
			double prefWidth = ( wi - spacing) / rows;
			double prefHeight = (he - (spacing+20)) / rows;
			
//			room.minWidthProperty().bind(new SimpleDoubleProperty(200));
//			room.prefWidthProperty().bind(new SimpleDoubleProperty(prefWidth));
//			room.maxWidthProperty().bind(new SimpleDoubleProperty(primaryStage.getWidth()/2));
			
//			room.minHeightProperty().bind(new SimpleDoubleProperty(100));
//			room.prefHeightProperty().bind(new SimpleDoubleProperty(prefHeight));
//			room.maxHeightProperty().bind(new SimpleDoubleProperty(primaryStage.getHeight()/2));
			
			
			
			room.prefWidthProperty().bind(new SimpleDoubleProperty(prefWidth));
			room.prefHeightProperty().bind(new SimpleDoubleProperty(prefHeight));
		}
		roomArea.getChildren().clear();
		roomArea.getChildren().addAll(rooms);
	}
	
	private void setHandlers() {
		btnAddRoom.setOnMouseClicked(e -> {
			createRoom();
		});
		btnRemoveRoom.setOnMouseClicked(e -> {
			removeRoom();
		});
	}
}


