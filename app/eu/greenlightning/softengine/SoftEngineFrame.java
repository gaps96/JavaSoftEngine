package eu.greenlightning.softengine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SoftEngineFrame extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new SoftEngineFrame().setVisible(true);
			}
		});
	}

	public SoftEngineFrame() {
		setTitle("Java Soft Engine");
		setContentPane(new SoftEngineComponent());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		exitOnEscape();
	}

	private void exitOnEscape() {
		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});
	}

}
