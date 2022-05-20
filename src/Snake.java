import java.util.ArrayList;

public class Snake {
	int number;
	ArrayList<Vertex> snakeVertices = new ArrayList<Vertex>();
	
	public Snake(int snakeNumber) {
		this.number = snakeNumber;
		this.snakeVertices = new ArrayList<>();
	}
	
	Vertex getSnakeHead() {
		Vertex v = null;
		
		if(snakeVertices.size() > 0) {
			return snakeVertices.get(0);
		}
		
		return v;
	}
	
	Vertex getSnakeTail() {
		Vertex v = null;
		
		if(snakeVertices.size() > 1) {
			return snakeVertices.get(snakeVertices.size() - 1);
		}
		
		return v;
	}
}


