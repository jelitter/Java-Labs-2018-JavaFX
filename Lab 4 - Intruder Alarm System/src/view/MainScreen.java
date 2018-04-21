package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainScreen {

	private static final int NUMBER_OF_ROOMS = 9;
	private static final int WIDTH = 900;
	private static final int HEIGHT = 600;
	private static MainScreen instance;
	private Stage primaryStage;
	private List<Room> rooms;
	
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
		
		FlowPane  root  = new FlowPane(); 
		root.setAlignment(Pos.BASELINE_CENTER); 
		
//		HBox root = new HBox(10);
		root.setPadding(new Insets(10));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");
		
		createRooms(NUMBER_OF_ROOMS);
		root.getChildren().addAll(rooms);
		
		for (Room room: rooms) {
			FlowPane.setMargin(room, new Insets(10));
		}
		
		
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		
		primaryStage.setTitle("Intruder Detection System");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	private void createRooms(int numberOfRooms) {
		
		int rows = (int) Math.floor(Math.sqrt(numberOfRooms));
		
		double prefWidth = WIDTH / rows;
		double prefHeight = HEIGHT/ rows;
		System.out.println("Rows: " + rows);
		System.out.println("Pref Width : " + prefWidth);
		System.out.println("Pref Height: " + prefHeight);
		
		for (int i = 0; i < numberOfRooms; i++) {
			Room room = new Room("Room " + Integer.toString(i+1));
			room.setMinSize(120, 60);
			room.setMaxSize(WIDTH/2, HEIGHT/2);
//			room.setPrefSize(prefWidth, prefHeight);
//			room.setPadding(new Insets(20));
			room.setStyle("-fx-background-color: LIMEGREEN; -fx-opacity: 0.8;");
			
			room.prefWidthProperty().bind(new SimpleDoubleProperty(prefWidth));
			room.prefHeightProperty().bind(new SimpleDoubleProperty(prefHeight));
			this.rooms.add(room);
		}
	}
}


