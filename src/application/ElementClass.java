package application;

public class ElementClass {
	
	private Element element;
	private int counter = 0;
	
	public ElementClass(Element element){
		this.element = element;
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void setCounter(int counter2){
		counter = counter2;
	}
	
	public Element getElement(){
		return element;
	}
	
	public void setElement(Element element2){
		element = element2;
	}
	
}
