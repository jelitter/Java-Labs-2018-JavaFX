package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private boolean armed;
	private static MainScreen instance;
	private Stage primaryStage;
	private Button btnAddRoom, btnRemoveRoom, btnExit;
	private FlowPane roomArea;
	private ObservableList<RoomPane> rooms;

	public MainScreen() {
		instance = this;
		go();
	}

	public static MainScreen getInstance() {
		if (instance == null)
			return new MainScreen();
		return instance;
	}

	public ObservableBooleanValue armedProperty() {
		return new SimpleBooleanProperty(this.armed);
	}

	public void toggleArmed() {
		this.armed = !this.armed;
		System.out.println("Armed: " + this.armed);
	}
	
	private void go() {

		armed = false;
		
		rooms = FXCollections.observableArrayList();

		primaryStage = new Stage();
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.getIcons().add(new Image("/assets/icon.png"));
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);

		VBox root = new VBox(10);

		roomArea = new FlowPane();
		roomArea.setPadding(new Insets(10));
		roomArea.setHgap(10);
		roomArea.setVgap(10);
		roomArea.prefWidthProperty().bind(root.widthProperty());
		roomArea.prefHeightProperty().bind(root.heightProperty());
		roomArea.setAlignment(Pos.BASELINE_CENTER);
		roomArea.setStyle("-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");

		HBox controls = new HBox(10);
		Button btnToggle = new Button("On/Off");
		
		btnToggle.textProperty().bind((ObservableValue<? extends String>) new SimpleStringProperty(armedProperty().get() ? "Off" : "On"));
		btnToggle.setOnMouseClicked(e -> toggleArmed());
		
		
		btnAddRoom = new Button("+ Room");
		btnRemoveRoom = new Button("- Room");
		btnExit = new Button("Exit");
		Text txtRooms = new Text();
		HBox space1 = new HBox(10);
		Pane space2 = new Pane();

		txtRooms.textProperty().bind(Bindings.concat("Rooms: ", Bindings.size(this.rooms)));
		space1.getChildren().add(txtRooms);
		space1.setAlignment(Pos.CENTER);

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
		if (this.rooms.size() < 20) {
			RoomPane newRoom = new RoomPane("Room " + Integer.toString(this.rooms.size() + 1));
//			newRoom.setStyle("-fx-background-color: LIMEGREEN; -fx-opacity: 0.8;");
			newRoom.disableProperty().bind(armedProperty());
			this.rooms.add(newRoom);
			updateRooms();
		}
	}

	private void removeRoom() {
		if (this.rooms.size() > 1) {
			this.rooms.remove(this.rooms.size() - 1);
			updateRooms();
		}
	}

	private void updateRooms() {
		for (RoomPane room : this.rooms) {

			// Calculating needed rows and columns needed for this many rooms.
			int cols = (int) Math.sqrt(this.rooms.size());
			int rows = (int) Math.ceil(this.rooms.size() / (double) cols);
			
			double wi = roomArea.widthProperty().get() - (cols + 1) * 10;
			double he = roomArea.heightProperty().get() - (rows + 1) * 10 - 40;
			double prefWidth = Math.floor(wi / cols);
			double prefHeight = Math.floor(he / rows);
			room.setPrefSize(prefWidth, prefHeight);
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
	
		roomArea.heightProperty().addListener(e -> {
			updateRooms();
		});
		
		roomArea.widthProperty().addListener(e -> {
			updateRooms();
		});
	}
	
	
}
