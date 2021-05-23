package Model;
//A* pathfinding implementation
//f(n) = g(n) + h(n) [cost]
//g(n) = distance travelled [dist]
//h(n) = estimated distance to end [heuristic h]
public class A extends Method{
	int x,y;
	public A(Field field){
		setField(field);
	}

	public void findPath(Node S) {
		if(S==null)
			return;
		this.setDistDia(field.getDiagonalCost());
		this.setDistReg(field.getSize());
		
		//Add start node to path
		field.addNode(field.getClosedList(),S);
		
		x = S.getX();
		y = S.getY();
		
		addOpen(S,x,y);
		Node next = findMinCost(); 
		handleNext(next);
			

	}
	public Node createN(int[] optie,double dist) {
		Node N;
		N = new Node(optie[0],optie[1]);
		N.setDist(dist);
		
		double xdist = Math.pow((field.getEnd().getX()-N.getX()), 2);
		double ydist =Math.pow((field.getEnd().getY()-N.getY()), 2);
		double h = Math.sqrt(xdist+ydist);//shortest path to end
		N.setH(h);
		if(!field.checkDuplicate(field.getBorderList(), N)&&(!field.checkDuplicate(field.getClosedList(), N)))
			field.addNode(field.getOpenList(),N);
		return N;
	}
	public Node findMinCost() {
		Node Nmin = null;
		double min = 99999999;
		for(Node item :field.getOpenList()) {
			if(item.getCost()<min) {
				min = item.getCost();
				Nmin = item;
			}
		}
		return Nmin;
	}
}










