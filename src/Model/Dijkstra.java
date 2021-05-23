package Model;

import java.awt.Dimension;
import java.util.ArrayList;

import View.Frame;

@SuppressWarnings("unused")
//Dijkstra's pathfinding method
//looks at every possible path 
//will always find the shortest path
//does not work with negative weigths
public class Dijkstra extends Method {
	int x,y;
	public Dijkstra(Field field){
		setField(field);
	}
	public void findPath(Node S) {
		if(S==null)
			return;
		this.setDistDia(field.getDiagonalCost());
		this.setDistReg(field.getSize());
		
		field.addNode(field.getClosedList(),S);
		
		x = S.getX();
		y = S.getY();
		
		addOpen(S,x,y);
		Node next = findMinDist(); 
		
		handleNext(next);
	}
	public Node createN(int[] optie,double dist) {
		Node N;
		N = new Node(optie[0],optie[1]);
		N.setDist(dist);
		
		if(!field.checkDuplicate(field.getBorderList(), N)&&(!field.checkDuplicate(field.getClosedList(), N)))
			field.addNode(field.getOpenList(),N);
		return N;
	}
	public Node findMinDist() {
		Node Nmin = null;
		double min = 99999999;
		for(Node item :field.getOpenList()) {
			if(item.getDist()<min) {
				min = item.getDist();
				Nmin = item;
			}
		}
		return Nmin;
	}
}





