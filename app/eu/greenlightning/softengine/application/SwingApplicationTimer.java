package eu.greenlightning.softengine.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.Timer;

public class SwingApplicationTimer implements ActionListener {

	private final Application application;
	private long nanoTime;

	public SwingApplicationTimer(Application application) {
		this(application, 60);
	}

	public SwingApplicationTimer(Application application, double fps) {
		this.application = Objects.requireNonNull(application, "Application must not be null.");
		this.nanoTime = System.nanoTime();
		Timer timer = new Timer(getMillisPerFrameForFPS(fps), this);
		timer.setInitialDelay(0);
		timer.start();
	}

	private int getMillisPerFrameForFPS(double fps) {
		if (fps <= 0) {
			throw new IllegalArgumentException("FPS must be greater than 0.");
		}
		return (int) (1000d / fps);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		long currentNanoTime = System.nanoTime();
		long nanoDelta = currentNanoTime - nanoTime;
		double secondDelta = nanoDelta / 1e9;
		nanoTime = currentNanoTime;
		application.updateAndRender(secondDelta);
	}

}
