package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Contoller.Controller;
import Model.Field;
import Model.Node;
/* The main graphics class. Controls the window and all path finding node graphics.
 */
@SuppressWarnings("serial")
public class Frame extends JPanel 
		implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	ControlPannel ch;
	JFrame window;
	Field field;
	Controller controller;
	boolean showSteps, btnHover;
	double a1, a2;
	char currentKey = (char) 0;
	String mode;
	Timer timer = new Timer(100, this);
	int r = Components.randomWithRange(0, 255);; 
	int G = Components.randomWithRange(0, 255);;
	int b = Components.randomWithRange(0, 255);;
	int setStart = 0;
	public boolean getShowSteps() {
		return showSteps;
	}
	public void setShowSteps(boolean b) {
		showSteps = b;
	}
	public String getMode() {
		return mode;
	}
	
	public Frame() {
		field = new Field(this, 25);
		controller = new Controller(field);
		initiate();
		ch = new ControlPannel(controller,this,field);
		// Set up window
		window = new JFrame();
		window.setContentPane(this);
		window.setTitle("Pathfinding Visualization");
		window.getContentPane().setPreferredSize(new Dimension(700, 600));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		// Add all controls
		
		ch.addAll();
		
		this.revalidate();
		this.repaint();
	}
	
	public void initiate() {
		mode = "Map Creation";
		showSteps = true;
		btnHover = false;
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		// Calculating value of a in speed function 1
		a1 = (5000.0000 / (Math.pow(25.0000/5000, 1/49)));
		a2 = 625.0000;
	}
	//@overrides  paintComponent in Jcomponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// If no path is found
		if (field.isNoPath()) {
			// Set timer for animation
			timer.setDelay(50);
			timer.start();

			// Set text of "run" button to "clear"
			ch.getB("run").setText("clear");
			
			// Set mode to "No Path"
			mode = "No Path";

			// Place "No Path" text on screen in center
			ch.noPathTBounds();
			ch.getL("noPathT").setVisible(true);
			this.add(ch.getL("noPathT"));
			this.revalidate();
		}
		
		// If pathfinding is complete (found path)
		else if (field.isComplete()) {
			// Set run button to clear
			ch.getB("run").setText("clear");
			// Set timer delay, start for background animation
			timer.setDelay(50);
			timer.start();

			// Set completed mode
			if(showSteps) {
				mode = "Found path";
			}
			else {
				mode = "Found path in " + field.getRunTime() + "ms";
			}
		}
		
		// Draws grid
		g.setColor(Color.lightGray);
		for (int j = 0; j < this.getHeight(); j += field.getSize()) {
			for (int i = 0; i < this.getWidth(); i += field.getSize()) {
				g.drawRect(i, j, field.getSize(), field.getSize());
			}
		}
		//draw Nodes
		field.drawNodes(g);
		
		//setComponents
		ch.setComponents();
		// If control panel is being hovered, change colours
		if(btnHover) {
			g.setColor(style.darkText);
			ch.hoverColour();
		}
		else {
			g.setColor(style.btnPanel);
			ch.nonHoverColour();
		}
		// Position all controls
		ch.positionAll();	
		// Drawing control panel rectangle
		ch.background(g);
	}

	// Draws info (f, g, h) on current node
	public void drawInfo(Node current, Graphics g) {
		int size = field.getSize();
		if (size > 50) {
			g.setFont(style.numbers);
			g.setColor(Color.black);
			g.drawString(Double.toString((int)current.getCost()), current.getX() + 4, current.getY() + 16);
			g.setFont(style.smallNumbers);
			g.drawString(Integer.toString((int)current.getDist()), current.getX() + 4, current.getY() + size - 7);
			g.drawString(Integer.toString((int)current.getH()), current.getX() + size - 26, current.getY() + size - 7);
		}
	}

	public void MapCalculations(MouseEvent e) {
		int xRollover = e.getX()% field.getSize();
		int yRollover = e.getY() % field.getSize();
		//Top left corner of the Node
		int mousedNodeX = e.getX() - xRollover; 
		int mousedNodeY = e.getY() - yRollover;
		Node NewNode = new Node(mousedNodeX, mousedNodeY);
		
		Node temp = field.searchNode(field.getBorderList(), mousedNodeX, mousedNodeY);
		if(temp != null)
			field.removeNode(field.getBorderList(),temp);	
		// If left mouse button is clicked	
		if (SwingUtilities.isLeftMouseButton(e)) {
			//add to wall
			field.addNode(field.getBorderList(),NewNode);
			if((field.getStart() != null && NewNode.equals(field.getStart())) || (field.getEnd() != null && NewNode.equals(field.getEnd())))
				field.removeNode(field.getBorderList(), NewNode);
			
		} 
		// If right mouse button is clicked
		else if (SwingUtilities.isRightMouseButton(e)) {
			//Remove from wall
			int Location = field.searchNodeLoc(field.getBorderList(),mousedNodeX, mousedNodeY);
			if (Location != -1) {
				field.removeNodeLoc(field.getBorderList(),Location);
			}
			//Place start/endpoint
			else {
				if(setStart == 0) {
					if (field.getStart() == null) {
						field.setStart(NewNode);
					} else {
						field.getStart().setXY(mousedNodeX, mousedNodeY);
						controller.resetLists();
					}
					setStart = 1;
	
				}
				else {
					if (field.getEnd() == null) {
						field.setEnd(NewNode) ;		
					} else {
						field.getEnd().setXY(mousedNodeX, mousedNodeY);
						controller.resetLists();
					}
					setStart = 0;
				}
				
				 
			}
	
		}
		repaint();
	}	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {
		MapCalculations(e);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		MapCalculations(e);
	}
	@Override
	// Track mouse on movement
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		// Detects if mouse is within button panel
		if(x >= 10 && x <= 332 && y >= (getHeight()-96) && y <= (getHeight()-6)) {
			btnHover = true;	
		}
		else {
			btnHover = false;
		}
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		currentKey = key;
		// Start if space is pressed
		if (currentKey == KeyEvent.VK_SPACE) {
			ch.getB("run").setText("stop");
			start();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		currentKey = (char) 0;
	}

	// Starts path finding
	void start() {
		if(field.getStart() != null && field.getEnd() != null) {
			if (!showSteps) {
				/*System.out.println("Don't Show");
				field.start(field.getStart(), field.getEnd());*/
			} else {
				field.setRunning(true);
				setSpeed();
			}
		}
		else {
			System.out.println("ERROR: Needs start and end points to run.");
			window.dispose();
			new Frame();//restart
		}
	}
	
	@Override
	// Scales the map with mouse wheel scroll
	public void mouseWheelMoved(MouseWheelEvent m) {
		int Newsize = field.getSize();
		int prevSize = field.getSize();
		int rotation = m.getWheelRotation();		
		int scroll = 3;

		// Changes size of grid based on scroll
		if (rotation == -1 && prevSize + scroll < 200) {
			Newsize += scroll;
		} else if (rotation == 1 && prevSize - scroll > 2) {
			Newsize += -scroll;
		}
		field.setSize(Newsize);
		double ratio = (double)Newsize/prevSize;

		// new X and Y values for Start
		if (field.getStart() != null) {
			int sX = (int) Math.round(field.getStart().getX() * ratio);
			int sY = (int) Math.round(field.getStart().getY() * ratio);
			field.getStart().setXY(sX, sY);
		}

		// new X and Y values for End
		if (field.getEnd() != null) {
			int eX = (int) Math.round(field.getEnd().getX() * ratio);
			int eY = (int) Math.round(field.getEnd().getY() * ratio);
			field.getEnd().setXY(eX, eY);
		}

		// new X and Y values for borders
		for(Node item: field.getBorderList()) {
			int newX = (int) Math.round((item.getX() * ratio));
			int newY = (int) Math.round((item.getY() * ratio));
			item.setXY(newX, newY);
		}

		// New X and Y for Open nodes
		for(Node item: field.getOpenList()) {
			int newX = (int) Math.round((item.getX() * ratio));
			int newY = (int) Math.round((item.getY() * ratio));
			item.setXY(newX, newY);
		}

		// New X and Y for Closed Nodes
		for(Node item: field.getClosedList()) {
			if (!item.equals(field.getStart())) {
				int newX = (int) Math.round((item.getX() * ratio));
				int newY = (int) Math.round((item.getY() * ratio));
				item.setXY(newX, newY);
			}
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Moves one step ahead in path finding (called on timer)
		if (field.isRunning() && showSteps) {	
			if(field.getNext()==null) {
				field.setNext(field.getStart());
			}		
			controller.getMethod().findPath(field.getNext());	
		}
		
		// Actions of run/stop/clear button
		if(e.getActionCommand() != null) {
			if(e.getActionCommand().equals("run") && !field.isRunning()) {
				ch.getB("run").setText("stop");
				start();
			}
			else if(e.getActionCommand().equals("clear")) {
				clear();
				
			}
			else if(e.getActionCommand().equals("stop")) {
				ch.getB("run").setText("start");
				field.setRunning(false);
				timer.stop();
			}
			else if(e.getActionCommand().equals("start")) {
				ch.getB("run").setText("stop");
				field.setRunning(true);
				timer.start();
			}
		}
		repaint();
	}
	public void clear() {
		ch.getB("run").setText("run");
		mode = "Map Creation";
		ch.getL("noPathT").setVisible(false);
		field.reset();
	}
	// Calculates delay with two exponential functions
	void setSpeed() {
		int delay = 0;
		int value = ch.getS("speed").getValue();
		
		if(value == 0) {
			timer.stop();
			return;
		}
		else if(value >= 1 && value < 50) {
			// Exponential function. value(1) == delay(5000). value (50) == delay(25)
			delay = (int)(a1 * (Math.pow(25/5000.0000, value / 49.0000))); //a = (5000.0000 / (Math.pow(25.0000/5000, 1/49)))
		}
		else if(value >= 50 && value <= 100) {
			// Exponential function. value (50) == delay(25). value(100) == delay(1).
			delay = (int)(a2 * (Math.pow(1/25.0000, value/50.0000)));
		}
		if(!timer.isRunning()) {
			timer.start();
		}
		timer.setDelay(delay);
	}
}