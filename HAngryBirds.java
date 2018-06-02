import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;

public class HAngryBirds extends JFrame{
	
	public HAngryBirds(){
		setSize(800,500);
		setTitle("HAngry Birds...");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		MyJPanel myJPanel= new MyJPanel();
		Container c = getContentPane();
		c.add(myJPanel);
		setVisible(true);
		setResizable(false);
	}
	
	public static void main(String[] args){
		new HAngryBirds();
	}
	
	public class MyJPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	    Timer timer;
	    Image image, image2, image3;
		int my_x, my_y;
		int mouse_x, mouse_y;
		int start_x, start_y;
		int init_x=100, init_y=375;
	    double t=0.0,t1, v=140.0;
		double v_x, v_y;
		int my_width, my_height;
		int grab_flag=0;
	    int release_flag=0;
	    int boss_y;
	    int boss_height, boss_width;
	    int boss_move;
	    int boss_hp;
		
			
		public MyJPanel(){
			setBackground(Color.white);
			addMouseListener(this);
			addMouseMotionListener(this);
			
			boss_move=15;
			boss_y=200;
			boss_hp=3;
			
			ImageIcon icon = new ImageIcon("bird.jpg");
			image = icon.getImage();
			my_width = image.getWidth(this);
			my_height = image.getHeight(this);
			my_x = init_x;
			my_y = init_y;

			ImageIcon icon2 = new ImageIcon("boss.png");
			image2 = icon2.getImage();
			boss_height= image.getHeight(this);
			boss_width= image.getWidth(this);
			ImageIcon icon3 = new ImageIcon("boom.jpg");
			image3 = icon3.getImage();
			timer = new Timer(100,this);
			timer.start();
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
		     
			g.drawImage(image,my_x,my_y,this);
			g.drawImage(image2,600,boss_y,this);
						if(boss_hp==3){
			    g.setColor(Color.green);
			    g.fillRect(605,boss_y-20,115,15);
			}
						else if(boss_hp==2){
			    g.setColor(Color.yellow);
			    g.fillRect(605,boss_y-20,76,15);
						}
						else if(boss_hp==1){
			    g.setColor(Color.red);
			    g.fillRect(605,boss_y-20,38,15);
						} else {
						    g.drawImage(image3,450,boss_y,this);
						}
		

			g.setColor(Color.black);
			g.fillRect(95+my_width/2,400,10,100);
			if(grab_flag==1){
			    g.drawLine(95+my_width/2,400,my_x+my_width/2,my_y+my_height/2);
			}
		
		}
		
		public void actionPerformed(ActionEvent e){
			Dimension d;
			d=getSize();
			

			boss_y += boss_move;
			if((boss_y<0) || (boss_y > (d.height-125))) boss_move = -boss_move;
//-------------------------------------------------------------------
				if(boss_hp==0){
				    boss_move=0;
				    t+=0.2;
				    if(t>5){
				    System.out.println("CONGRATULATIONS!!!! You killed the boss!!");
				    System.exit(0);
				    }
				}
			if(release_flag==1){
			    t+=0.2;
			    my_x = (int)(v*v_x*t+start_x);
			    my_y = (int)(9.8*t*t/2 - v*v_y*t+start_y);
			    if((my_x+my_width>615)&&(my_x<730)&&(my_y+my_height>boss_y)&&(my_y<boss_y+125)){
				boss_hp=boss_hp-1;
			
				my_x=init_x;
				my_y=init_y;
				t=0.0;				
			
				grab_flag=0;
				release_flag=0;
			    }
			    }
			
//-------------------------------------------------------------------
		
			if(grab_flag==0){
			    if((my_x<0)||(my_x>d.width)||(my_y>d.height)||(my_y<0)){
			
				my_x=init_x;
				my_y=init_y;
				t=0.0;				
			
				grab_flag=0;
				release_flag=0;
			    }
			}
			repaint();
		}
	
		
		public void mouseClicked(MouseEvent me)
		{
		}
		
		public void mousePressed(MouseEvent me)
		{
			mouse_x = me.getX();
			mouse_y = me.getY();
			if((grab_flag==0)&&(release_flag==0)&&(my_x<mouse_x)&&(mouse_x<my_x+my_width)&&(my_y<mouse_y)&&(mouse_y<my_y+my_height)){
				grab_flag = 1;
				start_x = mouse_x;
				start_y = mouse_y;
			}
		}
		
		public void mouseReleased(MouseEvent me)
		{
			if(grab_flag==1){
			
//-------------------------------------------------------------------
			    v_x = (double)(start_x-(my_x+my_width/2))/100;
			    v_y = -(double)(start_y-(my_y+my_height/2))/100;
//-------------------------------------------------------------------
				start_x = my_x;
				start_y = my_y;
				t=0.0;
				release_flag=1;
				grab_flag=0;
			}
		}
		
		public void mouseExited(MouseEvent me)
		{
		}
		
		public void mouseEntered(MouseEvent me)
		{
		}
		
		public void mouseMoved(MouseEvent me)
		{
		}
		
		public void mouseDragged(MouseEvent me)
		{
		    Dimension d;
		    d = getSize();
			if(grab_flag==1){
				mouse_x = me.getX();
				mouse_y = me.getY();
				my_x = init_x - (start_x-mouse_x);
				my_y = init_y - (start_y-mouse_y);
				if(my_y<0) my_y=0;
				if(my_x<0) my_x=0;
				if(my_y>d.height-my_height) my_y=d.height-my_height;
				if(my_x>d.width-my_width) my_x=d.width-my_width;
				repaint();
			}
		}
	}
}
