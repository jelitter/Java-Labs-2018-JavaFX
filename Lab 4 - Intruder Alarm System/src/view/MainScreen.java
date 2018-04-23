package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainScreen {

	private static final int NUMBER_OF_ROOMS = 4;
	private static final int WIDTH = 900;
	private static final int HEIGHT = 600;
	static BooleanProperty armedProperty;
	private static MainScreen instance;
	private Stage primaryStage;
	private Button btnAddRoom, btnRemoveRoom, btnAddThief, btnRemoveThief, btnExit;
	private StackPane stack;
	private FlowPane roomArea;
 	private Timeline detection; 
	private ObservableList<RoomPane> rooms;
	private ObservableList<Thief> thieves;

	public MainScreen() {
		instance = this;
		go();
	}

	public static MainScreen getInstance() {
		if (instance == null)
			return new MainScreen();
		return instance;
	}

	
	public void toggleArmed() {
		armedProperty.set(!armedProperty.get());
	}
	
	private void go() {
		
		detection = new Timeline(new KeyFrame(Duration.millis(33), ae -> {
			this.detectIntrussion();
		}));
		detection.setCycleCount(Timeline.INDEFINITE);
//		detection.play();

		armedProperty = new SimpleBooleanProperty(true);
		
		
		
		rooms = FXCollections.observableArrayList();
		thieves = FXCollections.observableArrayList();

		primaryStage = new Stage();
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.getIcons().add(new Image("/assets/icon.png"));
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);

		VBox root = new VBox(10);

		stack = new StackPane();
		
		roomArea = new FlowPane();
		roomArea.setPadding(new Insets(10));
		roomArea.setHgap(10);
		roomArea.setVgap(10);
		roomArea.prefWidthProperty().bind(root.widthProperty());
		roomArea.prefHeightProperty().bind(root.heightProperty());
		roomArea.setAlignment(Pos.BASELINE_CENTER);
		roomArea.setStyle("-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");

		HBox controls = new HBox(10);
		Button btnToggle = new Button("Alarm Disarmed");
		btnToggle.setPrefWidth(160);
		
//		btnToggle.textProperty().bind(Bindings.concat("Alarm Armed: ", armedProperty.asString()));
	
		armedProperty.addListener(e -> {
			if (armedProperty.get()) {
				btnToggle.setText("Alarm Armed");
				btnToggle.setStyle("-fx-background-color: LIMEGREEN;");
				detection.play();
			} else {
				btnToggle.setText("Alarm Disarmed");
				btnToggle.setStyle("-fx-background-color: CORAL;");
				detection.stop();
			}
		});
		
		armedProperty.set(false);
		
		btnToggle.setOnMouseClicked(e -> toggleArmed());
		
		
		btnAddRoom = new Button("+ Room");
		btnRemoveRoom = new Button("- Room");
		btnAddThief = new Button("+ Thief");
		btnRemoveThief = new Button("- Thief");
		btnExit = new Button("Exit");
		Text txtRooms = new Text();
		Text txtThieves= new Text();
		HBox roomBox = new HBox(10);
		HBox thiefBox = new HBox(10);
		Pane spacing = new Pane();

		txtRooms.textProperty().bind(Bindings.concat("Rooms: ", Bindings.size(this.rooms)));
		roomBox.getChildren().addAll(btnAddRoom, btnRemoveRoom, txtRooms);
		roomBox.setAlignment(Pos.CENTER);
		
		txtThieves.textProperty().bind(Bindings.concat("Thieves: ", Bindings.size(this.thieves)));
		thiefBox.getChildren().addAll(btnAddThief, btnRemoveThief, txtThieves);
		thiefBox.setAlignment(Pos.CENTER);
		
		HBox.setHgrow(roomBox, Priority.ALWAYS);
		HBox.setHgrow(thiefBox, Priority.ALWAYS);

		controls.getChildren().addAll(btnToggle, roomBox, thiefBox, spacing, btnExit);
		controls.setPadding(new Insets(5,5,0,5));

		VBox.setVgrow(roomArea, Priority.ALWAYS);
		
		stack.getChildren().addAll(roomArea);
		stack.getChildren().addAll(this.thieves);
	
		root.getChildren().addAll(controls, stack);

		Scene scene = new Scene(root, WIDTH, HEIGHT);

		primaryStage.setTitle("Intruder Detection System");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		createRooms(NUMBER_OF_ROOMS);
		createThief();

		setHandlers();
	}

	private void createRooms(int numberOfRooms) {
		for (int i = 0; i < numberOfRooms; i++) {
			createRoom();
		}
	}
	
	private void createThief() {
		if (this.thieves.size() < 10) {
			this.thieves.add(new Thief(stack));
			stack.getChildren().clear();
			stack.getChildren().addAll(roomArea);
			stack.getChildren().addAll(this.thieves);
			
		}
	}

	private void removeThief() {
		if (this.thieves.size() > 0) {
			this.thieves.remove(this.thieves.size() - 1);
			stack.getChildren().clear();
			stack.getChildren().addAll(roomArea);
			stack.getChildren().addAll(this.thieves);
		}
	}

	private void createRoom() {
		if (this.rooms.size() < 20) {
			RoomPane newRoom = new RoomPane("Room " + Integer.toString(this.rooms.size() + 1));
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
	
	private void detectIntrussion() {

		for (RoomPane room : this.rooms) {

			// Looking for intrusions
			room.deactivateAlarm();
			if (room.armedProperty.get()) {
				for (Thief thief : this.thieves) {
					if (thief.isInRoom(room)) {
						room.activateAlarm();
					}
				}
			}
		}
	}

	private void setHandlers() {
		btnAddRoom.setOnMouseClicked(e -> {
			createRoom();
		});
		btnRemoveRoom.setOnMouseClicked(e -> {
			removeRoom();
		});
		
		btnAddThief.setOnMouseClicked(e -> {
			createThief();
		});
		btnRemoveThief.setOnMouseClicked(e -> {
			removeThief();
		});
	
		roomArea.heightProperty().addListener(e -> {
			updateRooms();
		});
		
		roomArea.widthProperty().addListener(e -> {
			updateRooms();
		});
	}
	
	
}
