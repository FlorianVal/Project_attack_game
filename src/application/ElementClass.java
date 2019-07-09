package application;

public class ElementClass {
	
	//TODO : To finish Timer + to implement when bbtrex grow up
	
	private Element element;
	private int timer;
	private boolean mate;
	
	public ElementClass(Element element){
		this.element = element;
		this.timer = 0;
		this.mate = false;
	}
	
	public int getTimer(){
		return timer;
	}
	
	public void setCounter(int timer2){
		timer = timer2;
	}
	
	public Element getElement(){
		return element;
	}
	
	public void setElement(Element element2){
		element = element2;
	}
	
	public boolean getMate(){
		return mate;
	}
	
	public void setMate(boolean mate2){
		mate = mate2;
	}
	
}
