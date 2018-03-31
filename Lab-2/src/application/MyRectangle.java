package application;

public class MyRectangle extends Rotator {

	public MyRectangle(double width, double height) {
		this(width, height, 0.0);
	}

	public MyRectangle(double width, double height, double borderradius) {
		this.getPoints().addAll(new Double[]{
				-width/2, -height/2,
				width/2, -height/2,
				width/2,  height/2,
				-width/2,  height/2
		});
	}
}
