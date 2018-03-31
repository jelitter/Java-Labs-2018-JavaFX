package application;

public class MyCross extends Rotator {

	public MyCross(double radius) {

		double r = radius/2;
		double w = radius/8;

		this.getPoints().addAll(new Double[]{
				-w, -r,
				w, -r,
				w, -w,
				r, -w,
				r, w,
				w, w,
				w,r,
				-w,r,
				-w,w,
				-r,w,
				-r,-w,
				-w,-w
		});
	}

}
