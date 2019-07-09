package application;

public class ElementClass {
	
	Element element;
	private int counter = 0;
	
	public ElementClass(Element element, int counter){
		this.element = element;
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void setCounter(int counter2){
		counter = counter2;
	}
	
}
