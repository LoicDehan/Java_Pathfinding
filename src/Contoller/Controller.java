package Contoller;
import Model.*;

public class Controller {
	Field field;
	private Method method;
	private Method method1,method2,method3;
	public Controller(Field field) {
		this.field = field;
		method1 =  new Dijkstra(field);
		method2 =  new A(field);
		method3 =  new DepthFirst(field);
		method = method1;
	}
	
	public Method getMethod() {
		return method;
	}
	public void setMethod(String m) {
		Method prevMethod = method;
		if (m == "Dijkstra")
				method = method1;
		else if(m == "A*")
			method = method2;
		else if(m == "DepthFirst") {
			method = method3;
			field.setDiagonal(false);
		}
			
		if (method != prevMethod)
			field.getFrame().clear();
	}
	
	public void resetLists() {
		field.reset();
	}
}
