package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Room;

public class RoomPane extends Pane {

	private Room room;
	private VBox root;
	public BooleanProperty armedProperty;
	private ImageView alarmImg;

	public RoomPane(String name) {
		room = new Room(name);
		armedProperty = new SimpleBooleanProperty(false);
		this.draw();
	}

	private void draw() {
		root = new VBox(5);
		root.setPadding(new Insets(10));
		root.setStyle("-fx-background-color: WHITE;");

		root.prefWidthProperty().bind(this.widthProperty());
		root.prefHeightProperty().bind(this.heightProperty());
		
		HBox roomControls = new HBox(10);
		roomControls.setPadding(new Insets(10));
		
		ToggleGroup radioGroup = new ToggleGroup();
		VBox radioButtons = new VBox(10);
		RadioButton radioOn = new RadioButton("On");
		RadioButton radioOff = new RadioButton("Off");
		radioOn.setToggleGroup(radioGroup);
		radioOff.setToggleGroup(radioGroup);
		radioOn.setSelected(true);
		radioButtons.getChildren().addAll(radioOn, radioOff);
		armedProperty.bind(radioOn.selectedProperty());
		
		alarmImg = new ImageView();
		Image icon = new Image("/assets/alarm.gif");
		alarmImg.setImage(icon);
		alarmImg.setFitWidth(50);
		alarmImg.setPreserveRatio(true);
		alarmImg.setSmooth(true);
		alarmImg.setCache(true);
		alarmImg.setVisible(false);
		
		HBox.setHgrow(radioButtons, Priority.ALWAYS);
		HBox.setHgrow(alarmImg, Priority.ALWAYS);
		roomControls.getChildren().addAll(radioButtons, alarmImg);
		
		
		Text title = new Text(this.getName());
		title.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		title.setFill(Color.color(Math.random(), Math.random()/2, Math.random()));
		root.getChildren().addAll(title, roomControls);
		
		roomControls.visibleProperty().bindBidirectional(MainScreen.armedProperty);

		radioOff.setOnMouseClicked(e -> {
			deactivateAlarm();
		});
		
		this.getChildren().add(root);
		
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(0.0f);
		shadow.setOffsetX(0.0f);
		shadow.setColor(Color.GRAY);
		this.setEffect(shadow);
		title.setEffect(shadow);
	}

	public void activateAlarm() {
		root.setStyle("-fx-background-color: LIGHTCORAL; -fx-border-color: RED; -fx-border-width: 4;");
		alarmImg.setVisible(true);
	}
	public void deactivateAlarm() {
		root.setStyle("-fx-background-color: WHITE; -fx-border-color: TRANSPARENT;");
		alarmImg.setVisible(false);
	}

	
	// Getters / setters

	public String getName() {
		return this.room.getName();
	}

	public void setName(String name) {
		this.room.setName(name);
	}

}
