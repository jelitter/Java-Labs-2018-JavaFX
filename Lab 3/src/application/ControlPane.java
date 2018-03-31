package application;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Calendar;

import javafx.animation.KeyFrame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ControlPane extends VBox {

	private ClockPane clock;
	public StringProperty time;
	private Timeline tl;
	private CheckBox compassMode, smoothMode;
	private static final String START_STYLE = "-fx-base: LIMEGREEN;";
	private static final String STOP_STYLE = "-fx-base: LIGHTCORAL;";

	private Button btnStopWatch, btnSetTime;
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
		tl = new Timeline(new KeyFrame(Duration.millis(100), ae -> {

			// We keep the observable 'time' property updated with current time
			time.set(clock.getTimeString());
			if (isRunning()) {
				clock.setCurrentTime();
				// clock.updateClockFrame();
				if (compassMode.isSelected()) {
					if (smoothMode.isSelected())
						clock.setRotate(-(double) Calendar.getInstance().getTimeInMillis() / 1000 * 6);
					else
						clock.setRotate(-clock.getSecond() * 6);
				}
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
		btnSetTime = new Button("Set current time");
		setIcon(btnSetTime, "reset.png");

		HBox buttons = new HBox(10);
		HBox options = new HBox(10);

		buttons.setAlignment(Pos.CENTER);
		options.setAlignment(Pos.CENTER);
		
		compassMode = new CheckBox("Compass mode");
		compassMode.setMaxWidth(USE_COMPUTED_SIZE);
		smoothMode = new CheckBox("Smooth");
		smoothMode.setMaxWidth(USE_COMPUTED_SIZE);
		smoothMode.setDisable(!compassMode.isSelected());

//		HBox.setHgrow(separator, Priority.ALWAYS);
		
		options.getChildren().addAll(compassMode, smoothMode);
		buttons.getChildren().addAll(btnStopWatch, btnSetTime);

		this.getChildren().addAll(buttons, options);

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
		btnSetTime.setOnAction(e -> clock.setCurrentTime());
		
		compassMode.setOnAction(e -> {
			clock.setRotate(compassMode.isSelected() ? -clock.getSecond() * 6 : 0);
			smoothMode.setDisable(!compassMode.isSelected());
		});


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
