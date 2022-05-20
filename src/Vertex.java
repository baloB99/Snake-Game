import java.util.*;

public class Vertex {
	
	int vertexNumber, colour, x, y, type, greyness;
	double g_scores, h_scores, f_scores;
	boolean isApple, visited, fresh;
	
	Vertex parent;
	ArrayList<Vertex> children = new ArrayList<Vertex>();
	
	LinkedList<Vertex> adjacencies = new LinkedList<Vertex>();

	public Vertex(int vertexNumber) {
		this.vertexNumber = vertexNumber;
		this.colour = -1;
		this.type = -1;
		this.g_scores = 0;
		this.parent = null;
		this.greyness = -1;
		this.fresh = true;
		// TODO Auto-generated constructor stub
	}
	
	void addAdjacency(Vertex object) {
		this.adjacencies.add(object);
	}
	
	boolean isAdjacent(Vertex object) {
		for(Vertex vertex: this.adjacencies) {
			if(vertex.vertexNumber == object.vertexNumber) {
				return true;
			}
		}
		return false;
	}
	
	int getDegree() {
		return this.adjacencies.size();
		
	}	
	
	void setColor(int degree) {
		
		int[] used = new int[degree];
		int smallest_unused_colour = 0;
		
		for(Vertex v : adjacencies) {
			int c = v.colour;
			
			if(c != -1) {
				used[c] = 1;
			}
		}
			
		for(int i = 0; i<used.length; i++) {
			if(used[i] == 0) {
				smallest_unused_colour = i;
				break;
			}
		}
		
		this.colour = smallest_unused_colour;
	}

}