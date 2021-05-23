package Model;

//abstract class for the different pathfinding methodes:
// A*,Depthfirst, Dijkstra
public abstract class Method {
	Field field;
	double DistDia;
	double Reg;
	public abstract void findPath(Node current);
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public double getDistDia() {
		return DistDia;
	}
	public void setDistDia(double d) {
		DistDia = d;
	}
	public double getDistReg() {
		return Reg;
	}
	public void setDistReg(double d) {
		Reg = d;
	}
	
	public void addOpen(Node S,int x,int y) {
		//add neighbours of node S to open list
		Node N;
		int fsize = field.getSize();
		int[][] opties = { //[8][2]
				{x,y-fsize},//up
				{x+fsize,y},//right
                {x,y+fsize},//down
                {x-fsize,y},//left
				};
		int[][] optiesDia = { //[8][2]
				{x+fsize,y-fsize},//up-right
				{x+fsize,y+fsize},//down-right
                {x-fsize,y+fsize},//down-left
				{x-fsize,y-fsize},//up-left                
				};
		
		//Regular neighbours: up down left right
		for(int[] optie:opties) {
			N = createN(optie,S.getDist()+getDistReg());
			N.setNext(S);
		}
		//Diagonal neighbours
		if(field.isDiagonal()) {
			for(int[] optie:optiesDia) {
				N = createN(optie,S.getDist()+getDistDia());
				N.setNext(S);
			}
		}			
	}
	public abstract Node createN(int[] optie,double dist);
	
	public void handleNext(Node next) {
		//Add node to passed nodes
		if(next == null){
			field.setNoPath(true);
			return;
		}
				
		field.setNext(next);
		if(field.getEnd().equals(next)) {
			field.removeNode(field.getOpenList(), next);
			field.setEnd(next);
			finishPath();
			field.setComplete(true);
			return;
		}
			
		field.addNode(field.getClosedList(), next);
		field.removeNode(field.getOpenList(), next);
	}
	
	public void finishPath() {
		//show path
		Node E = field.getEnd();
		Node next = E.getNext();
		field.addNode(field.getPathList(),E);
		if(next == null) {
			System.out.println("Incomplete path");
			return;
		}
		while(!field.getStart().equals(next)) {
			field.addNode(field.getPathList(),next);
			next = next.getNext();
		}		
			
		field.addNode(field.getPathList(),field.getStart());
		field.reverse(field.getPathList());
	}
}
