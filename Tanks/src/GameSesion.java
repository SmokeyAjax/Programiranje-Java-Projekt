import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

// Nov razred,ki deduje vse lastnosti Jpanel, ter implementiramo še ActionListener
public class GameSesion  extends JPanel implements ActionListener 
{
	// Povemo serial version (da ni errorja)
	private static final long serialVersionUID = 1L;

	// Definiramo spremenljivke
	private Wall wall;
	
	// Definiramo spremenjljivke za igralca 1
	// Slikca (sprite)
	private ImageIcon player1;	
	// koordinate igralca
	private int player1X = 0;
	private int player1Y = 0;	
	// V katero smer je ubrnjen
	private boolean player1right = false;
	private boolean player1left = false;
	private boolean player1down = true;
	private boolean player1up = false;	
	// Ali se igralec dotika zidu
	private boolean player1Collision = false;
	// Število živlenj
	private int player1lives = 1;
	// Ali igralec strela
	private boolean player1Shoot = false;
	// smer metka
	private String projectiletDirection1 = "";
	
	// Definiramo enake spremenljivke še za igralca 2
	private ImageIcon player2;	
	private int player2X = 700;
	private int player2Y = 700;	
	private boolean player2right = false;
	private boolean player2left = false;
	private boolean player2down = false;
	private boolean player2up = true;
	private boolean player2Collision = false;

	private int player2lives = 1;
	private boolean player2Shoot = false;
	private String projectiletDirection2 = "";
	
	// Uravnavamo kako hitro poteka celoten program
	private Timer timer;
	private int delay=5;
	
	// Za kontroleranje igralcev
	private Player1Listener player1Listener;
	private Player2Listener player2Listener;
	
	// Definiramo objekte za metke
	private Player1Projectile projectile1 = null;
	private Player2Projectile projectile2 = null;
	
	// Ali je igra v poteku
	private boolean running = true;
	
	public GameSesion()
	{	
		// Spremenljivkam damo vrednosti
		wall = new Wall();
		player1Listener = new Player1Listener();
		player2Listener = new Player2Listener();
		setFocusable(true);

		addKeyListener(player1Listener);
		addKeyListener(player2Listener);
		setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{    		
		// Odzadje
		g.setColor(Color.black);
		g.fillRect(0, 0, 760, 780);

		// Unièljive zidove
		wall.drawWall(this, g);
		
		// Neuniæljivi zidovi	
		wall.drawHardWall(this, g);
		
		// Do zdej smo izrisal mapo, sedaj se pa zaène igra
		if (running)
		{
			// Dobimo slièico in izrišemo doloèeno, glede na to v katero smer je ubrnjen igralec
			if(player1up)
				player1 = new ImageIcon("BlueTankUp.png");	
			else if(player1down)
				player1 = new ImageIcon("BlueTankDown.png");
			else if(player1right)
				player1 = new ImageIcon("BlueTankRight.png");
			else if(player1left)
				player1 = new ImageIcon("BlueTankLeft.png");
			// Izrisuje pravo slièico na doloèenih koordinatah
			player1.paintIcon(this, g, player1X, player1Y);
			
			// Gledamo èe se igralc dotika zdiov, èe se ga premaknemo nazaj, tako da vizoalno ostane na mestu
			if (wall.checkWallCollision(player1X, player1Y, false) ||
					wall.checkHardWallCollision(player1X, player1Y, false))
				{
					player1Collision = true;
					
					if (player1up)
					{
						player1Y += 10;
					}
					else if (player1down)
					{
						player1Y -= 10;
					}
					else if (player1left)
					{
						player1X += 10;
					}
					else if (player1right)
					{
						player1X -= 10;
					}
				}
				else
				{
					player1Collision = false;
				}
			
			// enako kot za igralca 1 samo, da še za drugega
			if(player2up)
				player2 = new ImageIcon("GreenTankUp.png");	
			else if(player2down)
				player2 = new ImageIcon("GreenTankDown.png");
			else if(player2right)
				player2  =new ImageIcon("GreenTankRight.png");
			else if(player2left)
				player2 = new ImageIcon("GreenTankLeft.png");
						
			player2.paintIcon(this, g, player2X, player2Y);
			
			if (wall.checkWallCollision(player2X, player2Y, false) ||
				wall.checkHardWallCollision(player2X, player2Y, false))
			{
				player2Collision = true;
				
				if (player2up)
				{
					player2Y += 10;
				}
				else if (player2down)
				{
					player2Y -= 10;
				}
				else if (player2left)
				{
					player2X += 10;
				}
				else if (player2right)
				{
					player2X -= 10;
				}
			}
			else
			{
				player2Collision = false;
			}
			
			
			// Èe igralec ustreli in v igri ni njegovega metka
			if(projectile1 != null && player1Shoot)
			{
				// Naštimamo smer v katero bo letel metek
				if(projectiletDirection1.equals(""))
				{
					if(player1up)
					{					
						projectiletDirection1 = "up";
					}
					else if(player1down)
					{					
						projectiletDirection1 = "down";
					}
					else if(player1right)
					{				
						projectiletDirection1 = "right";
					}
					else if(player1left)
					{			
						projectiletDirection1 = "left";
					}
				}
				else
				{
					// Klièemo metodo, da premika metek, ter ga izrisujemo
					projectile1.move(projectiletDirection1);
					projectile1.draw(g);
				}
				// Èe se metek dotakne drugega igralca, ga unièi, ter se vrnemo, ker je konec igre
				if (new Rectangle(projectile1.getX(), projectile1.getY(), 10, 10).intersects(new Rectangle(player2X, player2Y, 50, 50)))
				{
					player2lives = 0;
					player2Shoot = false;
					projectiletDirection2 = "";
					return;
				}
				
				// Èe se metek zaleti v steno ali gre izven okvirja ga unièimo
				if (projectile1.getY() < 1 ||
					projectile1.getY() > 740 ||
					projectile1.getX() < 1 ||
					projectile1.getX() > 740 ||
					wall.checkWallCollision(projectile1.getX(), projectile1.getY(), true) ||
					wall.checkHardWallCollision(projectile1.getX(), projectile1.getY(), true))	
					{
						projectile1 = null;
						player1Shoot = false;
						projectiletDirection1 = "";
					}			
			}
			
			// Enako kot smo imeli pri prvem igralcu
			if(projectile2 != null && player2Shoot)
			{
				if(projectiletDirection2.equals(""))
				{
					if(player2up)
					{					
						projectiletDirection2 = "up";
					}
					else if(player2down)
					{					
						projectiletDirection2 = "down";
					}
					else if(player2right)
					{				
						projectiletDirection2 = "right";
					}
					else if(player2left)
					{			
						projectiletDirection2 = "left";
					}
				}
				else
				{
					projectile2.move(projectiletDirection2);
					projectile2.draw(g);
				}
				
				if (new Rectangle(projectile2.getX(), projectile2.getY(), 10, 10).intersects(new Rectangle(player1X, player1Y, 50, 50)))
				{
					player1lives = 0;
					player1Shoot = false;
					projectiletDirection1 = "";
					return;
				}
				
				if (projectile2.getY() < 1 ||
					projectile2.getY() > 740 ||
					projectile2.getX() < 1 ||
					projectile2.getX() > 740 ||
					wall.checkWallCollision(projectile2.getX(), projectile2.getY(), true) ||
					wall.checkHardWallCollision(projectile2.getX(), projectile2.getY(), true))	
				{
					projectile2 = null;
					player2Shoot = false;
					projectiletDirection2 = "";
				}	
			}
		}
		
		// Izpišemo zmagovalni zaslon za zelenega igralca
		if(player1lives == 0)
		{
			g.setColor(Color.green);
			g.setFont(new Font("serif",Font.BOLD, 50));
			g.drawString("Game Over", 250,250);
			g.drawString("GREEN WON", 210,400);
			g.setColor(Color.green);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("(Enter to Restart)", 260,500);
			running = false;
		}
		// Izpišemo zmagovalni zaslon za modrega igralca
		else if(player2lives == 0)
		{
			g.setColor(Color.blue);
			g.setFont(new Font("serif",Font.BOLD, 50));
			g.drawString("Game Over", 250,250);
			g.drawString("BLUE WON", 230,400);
			g.setColor(Color.blue);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("(Enter to Restart)", 260,500);
			running = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		repaint();
	}

	// Obrovnava vnose igralca 1
	private class Player1Listener implements KeyListener
	{
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}		
		public void keyPressed(KeyEvent e) 
		{
			// Restartamo igro	
			if (e.getKeyCode()== KeyEvent.VK_ENTER)
			{
				wall = new Wall();
				player1X = 0;
				player1Y = 0;	
				player1right = false;
				player1left = false;
				player1down = true;
				player1up = false;	
						
				player2X = 700;
				player2Y = 700;	
				player2right = false;
				player2left = false;
				player2down = false;
				player2up = true;	
						
				player1lives = 1;
				player2lives = 1;
				running = true;
				repaint();
			}
				
			// Premika metek (spreminja kooridinate metka)
			if(e.getKeyCode()== KeyEvent.VK_SPACE)
			{
				if(!player1Shoot)
				{
					if(player1up)
					{					
						projectile1 = new Player1Projectile(player1X + 20, player1Y);
					}
					else if(player1down)
					{					
						projectile1 = new Player1Projectile(player1X + 20, player1Y + 40);
					}
					else if(player1right)
					{				
						projectile1 = new Player1Projectile(player1X + 40, player1Y + 20);
					}
					else if(player1left)
					{			
						projectile1 = new Player1Projectile(player1X, player1Y + 20);
					}
						
					player1Shoot = true;
				}
			}
			
			// Premikamo in obraèamo igralca v doloèeno smer, ko drži doloèeno tipko
			if(e.getKeyCode()== KeyEvent.VK_W && !player1Collision)
			{
				player1right = false;
				player1left = false;
				player1down = false; 
				player1up = true;		
				
				// Ko je na robu okvirja, da ne gre izven zaslona
				if(!(player1Y < 10))
					player1Y-=10;
				}
				
				if(e.getKeyCode()== KeyEvent.VK_A && !player1Collision)
				{
					player1right = false;
					player1left = true;
					player1down = false;
					player1up = false;
					
					if(!(player1X < 10))
						player1X-=10;
				}
				
				if(e.getKeyCode()== KeyEvent.VK_S && !player1Collision)
				{
					player1right = false;
					player1left = false;
					player1down = true;
					player1up = false;
					
					if(!(player1Y > 690))
						player1Y+=10;
				}
				
				if(e.getKeyCode()== KeyEvent.VK_D && !player1Collision)
				{
					player1right = true;
					player1left = false;
					player1down = false;
					player1up = false;
					
					if(!(player1X > 690))
						player1X+=10;
				}
			}
		}
	
	private class Player2Listener implements KeyListener
	{
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}		
		public void keyPressed(KeyEvent e)
		{	
			if(e.getKeyCode()== KeyEvent.VK_NUMPAD0)
			{
				if(!player2Shoot)
				{
					if(player2up)
					{					
						projectile2 = new Player2Projectile(player2X + 20, player2Y);
					}
					else if(player2down)
					{					
						projectile2 = new Player2Projectile(player2X + 20, player2Y + 40);
					}
					else if(player2right)
					{				
						projectile2 = new Player2Projectile(player2X + 40, player2Y + 20);
					}
					else if(player2left)
					{			
						projectile2 = new Player2Projectile(player2X, player2Y + 20);
					}
					
					player2Shoot = true;
				}
			}
			if(e.getKeyCode()== KeyEvent.VK_UP && !player2Collision)
			{
				player2right = false;
				player2left = false;
				player2down = false;
				player2up = true;		
				
				if(!(player2Y < 10))
					player2Y-=10;
			}
			
			if(e.getKeyCode()== KeyEvent.VK_LEFT && !player2Collision)
			{
				player2right = false;
				player2left = true;
				player2down = false;
				player2up = false;
				
				if(!(player2X < 10))
					player2X-=10;
			}
				
			if(e.getKeyCode()== KeyEvent.VK_DOWN && !player2Collision)
			{
				player2right = false;
				player2left = false;
				player2down = true;
				player2up = false;
					
				if(!(player2Y > 690))
					player2Y+=10;
			}
			
			if(e.getKeyCode()== KeyEvent.VK_RIGHT && !player2Collision)
			{
				player2right = true;
				player2left = false;
				player2down = false;
				player2up = false;
				
				if(!(player2X > 690))
					player2X+=10;
			}
				
		}
	}
}