package application;


public class Map {
	
	private Element map[][];
	
	public Map(int width, int height) {
		map = new Element[width][height];
		InitRandomMap(map);
	}

	void InitRandomMap(Element[][] map) {
		
	}
}