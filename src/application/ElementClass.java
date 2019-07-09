package application;

public class ElementClass {
	
	//TODO : To finish Timer
	
	private Element element;
	private int timer;
	private int counterFruit;
	
	public ElementClass(Element element){
		this.element = element;
		this.timer = 0;
		this.counterFruit = 0;
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
	
	public int getCounterFruit(){
		return counterFruit;
	}
	
	public void setCounterFruit(int counterFruit2){
		counterFruit = counterFruit2;
	}
	
	public int incrementerCounterFruit(){
		counterFruit+=1;
		return counterFruit;
	}
	
	public int incrementerTimer(){
		timer+=1;
		return timer;
	}
	
}
