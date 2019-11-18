package Gui;
import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*; 
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
public class Kscreen implements ActionListener{

	//private static final int EXIT_ON_CLOSE = 0;
	int pid;
	int portid;
	String pname;
	String fileloc;
	String whitelistfile="Whitelist.txt";		//path of the file that stores the whitelisted applications'
	
	
	JButton sc_bt, op_bt, kill_bt, del_bt,white_bt;
	
	public Kscreen(int id, String name, String path)
	{
			//setInfo(id, name, path);
		
			this.pid=id;
			this.pname=name;
			this.fileloc=path;
		
		
		
		
		
			JFrame f = new JFrame("KeyLogger !");
			//f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			JPanel p = new JPanel();
			
		
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
			

			
		
			
			
			op_bt = new JButton("Open File Location");
			op_bt.setBackground(Color.LIGHT_GRAY);
			kill_bt = new JButton("Kill Process");
			kill_bt.setBackground(Color.RED);
			del_bt = new JButton("Delete File");
			del_bt.setBackground(Color.ORANGE);
			white_bt = new JButton("Whitelist File");
			white_bt.setBackground(Color.GREEN);
			p.add(op_bt);
			op_bt.addActionListener(this);
			p.add(kill_bt);
			kill_bt.addActionListener(this);
			p.add(del_bt);
			del_bt.addActionListener(this);
			p.add(white_bt);
			white_bt.addActionListener(this);
			
			
			//f.add(warningLabel,"Center");
			f.add(p,"South");
			
			f.pack(); 
			
			f.setVisible(true);
		
					

		}
		
	public String  getFilePathString(String fileloc)
	{
		
		//String filelocpath="";
		
		fileloc.substring(0,fileloc.lastIndexOf("\\") );
		System.out.println(fileloc.substring(0,fileloc.lastIndexOf("\\") ));
	
		return(fileloc.substring(0,fileloc.lastIndexOf("\\") ));
		
		
		
	}
		
		
		public void actionPerformed(ActionEvent e) {
			
			boolean bool=false;
			
			String filePath = "explorer "+getFilePathString(fileloc);
			
       
			
		if(e.getSource()== op_bt)
		{
			 try{
			 
			 Runtime.getRuntime().exec(filePath);
			 }
			 catch(IOException ioe)
			 {
				 ioe.printStackTrace(); 
			 }
		}
		
		if(e.getSource()== del_bt)
		{
			try{
				System.out.println(fileloc);
				//File file = new File("C:\\Program Files\\HelloWorld.txt"); 
				File file = new File(fileloc); 
				bool = file.delete();
				System.out.println("File deleted: "+bool);
				
				
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		
		
		if(e.getSource()== kill_bt)
		{
			try{
				String killcommand = "taskkill /F /PID " + pid;
				Runtime.getRuntime().exec(killcommand);
				//System.out.println("Process Terminated!");
				//Runtime.getRuntime().exec("taskkill /F /IM <processname>.exe")		using processname
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		if(e.getSource()== white_bt)
		{
			try{
				
				BufferedWriter out = new BufferedWriter(new FileWriter(whitelistfile, true)); 
				out.write(fileloc);
				out.newLine();
	            out.close();
				
			}catch(IOException ex)
			{
				System.out.println("exception occoured" + ex); 
			}
		}
		
		
		
    }
			
	
	
	
	
	
	public String readLabel() {
		
		return(pid+" - "+pname+" located at "+fileloc+" is attempting to communicate through an email!");
	}
	

}
