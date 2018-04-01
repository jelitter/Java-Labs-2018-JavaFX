package application;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;

public class StopWatch {

	private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss:S");
	private String[] split;
	private int seconds;
	private SimpleStringProperty min, sec, millis, sspTime;
	public SimpleStringProperty timer;
	private Timeline tl;
	private long time;

	public StopWatch() {
		min = new SimpleStringProperty("00");
		sec = new SimpleStringProperty("00");
		millis = new SimpleStringProperty("00");
		sspTime = new SimpleStringProperty("00:00:00");
		timer = sspTime;
	}

	public void startTimer(long time) {
		this.time = time;
		
		
		tl = new Timeline(new KeyFrame(Duration.millis(50), ae -> {
			setTime(this.time);
            this.time += 50;
		}));
		tl.setCycleCount(Timeline.INDEFINITE);
		tl.play();
		
	}

	public void stopTimer(long time) {
		this.time = time;
		setTime(time);
		tl.stop();
	}
	
	public void stopTimer() {
		tl.stop();
	}

	public void setTime(long time) {
		this.time = time;
		split = sdf.format(new Date(time)).split(":");
		min.set(split[0]);
		sec.set(split[1]);
		
		seconds = Integer.parseInt(sec.get());

		if (split[2].length() == 1) {
			split[2] = "0" + split[2];
		}
		millis.set(split[2].substring(0, 2));

		sspTime.set(min.get() + ":" + sec.get() + ":" + millis.get());
		timer = sspTime;
	}
	
	public int getSeconds() {
		return seconds;
	}

	public long getTime() {
		return time;
	}

	public SimpleStringProperty getSspTime() {
		return sspTime;
	}
}
