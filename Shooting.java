import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;

public class Shooting extends JFrame{
	public Shooting(){
		setSize(800,500);	
		setTitle("Game Example");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MyJPanel myJPanel= new MyJPanel();
		Container c = getContentPane();
		c.add(myJPanel);
		setVisible(true);
	}
	public static void main(String[] args){
	new Shooting();
	}
	public class MyJPanel extends JPanel
	    implements ActionListener, MouseListener,MouseMotionListener, KeyListener
	{
		int my_x;
		int player_width,player_height;
		int enemy_width,enemy_height;
		int n;
		int enemy_x[];
		int enemy_y[];

		int enemy_move[];
		int enemy_alive[];
		int num_of_alive;
 
		int my_missile_x,my_missile_y;
		int enemy_missile_x[];
		int enemy_missile_y[];
		int enemy_missile_move[];
		int enemy_missile_flag[];
		int missile_flag;

		public static final int MY_Y=400;
		
		Image image,image2;
		Timer timer;

		public MyJPanel(){
			my_x = 250;
			missile_flag=0;
			int i;
			n = 14;
			num_of_alive = 14;
			enemy_x = new int[n];
			enemy_y = new int[n];

			enemy_move = new int[n];
			enemy_alive= new int[n];
			enemy_missile_x = new int[n];
			enemy_missile_y = new int[n];
			enemy_missile_move = new int[n];
			enemy_missile_flag = new int[n];

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
				enemy_move[i] = -10;
			}

			for(i=0;i<n;i++ ){
				enemy_missile_x[i] = enemy_x[i]+enemy_width/2;
				enemy_missile_y[i] = enemy_y[i]+enemy_height;

				enemy_missile_move[i] = 10;
				enemy_missile_flag[i] = 1;
				
			}

			ImageIcon icon = new ImageIcon("player.jpg");
			image = icon.getImage();

			ImageIcon icon2 = new ImageIcon("enemy.jpg");
			image2 = icon2.getImage();

			player_width = image.getWidth(this);
			player_height= image.getHeight(this);

			enemy_width = image2.getWidth(this);
			enemy_height= image2.getHeight(this);
			setBackground(Color.black);
			addMouseListener(this);
			addMouseMotionListener(this);
			setFocusable(true);
			addKeyListener(this);
			timer = new Timer(50, this);
			timer.start();
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(image,my_x,400,this);
			for(int i=0;i<n;i++){
				if(enemy_alive[i] == 1){
					g.drawImage(image2,enemy_x[i],enemy_y[i],this);
				}
			}
			for(int i=0;i<n;i++){
				if(enemy_missile_flag[i] == 1){
					g.setColor(Color.white);
					g.fillRect(enemy_missile_x[i],enemy_missile_y[i],2,5);
				}
			}
			if(missile_flag ==1){
				g.setColor(Color.white);
				g.fillRect(my_missile_x,my_missile_y,2,5);
			}

		}
		public int detect_hit_of_enemy_missile(int i){
			if( enemy_missile_flag[i] == 1 ){
                              if( ((enemy_missile_x[i]+2) >= my_x) &&
				  ((my_x+player_width) > enemy_missile_x[i]) && 
				  ( (enemy_missile_y[i]+5) >= MY_Y ) && 
				  ((MY_Y + player_height) > enemy_missile_y[i]) ){
					return 1;
                                }
			}
			return 0;
		}
		public int detect_hit_of_my_missile(int i){
			if(enemy_alive[i] == 1){
				if( (enemy_x[i] <= my_missile_x) && 
				    ( my_missile_x < (enemy_x[i]+enemy_width)) && 
				    ((enemy_y[i]+enemy_height) >= my_missile_y) && 
				    enemy_y[i] < (my_missile_y+5) ){
					return 1;
				}  
			}
			return 0;
		}
		public void actionPerformed(ActionEvent e){
			Dimension dim=getSize();

			for(int i=0; i< n;i++){
				enemy_x[i] +=enemy_move[i];
				if( (enemy_x[i] < 0) || ( enemy_x[i]> (dim.width - enemy_width) ) ){
					enemy_move[i] = - enemy_move[i];
				}
				
				if(enemy_missile_flag[i]==1){
					enemy_missile_y[i] += enemy_missile_move[i];
					if (enemy_missile_y[i] > 500){
						enemy_missile_flag[i] = 0;
					}
				}else{
					enemy_missile_x[i] = enemy_x[i]+ enemy_width/2;
					enemy_missile_y[i] = enemy_y[i];
					if( enemy_alive[i] == 1 ){
						enemy_missile_flag[i] = 1;
					}
				}
				if(detect_hit_of_enemy_missile(i)==1){
                                        System.out.println("===Game End===");
                                        System.exit(0);
				}
			}
                 	if(my_x > dim.width-1) my_x=0;
			if(my_x < -player_width+1) my_x = dim.width - player_width;
			if(missile_flag == 1){
				my_missile_y -= 15;
				if(0 > my_missile_y){
					missile_flag = 0;
				} 
				for(int i=0; i < n;i++){
					if(detect_hit_of_my_missile(i)==1){
						enemy_alive[i]=0;
						missile_flag = 0;
						num_of_alive--;
						if (num_of_alive == 0){
							System.out.println("===Game Clear===");
							System.exit(0);
						}
					}
				}
			}

			repaint();
		}
		public void mouseClicked(MouseEvent me)
		{

		}
		public void mousePressed(MouseEvent me){
		
		}
		public void mouseReleased(MouseEvent me)
		{ }
		public void mouseExited(MouseEvent me)
		{ }
		public void mouseEntered(MouseEvent me)
		{ }
		public void mouseMoved(MouseEvent me){
		
		}
	    public void mouseDragged(MouseEvent me)
		{ }
	    public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch( key ){
		case KeyEvent.VK_RIGHT:
		    my_x = my_x +10;
		    break;
		case KeyEvent.VK_LEFT:
		    my_x = my_x -10;
		    break;
		case KeyEvent.VK_X:
		    if(missile_flag == 0){
			my_missile_x = my_x + player_width / 2;
			my_missile_y = MY_Y;//MY_Y=400
			missile_flag = 1;
		    }
		    break;
		}
	    }
	    public void keyReleased(KeyEvent e){}
	    public void keyTyped(KeyEvent e){}
	}
}
