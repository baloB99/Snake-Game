import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class visualDebug 
{
	JFrame frame;
	JLabel label;
	ImageIcon icon;
	
	visualDebug()
	{
		icon = new ImageIcon("Output.png");
		label = new JLabel(icon);
		
		label.setSize(450,450);
		
		frame = new JFrame("Visual Debugger");
		frame.add(label);
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void update(BufferedImage img)
	{
		Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		icon.setImage(newImg);
		label.repaint();
	}
	
}