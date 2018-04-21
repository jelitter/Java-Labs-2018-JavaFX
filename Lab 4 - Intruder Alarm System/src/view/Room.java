package view;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Room extends Pane {

	private String name;

	public Room(String name) {
		this.setName(name);
		this.draw();
	}

	private void draw() {
		VBox root = new VBox(10);
		root.setMinSize(60, 30);
		root.setMaxSize(500, 300);
		root.setPadding(new Insets(20));
		root.setStyle("-fx-background-color: WHITE; -fx-opacity: 0.8;");

		Text title = new Text(this.getName());
		root.getChildren().addAll(title);
		this.getChildren().add(root);
	}

	
	// Getters / setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
