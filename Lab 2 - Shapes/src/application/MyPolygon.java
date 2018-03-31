package application;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

public class MyPolygon extends Rotator {
	
	public MyPolygon(int sides, Double radius) {		
		this(sides, radius, Color.color(Math.random(), Math.random(), Math.random()));
	}
	
	public MyPolygon(int sides, Double radius, Color col) {		
		Double centerX = radius / 2, centerY = radius/2;
		
		ObservableList<Double> list = this.getPoints();

		for (int i = 0; i < sides; i++) {
			list.add(centerX + radius * Math.cos(2 * i * Math.PI / sides)); 
			list.add(centerY - radius * Math.sin(2 * i * Math.PI / sides));

		} 
	}
}

