package Model;

public class Node {
	private int x, y;
	private double dist,h;
	private Node next;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.dist = 0;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public double getDist(){
		return dist;
	}
	public void setDist(double d) {
		this.dist = d;
	}
	public void setH(double h) {//heuristic; estimated distance to end (A*)
		this.h = h;
	}
	public double getH() {
		return h;
	}
	public double getCost() {
		return getDist()+getH();
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	
	@Override
	public boolean equals(Object a) {
		Node ander = (Node)a;
		if (getX() == ander.getX() && getY() == ander.getY()) {
			return true;
		}
		return false;
	}
}