package Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import View.Frame;
import View.style;
//Representation of the nodes
//Borders: impassable walls the path cannot go through
//Open: Nodes currently neighbouring the path during pathfinding
//Closed: Passed nodes during pathfinding
//Path: Final path
public class Field {
	private int size;
	private double  diagonalMoveCost;
	private long runTime;
	private Frame frame;
	private Node startNode, endNode, next;
	private boolean diagonal, running, noPath, complete;
	private ArrayList<Node> borders, open, closed, path;
	//private Sort sort = new Sort();

	public Field(int size) {
		this.setSize(size);
		FieldInit();
	}

	public Field(Frame frame, int size) {
		this.frame = frame;
		this.setSize(size);
		FieldInit();
	}

	public Field(Frame frame, int size, Node start, Node end) {
		this.frame = frame;
		this.setSize(size);
		startNode = start;
		endNode = end;
		FieldInit();
	}
	public void FieldInit() {
		diagonalMoveCost = (Math.sqrt(2 * (Math.pow(size, 2))));
		//default values for checkboxes
		diagonal = true;
		running = false;
		complete = false;
		//Node states
		borders = new ArrayList<Node>();
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		path = new ArrayList<Node>();
	}

	public Frame getFrame() {
		return frame;
	}
	//get,setters
	public void setRunning(boolean b) {
		running = b;
	}
	public void setStart(Node s) {
		startNode = s;
		if(s != null)
			s.setDist(0);
	}
	public void setEnd(Node e) {
		endNode = e;
	}
	public Node getEnd() {
		return endNode;
	}
	public boolean isRunning() {
		return running;
		
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean b) {
		complete = b;
		running = !b;
		noPath = !b;
	}
	public void setNoPath(boolean b) {
		complete = !b;
		running = !b;
		noPath = b;
	}
	public Node getStart() {
		return startNode;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node n) {
		next = n;
	}
	public boolean isNoPath() {
		return noPath;
	}
	public void setDiagonal(boolean d) {
		diagonal = d;
	}
	public boolean isDiagonal() {
		return diagonal;
	}
	
	public void setSize(int s) {
		size = s;
		diagonalMoveCost = (Math.sqrt(2 * (Math.pow(size, 2))));
	}
	public double getDiagonalCost(){
		return diagonalMoveCost;
	}
	public int getSize() {
		return size;
	}
	public ArrayList<Node> getBorderList() {
		return borders;
	}
	public ArrayList<Node> getOpenList() {
		return open;
	}
	public ArrayList<Node> getClosedList() {
		return closed;
	}
	public ArrayList<Node> getPathList() {
		return path;
	}
	public long getRunTime() {
		return runTime;
	}

	public void addNode(ArrayList<Node> list,Node node) {
		Rectangle d = frame.getBounds();
		int h = d.height;
		int w = d.width;
		if(node.getX()>w-1 ||node.getX()<0 || node.getY()>h||node.getY()<0 )
			return;
		else if (list.size() == 0) {
			list.add(node);
		} 
		
		else if (!checkDuplicate(list,node)) {
			list.add(node);
		}
	}

	public void removeNode(ArrayList<Node> list,Node node) {
		for (int i = 0; i < list.size(); i++) {
			if (node.getX() == list.get(i).getX() && node.getY() == list.get(i).getY()) {
				list.remove(i);
			}
		}
	}

	public void removeNodeLoc(ArrayList<Node> list, int location) {
		list.remove(location);
	}
	
	public boolean checkDuplicate(ArrayList<Node> list, Node node) {
		for (int i = 0; i < list.size(); i++) {
			if (node.getX() == list.get(i).getX() && node.getY() == list.get(i).getY()) {
				return true;
			}
		}
		return false;
	}


	
	public int searchNodeLoc(ArrayList<Node> list,int xSearch, int ySearch) {
		int Location = -1;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getX() == xSearch && list.get(i).getY() == ySearch) {
				Location = i;
				break;
			}
		}
		return Location;
	}
	public Node searchNode(ArrayList<Node> list,int xSearch, int ySearch) {
		Node N = null;
		for (Node temp:list) {
			if (xSearch == temp.getX() && ySearch == temp.getY()) {
				N = temp;
			}
		}
		return N;
	}
	
	public Node getNode(ArrayList<Node> list,int x, int y) {
		for(Node item: list){
			if (item.getX() == x && item.getY() == y) {
				return item;
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void reverse(ArrayList list) {
		int j = list.size() - 1;

		for (int i = 0; i < j; i++) {
			Object temp = list.get(i);
			list.remove(i);
			list.add(i, list.get(j - 1));
			list.remove(j);
			list.add(j, temp);
			j--;
		}
	}
	public void drawNodes(Graphics g) {
		int size = getSize();
		// Draws all borders
		g.setColor(Color.black);
		for(Node item: borders){
			g.fillRect(item.getX() + 1,item.getY() + 1, size-1, size-1);
		}
		// Draws all open Nodes (path finding nodes)
		for(Node item: open) {
			g.setColor(style.greenHighlight);
			g.fillRect(item.getX() + 1, item.getY() + 1, size-1, size-1);
			frame.drawInfo(item, g);
		}
		// Draws all closed nodes
		for(Node item: closed) {
			g.setColor(style.redHighlight);
			g.fillRect(item.getX() + 1, item.getY() + 1, size - 1, size - 1);
			frame.drawInfo(item, g);
		}
		// Draw all final path nodes
		for(Node item: path) {
			g.setColor(style.blueHighlight);
			g.fillRect(item.getX() + 1, item.getY() + 1, size - 1, size - 1);
			//drawInfo(current, g);
		}
		// Draws start of path
		if (startNode != null) {
			g.setColor(Color.blue);
			g.fillRect(startNode.getX() + 1, startNode.getY() + 1, size - 1, size - 1);
		}
		// Draws end of path
		if (endNode != null) {
			g.setColor(Color.red);
			g.fillRect(endNode.getX() + 1, endNode.getY() + 1, size - 1, size - 1);
		}			
	}
	public void reset() {
		resetLists();
		noPath = false;
		running = false;
		complete = false;
		//frame.initiate();
	}
	public void resetLists() {
		open.removeAll(open);
		closed.removeAll(closed);
		path.removeAll(path);	
		next = null;
	}
	
}