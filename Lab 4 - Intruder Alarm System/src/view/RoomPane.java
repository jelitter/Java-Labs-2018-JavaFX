package view;

import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Room;

public class RoomPane extends Pane {

	private Room room;

	public RoomPane(String name) {
		room = new Room(name);
		this.draw();
	}

	private void draw() {
		VBox root = new VBox(0);
//		root.setMinSize(60, 30);
//		root.setMaxSize(500, 300);
//		root.setPadding(new Insets(0));
		root.setStyle("-fx-background-color: WHITE; -fx-opacity: 0.8;");

		Text title = new Text(this.getName());
		root.getChildren().addAll(title);
		this.getChildren().add(root);
		
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(0.0f);
		shadow.setOffsetX(0.0f);
		shadow.setColor(Color.GRAY);
		this.setEffect(shadow);
	}

	
	// Getters / setters

	public String getName() {
		return this.room.getName();
	}

	public void setName(String name) {
		this.room.setName(name);
	}

}
