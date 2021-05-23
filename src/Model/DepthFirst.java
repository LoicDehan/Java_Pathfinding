package Model;
//Depthfirst pathfinding
//Looks at every possible path starting top right
//Will always find a path if possible
public class DepthFirst extends Method {
	int x,y;
	public DepthFirst(Field field){
		setField(field);
	}
	public void findPath(Node S) {
		if(S==null)
			return;
		this.setDistReg(field.getSize());
		////////////////////////////////
		
		field.addNode(field.getClosedList(),S);
		
		x = S.getX();
		y = S.getY();
		
		addOpen(S,x,y);
		Node next = findNext(); 
		handleNext(next);
	}
	public Node createN(int[] optie,double dist) {
		Node N;
		N = new Node(optie[0],optie[1]);
		//N.setDist(dist);
		
		if(!field.checkDuplicate(field.getBorderList(), N)&&(!field.checkDuplicate(field.getClosedList(), N)))
			field.addNode(field.getOpenList(),N);
		return N;
	}
	public Node findNext() {
		int fsize = field.getSize();
		Node N;
		int[][] opties = { //[8][2]
				{x,y-fsize},//up
				{x+fsize,y},//right
                {x,y+fsize},//down
                {x-fsize,y},//left
                
				};
		for(int[] optie: opties) {
			N = field.searchNode(field.getOpenList(),optie[0],optie[1]);
			if(N!=null)
				return N;
		}
		return field.getOpenList().get(field.getOpenList().size() - 1);
	}
}
