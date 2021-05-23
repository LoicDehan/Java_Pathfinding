package View;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Contoller.Controller;

public interface Components {
	
	public static JLabel modeText() {
		JLabel modeText;
		modeText = new JLabel("Mode: ");
		modeText.setName("modeText");
		modeText.setFont(style.bigText);
		modeText.setForeground(style.darkText);
		modeText.setVisible(true);
		return modeText;
	}
	public static JLabel speedT() {
		JLabel speedT = new JLabel("Speed: ");
		speedT.setName("speedT");
		speedT.setFont(style.numbers);
		speedT.setVisible(true);
		return speedT;
	}
	public static JLabel speedC() {
		JLabel speedC = new JLabel("50");
		speedC.setName("speedC");
		speedC.setFont(style.numbers);
		speedC.setVisible(true);
		return speedC;
	}
	public static JLabel openT() {
		JLabel openT = new JLabel("Open");
		openT.setName("openT");
		openT.setFont(style.numbers);
		openT.setVisible(true);
		return openT;
	}
	public static JLabel openC() {
		JLabel openC = new JLabel("0");
		openC.setName("openC");
		openC.setFont(style.numbers);
		openC.setVisible(true);
		return openC;
	}			
	public static JLabel closedT() {	
		JLabel closedT = new JLabel("Closed");
		closedT.setName("closedT");
		closedT.setFont(style.numbers);
		closedT.setVisible(true);
		return closedT;
	}
	public static JLabel closedC() {	
		JLabel closedC = new JLabel("0");
		closedC.setName("closedC");
		closedC.setFont(style.numbers);
		closedC.setVisible(true);
		return closedC;
	}	
	public static JLabel pathT() {	
		JLabel pathT = new JLabel("Path");
		pathT.setName("pathT");
		pathT.setFont(style.numbers);
		pathT.setVisible(true);
		return pathT;
	}	
	public static JLabel pathC() {	
		JLabel pathC = new JLabel("0");
		pathC.setName("pathC");
		pathC.setFont(style.numbers);
		pathC.setVisible(true);
		return pathC;
	}
	public static JLabel noPathT() {	
		JLabel noPathT = new JLabel("NO PATH");
		noPathT.setName("noPathT");
		noPathT.setForeground(Color.white);
		noPathT.setFont(style.REALBIGText);
		return noPathT;
	}		

	public static JCheckBox diagonalCheck() {
		JCheckBox diagonalCheck = new JCheckBox();
		diagonalCheck.setText("Diagonal");
		diagonalCheck.setName("diagonalCheck");
		diagonalCheck.setOpaque(false);
		diagonalCheck.setSelected(true);
		diagonalCheck.setFocusable(false);
		diagonalCheck.setVisible(true);
		return diagonalCheck;
	}	
	public static JCheckBox trigCheck() {
		JCheckBox trigCheck = new JCheckBox();
		trigCheck.setText("Trig");
		trigCheck.setName("trigCheck");
		trigCheck.setOpaque(false);
		trigCheck.setFocusable(false);
		trigCheck.setVisible(true);
		return trigCheck;
	}	
		
	// Set up JSliders
	public static JSlider speed(Frame frame) {
		JSlider speed = new JSlider();
		speed.setName("speed");
		speed.setOpaque(false);
		speed.setVisible(true);
		speed.setFocusable(false);
		speed.addChangeListener(new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			speed.setValue(source.getValue());
			frame.setSpeed();
			frame.repaint();
			}
		});
		return speed;
	}		
	public static JRadioButton rb(Controller controller,String m) {
		JRadioButton rb = new JRadioButton(m,false);
		rb.setName(m);
		rb.setOpaque(false);
		rb.setVisible(true);
		rb.setFocusable(false);
		rb.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent a) {
		    			controller.setMethod(m);}	
		    });
		return rb;
	}		
	public static JButton run(Frame frame) {	// Set up JButton
		JButton run = new JButton();
		run.setText("run");
		run.setName("run");
		run.setFocusable(false);
		run.addActionListener(frame);
		run.setMargin(new Insets(0,0,0,0));
		run.setVisible(true);
	return run;
	}
	
	// Returns random number between min and max
	static int randomWithRange(int min, int max){
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

}
