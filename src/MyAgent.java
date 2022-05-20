import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


public class MyAgent extends za.ac.wits.snake.MyAgent {

    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }
    
   // visualDebug vbug = new visualDebug();
   
    /*
    public void vDebug(Graph playarea, List<Vertex> routeToApple, int move){
    	int row = 50;
    	int col = 50;
    	imgView v = new imgView(playarea, row, col, null, null, move);
     //   vbug.update(v.getImage());
    }
    */
    
    ArrayList<Integer> coordinateConversion(String stringCoordinates){
    	ArrayList<Integer> tuple = new ArrayList<>();
    	String[] whatever = stringCoordinates.split(",");
    	
    	int x = Integer.parseInt(whatever[0]);
    	int y = Integer.parseInt(whatever[1]);
    	
    	tuple.add(x); tuple.add(y);
    	return tuple;
    }
    
    public static void AstarSearch(Vertex source, Vertex goal){

        Set<Vertex> explored = new HashSet<Vertex>();

        PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>(20, 
                new Comparator<Vertex>(){
                         //override compare method
         public int compare(Vertex i, Vertex j){
        	 
            if(i.f_scores > j.f_scores){
                return 1;
            }

            else if (i.f_scores < j.f_scores){
                return -1;
            }

            else{
                return 0;
            }
         }

        });
                

        //cost from start
        source.g_scores = 0;

        queue.add(source);

        boolean found = false;

        while((!queue.isEmpty())&&(!found)){

                //the node in having the lowest f_score value
        	Vertex current = queue.poll();

                explored.add(current);

                //goal found
                if(current.vertexNumber == goal.vertexNumber){
                        found = true;
                }

                //check every child of current node
                for(Vertex child : current.adjacencies){
                        double temp_g_scores = current.g_scores;
                        //calculate h_score
                        child.h_scores = Math.abs(current.x - source.x) + Math.abs(current.y - source.y);
                        
                        if(child.type == -1 || child.type == 100) {
                        	temp_g_scores = temp_g_scores + 1;
                        }
                        
                        else {
                        	temp_g_scores = temp_g_scores + 10;
                        }
                        
                        double temp_f_scores = temp_g_scores + child.h_scores;
                        /*if child node has been evaluated and 
                        the newer f_score is higher, skip*/
                        
                        if((explored.contains(child)) && 
                                (temp_f_scores >= child.f_scores)){
                                continue;
                        }

                        /*else if child node is not in queue or 
                        newer f_score is lower*/
                        
                        else if((!queue.contains(child)) || 
                                (temp_f_scores < child.f_scores)){

                                child.parent = current;
                                child.g_scores = temp_g_scores;
                                child.f_scores = temp_f_scores;

                                if(queue.contains(child)){ 
                                        queue.remove(child);
                                }

                                queue.add(child);
                        }
                }
        }
    }
    
    public static List<Vertex> printPath(Vertex target, Vertex snakeHead){
        List<Vertex> path = new ArrayList<Vertex>();

		for(Vertex node = target; node!=null; node = node.parent){
			node.type = 20;
		    path.add(node);
		}
		
		Collections.reverse(path);
		
		return path;
	}
    
    static void drawLine(ArrayList<ArrayList<Vertex>> board, ArrayList<Integer> point1, ArrayList<Integer> point2, int snakeNumber) {
		int length;
		
		int intPos1x = point1.get(0);
		int intPos1y = point1.get(1);
		
		int intPos2x = point2.get(0);
		int intPos2y = point2.get(1);
		
		if(intPos1x == intPos2x) {
			length = Math.abs(intPos1y-intPos2y);
			if(intPos1y > intPos2y) {
				for(int i = 0; i<length+1; i++) {
					board.get(intPos2y+i).get(intPos1x).type = snakeNumber;
				}
			}
			else {
				for(int i = 0; i<length+1; i++) {
					board.get(intPos1y+i).get(intPos1x).type = snakeNumber;
				}
			}
			
		}
		
		else {
			length = Math.abs(intPos1x - intPos2x);
		
			if(intPos1x > intPos2x) {
				for(int i = 0; i<length+1; i++) {
					board.get(intPos2y).get(intPos2x+i).type = snakeNumber;
				}
			}
			else {
				for(int i = 0; i<length+1; i++) {
					board.get(intPos2y).get(intPos1x+i).type = snakeNumber;
				}
			}
		}
	}

    int direction(List<Vertex> path, Vertex snakeHead) {
    	int move = 5;
    	
    	for(Vertex next : path) {
    		if(snakeHead.adjacencies.contains(next)) {
    			
    			if(snakeHead.x == next.x) {
    				if(snakeHead.y > next.y) {
    					//up
    					move = 0;
    				}
    				else {
    					//down
    					move = 1;
    				}
    			}
    			
    			else  {
    				if(snakeHead.x > next.x) {
    					//left
    					move = 2;
    				}
    				else {
    					//right
    					move = 3;
    				}
    			}
    		}
    		
    	}
    	
    	return move;
    }
    
    void buffer(Vertex head) {
    	if(head!=null) {
    		for(Vertex adj : head.adjacencies) {
        		if(adj != null) {
        			adj.type = 6;
        		}
        	}
    	}
    	
    }
    
    int furthestCorner(Snake snake, Vertex cornerVertex, Graph graph) {
    	int move = 5;
    	
    	Vertex mySnakeHeadVertex = snake.getSnakeHead();
    	Vertex mySnakeTailVertex = snake.getSnakeTail();
    	
    	AstarSearch(mySnakeHeadVertex, cornerVertex);
    	if(cornerVertex.parent == null) {
    		if(!mySnakeTailVertex.adjacencies.isEmpty()) {
    			Vertex snakeTailAdj = mySnakeTailVertex.adjacencies.get(0);
    			AstarSearch(mySnakeHeadVertex, snakeTailAdj);
    			List<Vertex> path = printPath(snakeTailAdj, mySnakeHeadVertex);
    	        move = direction(path, mySnakeHeadVertex);
    		}
    	}
    	
    	else {
	        List<Vertex> path = printPath(cornerVertex, mySnakeHeadVertex);
	        move = direction(path, mySnakeHeadVertex);
    	}
    	
    	return move;
    }
    
    int freshness = 5;
    ArrayList<ArrayList<Integer>> applePosition = new ArrayList<ArrayList<Integer>>();
    
    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);
            
            
            while (true) {
            	Graph graph = new Graph(2500);
            	
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }

                String apple1 = line;
                
                //do stuff with apples
                String[] appleStringCoordinates = apple1.split(" ");
                int appleX = Integer.parseInt(appleStringCoordinates[0]);
                int appleY = Integer.parseInt(appleStringCoordinates[1]);
                ArrayList<Integer> applePos = new ArrayList<Integer>();
                applePos.add(appleX); applePos.add(appleY);
                applePosition.add(applePos);
                //check if apple is fresh
                if(applePosition.size() > 1) {
                	if( applePosition.get( applePosition.size()-1 ).equals( applePosition.get(applePosition.size()-2) )) {
                		freshness-= 0.1;
                	}
                	else {
                		freshness = 5;
                	}
                }
                
              
                Vertex appleVertex = graph.board.get(appleY).get(appleX);
                if(freshness < 1) {
                	appleVertex.fresh = false;
                }
                
                else {
                	appleVertex.fresh = true;
                }
                
                appleVertex.isApple = true;
                
                //set apple type to 100, for debugging
                graph.board.get(appleY).get(appleX).type = 100;
                
                // read in obstacles and do something with them!
                int nObstacles = 3;
                for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                    String obs = br.readLine();
                    String[] obsStringCoordinates = obs.split(" ");
                    ArrayList<ArrayList<Integer>> obsCoordinates = new ArrayList<ArrayList<Integer>>();
                    for(String s : obsStringCoordinates) {
                    	ArrayList<Integer> coordinate = coordinateConversion(s);
                    	obsCoordinates.add(coordinate);
                    	graph.board.get(coordinate.get(1)).get(coordinate.get(0)).type = 50;
                    }
                }

                int mySnakeNum = Integer.parseInt(br.readLine());
                List<Snake> allSnakes = new ArrayList<Snake>();
                Snake mySnake = new Snake(mySnakeNum);
                
                for (int i = 0; i < nSnakes; i++) {
                	
                    String snakeLine = br.readLine();
                    String[] snakeStringDetails = snakeLine.split(" ");
                    
                    ArrayList<ArrayList<Integer>> snakeCoordinates =  new ArrayList<ArrayList<Integer>>();
                    
                    if (i == mySnakeNum) {
                    	//hey! That's me :)
                    	for(int j = 3; j<snakeStringDetails.length; j++) {
                    		ArrayList<Integer> coord = coordinateConversion(snakeStringDetails[j]);
                    		snakeCoordinates.add(coord);
                    		
                    		Vertex v = graph.board.get(coord.get(1)).get(coord.get(0));
                    		if(j==3) {
                    			v.type = 15;
                    			
                    		}
                    		else {
                    			v.type = 10;
                    		}
                    		
                    		mySnake.snakeVertices.add(v);
                    	}
                    	
                    	for(int k = 0; k<snakeCoordinates.size()-1; k++) {
                    		drawLine(graph.board, snakeCoordinates.get(k), snakeCoordinates.get(k+1), 10);
                    	}
                        
                    	allSnakes.add(mySnake);
                    }
                    //do stuff with other snakes
                    else {
                    	Snake otherSnake = new Snake(i);
                    	
                    	for(int j = 3; j<snakeStringDetails.length; j++) {
                    		ArrayList<Integer> coord = coordinateConversion(snakeStringDetails[j]);
                    		snakeCoordinates.add(coord);
                    		
                    		Vertex v = graph.board.get(coord.get(1)).get(coord.get(0));
                    		otherSnake.snakeVertices.add(v);
                    		if(j==3) {
                    			v.type = 7;
                    		}
                    		
                    		otherSnake.snakeVertices.add(v);
                    	}
                    	
                    	for(int k = 0; k<snakeCoordinates.size()-1; k++) {
                    		drawLine(graph.board, snakeCoordinates.get(k), snakeCoordinates.get(k+1), i);
                    	}
                    	allSnakes.add(otherSnake);
                    }
                }
                
                graph.makeEdges();
                
                for(Snake s : allSnakes) {
                	if(s == mySnake) {
                		continue;
                	}
                	Vertex v = s.getSnakeHead();
                
                	buffer(v);
                }
                
                
                Vertex mySnakeHeadVertex = mySnake.getSnakeHead();
                ArrayList<Vertex> snakeHeads = new ArrayList<Vertex>();
                for(Snake snake : allSnakes){
                	Vertex head = snake.getSnakeHead();
                	
                	if(head != null) {
                		head.h_scores = Math.abs(head.x - appleVertex.x) + Math.abs(head.y - appleVertex.y);
                		snakeHeads.add(head);
                	}
                	
                }
                
                PriorityQueue<Vertex> snakeHeadQueue = new PriorityQueue<Vertex>(20, 
                        new Comparator<Vertex>(){
                    //override compare method
				    public int compare(Vertex i, Vertex j){
				   	 
				       if(i.h_scores > j.h_scores){
				           return 1;
				       }
				
				       else if (i.h_scores <= j.h_scores){
				           return -1;
				       }
				
				       else{
				           return 0;
				       }
				    }
				
				   });
                
                for(Vertex head : snakeHeads) {
                	snakeHeadQueue.add(head);
                }
                //each corner vertex:
                
                //finished reading, calculate move:
               Timer myTimer = new Timer();
               myTimer.start();
               
                int move = 5;
                if(snakeHeadQueue.peek() == mySnakeHeadVertex) {
                	
	        		AstarSearch(mySnakeHeadVertex, appleVertex);
	                List<Vertex> path = printPath(appleVertex, mySnakeHeadVertex);
	                mySnakeHeadVertex.type = 15;
	                move = direction(path, mySnakeHeadVertex);
	        	
             }
                
                else {
                	List<Integer> furthestCorner = new ArrayList<Integer>();
                	Map<Integer, Vertex> myMap = new HashMap<Integer, Vertex>();
                	
                	for(int i = 0; i<50; i+=49) {
                		for(int j = 0; j<50; j+=49) {
                			int distanceCnr = Math.abs(mySnakeHeadVertex.x - j) + Math.abs(mySnakeHeadVertex.y - i);
                			furthestCorner.add(distanceCnr);
                			myMap.put(distanceCnr, graph.board.get(i).get(j));
                		}
                	}
                	
                	Collections.sort(furthestCorner);
                	
                	Vertex chosenVertex = myMap.get(furthestCorner.get(3));
                	
                	move = furthestCorner(mySnake, chosenVertex, graph);
                	
               	}
               // myTimer.stop();
                System.err.println(move);
                    
                System.out.println(move);
               // vDebug(graph, null, move);
               
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}