package View;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import Contoller.Controller;
import Model.Field;

public class ControlPannel implements style,Components {
	private Frame frame;
	private JLabel modeText, speedT, speedC, openT, 
	closedT, pathT, openC, closedC, pathC, noPathT;
	private JCheckBox diagonalCheck;
	private JSlider speed;
	private JButton run;
	private ButtonGroup bgMethod;
	private ArrayList<JLabel> labels;
	private ArrayList<JCheckBox> checks;
	private ArrayList<JSlider> sliders;
	private ArrayList<JButton> buttons;
	private ArrayList<JRadioButton> radiobuttons;
	private Field field;
	private JRadioButton rb1,rb2,rb3;
	private Controller controller;
	public ControlPannel(Controller controller,Frame frame,Field field) {
		this.field = field;
		this.frame = frame;
		this.controller = controller;
		createComponents();
		addComponents();
	}
	private void createComponents() {// Set up JLabels
		modeText = Components.modeText();
		speedT = Components.speedT();
		speedC = Components.speedC();
		openT = Components.openT();
		openC =  Components.openC();		
		closedT = Components.closedT();		
		closedC = Components.closedC();		
		pathT =  Components.pathT();
		pathC = Components.pathC();
		
		noPathT = Components.noPathT();
		// Set up JCheckBoxes
		diagonalCheck =Components.diagonalCheck();
		// Set up JSliders
		speed =  Components.speed(frame);
		run = Components.run(frame);		
		//radioButtons
		bgMethod = new ButtonGroup();
		rb1 = Components.rb(controller,"Dijkstra");
		rb1.setSelected(true);
		rb2 = Components.rb(controller,"A*");
		rb3 = Components.rb(controller,"DepthFirst");
		bgMethod.add(rb1);
		bgMethod.add(rb2);
		bgMethod.add(rb3);
		}
	
	private void addComponents() {
		labels = new ArrayList<JLabel>();
		checks = new ArrayList<JCheckBox>();
		sliders = new ArrayList<JSlider>();
		buttons = new ArrayList<JButton>();
		radiobuttons = new ArrayList<JRadioButton>();
		// Add JLabels to list
		labels.add(modeText);
		labels.add(speedT);
		labels.add(speedC);
		labels.add(openT);
		labels.add(openC);
		labels.add(closedT);
		labels.add(closedC);
		labels.add(pathT);
		labels.add(pathC);
		labels.add(noPathT);
		// Add JCheckboxes to list
		checks.add(diagonalCheck);
		// Add JSliders to list
		sliders.add(speed);
		// Add JButtons to list
		buttons.add(run);
		//radioButtons
		radiobuttons.add(rb1);
		radiobuttons.add(rb2);
		radiobuttons.add(rb3);
		
	}
	public void setComponents(){
		// Setting mode text
		getL("modeText").setText(frame.getMode());
		// Setting numbers in pathfinding lists
		getL("openC").setText(Integer.toString(field.getOpenList().size()));
		getL("closedC").setText(Integer.toString(field.getClosedList().size()));
		getL("pathC").setText(Integer.toString(field.getPathList().size()));
		// Setting speed number text 

		getL("speedC").setText(Integer.toString(getS("speed").getValue()));
		
		// Getting values from checkboxes
		if(field.isDiagonal()!=getC("diagonalCheck").isSelected()) {
			field.setDiagonal(getC("diagonalCheck").isSelected());
			controller.resetLists();
		}
			
	}
	public void background(Graphics g) {
		int marge = 10;
		int hoogte = 86;
		int breedte = 322+78;
		g.fillRect(marge, frame.getHeight()-hoogte-marge, breedte, hoogte);
	}
	// Adds all components to frame
		public void addAll() {
			for (JCheckBox item : checks) {
				frame.add(item);
				}	
			for (JButton item : buttons) {
				frame.add(item);
				}	
			for (JSlider item : sliders) {
				frame.add(item);
				}	
			for (JLabel item : labels) {
				frame.add(item);
				}	
			for (JRadioButton item : radiobuttons) {
				frame.add(item);
				}	
		}		
		
	// Gets a specific JLabel by name
	public JLabel getL(String t) {
		for(JLabel item : labels) {
			if(item.getName().equals(t)) {
				return item;
			}
		}
		return null;
	}
	// Gets specific JCheckBox by name
	public JCheckBox getC(String t) {
		for(JCheckBox item : checks) {
			if(item.getName().equals(t)) {
				return item;
			}
		}
		return null;
	}
	// Gets specific JCheckBox by name
	public JSlider getS(String t) {
		for (JSlider item : sliders){
			if(item.getName().equals(t)) {
				return item;
			}
		}
		return null;
	}
	// Gets specific JCheckBox by name
	public JButton getB(String t) {
		for (JButton item : buttons){
			if(item.getName().equals(t)) {
				return item;
			}
		}
		return null;
	}
	public void noPathTBounds() {
		noPathT.setBounds((int)((frame.getWidth()/2)-(noPathT.getPreferredSize().getWidth()/2)), 
				(int)((frame.getHeight()/2)-noPathT.getPreferredSize().getHeight()/2), 
				(int)noPathT.getPreferredSize().getWidth(), (int)noPathT.getPreferredSize().getHeight());
	}
	public void positionAll() {
		Dimension size;
		int Xkolom1 = 10;
		int Xkolom2 = 110;
		int Xkolom3 = 180;
		int Xkolom4 = 250;
		int Xkolom5 = 320;
		int yrij1 = frame.getHeight()-88;
		int yrij2 = yrij1 + 15;
		int yrij3 = yrij2 + 15;
		int yrij4 = yrij3 + 18;
		// Set label bounds
		Dimension sizeT = speedT.getPreferredSize();
		speedT.setBounds(Xkolom2, yrij1, sizeT.width, 20);
		speedC.setBounds(Xkolom2+sizeT.width,yrij1, 30, 20);
		openT.setBounds(Xkolom3, yrij1, 60, 20);
		openC.setBounds(Xkolom3+50, yrij1, 60, 20);
		closedT.setBounds(Xkolom3, yrij2, 60, 20);
		closedC.setBounds(Xkolom3+50, yrij2, 60, 20);
		pathT.setBounds(Xkolom3, yrij3, 60, 20);
		pathC.setBounds(Xkolom3+50, yrij3, 60, 20);
		size = modeText.getPreferredSize();
		modeText.setBounds(Xkolom1+3, yrij4, size.width, size.height);
		// Set check box bounds
		diagonalCheck.setBounds(Xkolom1, yrij2+5, 90, 20);
		// Set button bounds
		run.setBounds(Xkolom1+5, yrij1-5, 52, 22);	
		// Set slider bounds
		speed.setBounds(Xkolom2-5, yrij2+2,sizeT.width+30 , 20);
		//set radiobuttons bounds
		rb1.setBounds(Xkolom4,yrij1,100,14);
		rb2.setBounds(Xkolom4,yrij2,100,14);
		rb3.setBounds(Xkolom4,yrij3,100,14);
	}
	// Sets text of JLabels to lightText
	public void hoverColour() {
		for (JCheckBox item : checks) {
			item.setForeground(lightText);
		}	
		for (JSlider item : sliders) {
			item.setForeground(lightText);
		}	
		for (JLabel item : labels) {
			item.setForeground(lightText);
		}
		for (JRadioButton item : radiobuttons) {
			item.setForeground(lightText);
		}
		getL("noPathT").setForeground(NopathHighlight);
	}
	// Sets text of JLabels to darkText
	public void nonHoverColour() {
		for (JCheckBox item : checks) {
			item.setForeground(darkText);
		}	
		for (JSlider item : sliders) {
			item.setForeground(darkText);
		}	
		for (JLabel item : labels) {
			item.setForeground(darkText);
		}
		for (JRadioButton item : radiobuttons) {
			item.setForeground(darkText);
		}
		getL("noPathT").setForeground(NopathHighlight);
	}
}