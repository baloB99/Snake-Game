import java.awt.Color;
//import java.awt.geom.AffineTransform;
//import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import javax.imageio.ImageIO;

public class imgView {
	
	int x;
	int y;
	Graph playArea;
	BufferedImage img;
	Vertex apple;
	ArrayList<Snake> snakes;
	List<Vertex> path = null;
	Vertex next = null;
	
	public imgView(Graph data, int row, int col, Vertex appleVertex, List<Vertex> routeToApple, int move)
	{
		this.playArea = data;
		this.x = col;
		this.y = row;
		this.apple = appleVertex;
		this.path = routeToApple;
		this.next = playArea.getVertex(move);
		//snakes = snakeData;
		this.img = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
	}
	
	BufferedImage getImage()
	{
		updateImage();
		if(path != null) {
		//	displayPath();
		}
		//displaySnakes();
		
		//displayApple(apple);
		//displayNext();
		return img;
	}
	
	
	public void updateImage()
	{
		//File f = null;
		
		for(int i=0;i<y;i++)
		{
			for(int j =0;j<x;j++)
			{
				
				Vertex v = playArea.board.get(j).get(i);
			
				if(v.type == 50)
				{ //obstacles
					img.setRGB(i,j,Color.black.getRGB());
				}
				else if(v.type == -1)
				{ //empty space
					img.setRGB(i,j,Color.gray.getRGB());
				}
				else if(v.type == 10)
				{//my snake
					img.setRGB(i,j,Color.red.getRGB());
				}
				
				else if(v.type == 1) { //first snake
					img.setRGB(i,j,Color.green.getRGB());
				}
				else if(v.type == 2) { //second snake
					img.setRGB(i,j,Color.blue.getRGB());
				}
				else if(v.type == 3) { //third snake
					img.setRGB(i,j,Color.orange.getRGB());
				}
			
				else if(v.type == 6) {//snake buffer
					img.setRGB(i,j,Color.cyan.getRGB());
				}
				
				else if(v.type == 7) { //snakeTails
					img.setRGB(i,j,Color.white.getRGB());
				}
				else if(v.type == 20) { // path 
					img.setRGB(i,j,Color.pink.getRGB());
				}
				
				else if(v.type == 100) {  //apple
					img.setRGB(i,j,Color.RED.getRGB());
				}
				
				else if(v.type == 15) {  //snakeHead
					img.setRGB(i,j,Color.ORANGE.getRGB());
				}
				
				else { 
					img.setRGB(i,j,Color.white.getRGB());
				}
			}
		}
		
	}
	

}