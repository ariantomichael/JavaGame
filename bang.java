import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;

public class bang extends JFrame{
	public bang(){
		setSize(800,500);	
		setTitle("Game Example");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MyJPanel myJPanel= new MyJPanel();
		Container c = getContentPane();
		c.add(myJPanel);
		setVisible(true);
	}
	public static void main(String[] args){
		new bang();
	}
	public class MyJPanel extends JPanel
	implements ActionListener, MouseListener,MouseMotionListener{
		int my_x;
		int player_width,player_height;
		int enemy_width,enemy_height;
		int n;
		int enemy_x[];
		int enemy_y[];

		int enemy_move[];
		int enemy_alive[];
 
	    int my_missile_x[];
	    int my_missile_y[];
		int missile_flag[];

		public static final int MY_Y=400;
		
	    Image image,image2,image3;
		Timer timer;

		public MyJPanel(){
		    int i;
			my_x = 250;
			missile_flag = new int[20];
			for(i=0;i<20;i++){
			missile_flag[i]=0;
			}
			n = 14;
			enemy_x = new int[n];
			enemy_y = new int[n];

			enemy_move = new int[n];
			enemy_alive= new int[n];
			my_missile_y = new int[20];
			my_missile_x = new int[20];
			for(i=0;i<7;i++){
				enemy_x[i] = 70*(i+1)-50;
				enemy_y[i] = 50;
			}
			for(i=7;i<n;i++){
				enemy_x[i] = 70*(i-5)- 50;
				enemy_y[i] = 100;
			}
			for(i=0;i<n;i++){
				enemy_alive[i] = 1;
				enemy_move[i] = -3;
			}

			ImageIcon icon = new ImageIcon("Mirana.png");
			image = icon.getImage();

			ImageIcon icon2 = new ImageIcon("void.png");
			image2 = icon2.getImage();
			ImageIcon icon3 = new ImageIcon("arrow.png");
			image3 = icon3.getImage();

			player_width = image.getWidth(this);
			player_height= image.getHeight(this);

			enemy_width = image2.getWidth(this);
			enemy_height= image2.getHeight(this);
			setBackground(Color.black);
			addMouseListener(this);
			addMouseMotionListener(this);
			timer = new Timer(50, this);
			timer.start();
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,my_x,400,this);
			for(int i=0;i<1;i++){
				if(enemy_alive[i] == 1){
					g.drawImage(image2,enemy_x[i],enemy_y[i],this);
				}
			}
			for(int i=0;i<20;i++){
			    if(missile_flag[i] ==1){
				g.setColor(Color.white);
				g.drawImage(image3,my_missile_x[i],my_missile_y[i],this);
			
			    }
			}
		}
		public void actionPerformed(ActionEvent e){
		    Dimension dim=getSize();
		    for(int i=0; i< n;i++){
			enemy_x[i] +=enemy_move[i];
			if((enemy_x[i]<0)||(enemy_x[i]>(dim.width-enemy_width))){
			    enemy_move[i] = -enemy_move[i];
			}
		    }
		    for(int i=0; i<20;i++){
			if(missile_flag[i]==1){
			    my_missile_y[i] -= 3;
			    for(int k=0;k<1;k++){
				if(my_missile_y[i]>enemy_y[k] && my_missile_y[i]<enemy_y[k]+enemy_height && my_missile_x[i]>enemy_x[k] && my_missile_x[i]<enemy_x[k]+enemy_width){
				    enemy_alive[k] =0;
				    missile_flag[i]=0;
				}
			    }
			    if(0 > my_missile_y[i]){
				missile_flag[i] = 0;
			    } 				 
			}
		    }
		    repaint();
		}
		public void mouseClicked(MouseEvent me){
		    
		}
		public void mousePressed(MouseEvent me){
		    for(int i=0;i<20;i++){
			if(missile_flag[i]== 0){
			    my_missile_x[i] = my_x + player_width / 2;
			    my_missile_y[i] = MY_Y;//MY_Y=400
			    missile_flag[i] = 1;
			    break;
			} 
			else continue;
		    }
		    
			
		
		}
		public void mouseReleased(MouseEvent me){
		}
		public void mouseExited(MouseEvent me){
		}
		public void mouseEntered(MouseEvent me){
		}
		public void mouseMoved(MouseEvent me){
			my_x = me.getX();
		}
		public void mouseDragged(MouseEvent me){
		}
	}
}
