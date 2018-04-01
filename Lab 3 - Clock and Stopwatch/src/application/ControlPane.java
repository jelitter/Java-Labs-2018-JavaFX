package application;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.util.Calendar;

import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ControlPane extends VBox {

	private ClockPane clock;
	public StringProperty time;
	private Timeline tl;
	private CheckBox compassMode, smoothMode;
	ComboBox<Double> comboBox;
	private static final String START_STYLE = "-fx-base: LIMEGREEN;";
	private static final String STOP_STYLE = "-fx-base: LIGHTCORAL;";

	private Button btnStopWatch, btnResetStopWatch;
	private boolean running;

	public ControlPane(ClockPane clock) {
		super(10);
		this.clock = clock;
		setupButtons();
		setRunning(false);
		startAnimation();
	}

	private void startAnimation() {
		time = new SimpleStringProperty();
		tl = new Timeline(new KeyFrame(Duration.millis(16), ae -> {

			// We keep the observable 'time' property updated with current time
			time.set(clock.getTimeString());
			clock.setCurrentTime();
			// clock.updateClockFrame();
			if (compassMode.isSelected()) {
				if (smoothMode.isSelected())
					clock.setRotate(-(double) Calendar.getInstance().getTimeInMillis() / 1000 * 6);
				else
					clock.setRotate(-clock.getSecond() * 6);
			}

			if (isRunning()) {
				// Stop watch
			}
			
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();
	}

	public boolean isRunning() {
		return running;
	}

	private void setupButtons() {
//		Region separator = new Region();
		btnStopWatch = new Button();
		btnResetStopWatch = new Button("Reset");
		setIcon(btnResetStopWatch, "reset.png");

		this.setSpacing(10);
		HBox buttons = new HBox(10);
		HBox secHandOpts = new HBox(5);
		HBox options = new HBox(10);

		buttons.setAlignment(Pos.CENTER);
		secHandOpts.setAlignment(Pos.CENTER);
		options.setAlignment(Pos.CENTER);
		
		secHandOpts.setPrefHeight(20);
		comboBox = new ComboBox<Double>(FXCollections.observableArrayList(1.0, 0.5, 0.1));
		comboBox.setPromptText("Seconds");
		comboBox.getSelectionModel().select(0);
		clock.setMoveEverySecond(getEverySecond());
		
		Label t1 = new Label("Move seconds hand every"); 
		Label t2 = new Label("seconds"); 
		
		compassMode = new CheckBox("Compass mode");
		compassMode.setPrefWidth(USE_COMPUTED_SIZE);
		smoothMode = new CheckBox("Smooth");
		smoothMode.setPrefWidth(USE_COMPUTED_SIZE);
		smoothMode.setDisable(!compassMode.isSelected());
		Group separator = new Group();
		HBox.setHgrow(separator, Priority.ALWAYS);
		
		buttons.getChildren().addAll(btnStopWatch, btnResetStopWatch);
		secHandOpts.getChildren().addAll(t1, comboBox, t2);
		options.getChildren().addAll(compassMode, separator, smoothMode);

		this.getChildren().addAll(buttons, secHandOpts, options);

		for (Node n : buttons.getChildren()) {
			if (n instanceof Button) {
				((Button) n).setMinWidth(110);
				((Button) n).setPrefWidth(USE_COMPUTED_SIZE);
				((Button) n).setPadding(new Insets(5, 20, 5, 10));
				((Button) n).setStyle("-fx-base: DEEPSKYBLUE;");
				((Button) n).setPrefHeight(30);
				((Button) n).setFont(Font.font("Arial", 16));
			}
		}

		btnStopWatch.setOnAction(e -> toggle());
		btnResetStopWatch.setOnAction(e -> clock.setCurrentTime());
		
		compassMode.setOnAction(e -> {
			clock.setRotate(compassMode.isSelected() ? -clock.getSecond() * 6 : 0);
			smoothMode.setDisable(!compassMode.isSelected());
		});
		
		comboBox.setOnAction(e -> {
			clock.setMoveEverySecond(getEverySecond());
		});


	}
	
	public double getEverySecond() {
		return (double) comboBox.getValue();
	}

	public void setIcon(Button button, String iconName) {
		Image img = new Image("/assets/" + iconName);
		ImageView imgv = new ImageView();
		StackPane pane = new StackPane();
		imgv.setImage(img);
		imgv.setFitHeight(24);
		imgv.setPreserveRatio(true);
		imgv.setSmooth(true);
		imgv.setCache(true);
		pane.getChildren().add(imgv);
		pane.setPadding(new Insets(5));
		button.setGraphic(pane);
		button.setContentDisplay(ContentDisplay.LEFT);
	}

	public void setRunning(boolean b) {
		this.running = b;

		btnStopWatch.setText(b ? "Stop" : "Start");
		setIcon(btnStopWatch, b ? "stop.png" : "start.png");
		btnStopWatch.setStyle(b ? STOP_STYLE : START_STYLE);
	}

	public void toggle() {
		setRunning(!isRunning());
	}

}
