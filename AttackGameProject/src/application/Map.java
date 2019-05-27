package application;


public class Map {
	
	private Element map[][];
	private int width;
	private int height;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new Element[width][height];
		InitRandomMap(map);
	}

	void InitRandomMap(Element[][] map) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				map[i][j] = Element.empty;
				//TODO with proba
			}
		}
	}
}