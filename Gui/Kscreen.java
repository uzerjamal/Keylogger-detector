package Gui;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
public class Kscreen {

	int pid;
	int portid;
	String pname;
	String fileloc;
	
	
	
	
	public Kscreen()
	{
		
		
		
			JFrame f = new JFrame("KeyLogger !");
			JButton sc_bt, op_bt, kill_bt, del_bt;
			JPanel p = new JPanel();
			
		/*
		 * ImageIcon icon = new ImageIcon("warn_klog.png"); Image normalImage =
		 * icon.getImage(); Icon warningIcon = new ImageIcon(normalImage); JLabel
		 * warningLabel = new JLabel(warningIcon);
		 */
			try {
				
				JPanel p1 = new JPanel();	
			//BufferedImage myPicture = ImageIO.read(new File("warn_klog.png")); 
			BufferedImage myPicture = ImageIO.read(new File("Gui/warn_klog.png")); 
			
			JLabel picLabel = new JLabel(new ImageIcon(myPicture)); 
			p1.add(picLabel);
			f.add(p1,"North");
			
			}catch(IOException e) {
			}
			
			
			
			JPanel p2 = new JPanel();
			JLabel iLabel = new JLabel(readLabel()); 
			
			p2.add(iLabel);
			p2.setBackground(Color.YELLOW) ;
			f.add(p2,"Center");
			

			
			//C:\\Users\\Home\\Downloads\\Kwarn.png
			
			sc_bt = new JButton("Scan the File");
			
		    sc_bt.setBackground(Color.GREEN);
			op_bt = new JButton("Open File Location");
			op_bt.setBackground(Color.LIGHT_GRAY);
			kill_bt = new JButton("Kill Process");
			kill_bt.setBackground(Color.RED);
			del_bt = new JButton("Delete File");
			del_bt.setBackground(Color.ORANGE);
			p.add(sc_bt);
			p.add(op_bt);
			p.add(kill_bt);
			p.add(del_bt);
			
			//f.add(warningLabel,"Center");
			f.add(p,"South");
			
			f.pack(); 
			
			f.setVisible(true);
		
					

		}
	
	
	
	public void setInfo(int pid, String pname, String fileloc,int portid)
	{
		this.pid=pid;
		this.pname=pname;
		this.fileloc=fileloc;
		this.portid =portid;
	}
	
	
	public String readLabel() {
		return(pid+" - "+pname+" located at "+fileloc+" is attempting to communicate through port "+portid);
	}
	

}
