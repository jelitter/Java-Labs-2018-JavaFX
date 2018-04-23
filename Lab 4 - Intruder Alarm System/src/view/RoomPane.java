package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Room;

public class RoomPane extends Pane {

	private Room room;

	public RoomPane(String name) {
		room = new Room(name);
		this.draw();
	}

	private void draw() {
		VBox root = new VBox(5);
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
		radioOff.setSelected(true);
		radioButtons.getChildren().addAll(radioOn, radioOff);
		
		
		Button btnIntruder = new Button("Intruder");
		btnIntruder.setTextAlignment(TextAlignment.CENTER);
		btnIntruder.setAlignment(Pos.CENTER);
		btnIntruder.setMaxWidth(Double.MAX_VALUE);
		btnIntruder.setMaxHeight(Double.MAX_VALUE);
		
		
		// Intruder button will only be visible when "On" radio is selected.
		btnIntruder.visibleProperty().bind(radioOn.selectedProperty());
		btnIntruder.setOnMouseClicked(e -> {
			activateAlarm(root);
		});
		
		
		HBox.setHgrow(radioButtons, Priority.ALWAYS);
		HBox.setHgrow(btnIntruder, Priority.ALWAYS);
		roomControls.getChildren().addAll(radioButtons, btnIntruder);
		
		
		
		Text title = new Text(this.getName());
		title.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		title.setFill(Color.color(Math.random(), Math.random()/2, Math.random()));
		root.getChildren().addAll(title, roomControls);
		
//		roomControls.visibleProperty().bind(MainScreen.armed);
		roomControls.visibleProperty().bindBidirectional(MainScreen.armedProperty);

		root.setOnMouseEntered(e -> {
			if (radioOn.isSelected())
				activateAlarm(root);
		});
		root.setOnMouseExited(e -> {
			deactivateAlarm(root);
		});
		
		radioOff.setOnMouseClicked(e -> {
			deactivateAlarm(root);
		});
		
		
		this.getChildren().add(root);
		
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(0.0f);
		shadow.setOffsetX(0.0f);
		shadow.setColor(Color.GRAY);
		this.setEffect(shadow);
		title.setEffect(shadow);
	}

	private void activateAlarm(VBox root) {
		root.setStyle("-fx-background-color: CORAL;");
	}
	private void deactivateAlarm(VBox root) {
		root.setStyle("-fx-background-color: WHITE;");
	}

	
	// Getters / setters

	public String getName() {
		return this.room.getName();
	}

	public void setName(String name) {
		this.room.setName(name);
	}

}
