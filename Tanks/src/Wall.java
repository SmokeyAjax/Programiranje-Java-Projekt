import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Wall{
	// Koordinate uni�ljivih zidov
	int wallCoordinatesX[] = {
			100, 100, 100, 100, 100, 100, 100, 100,
			150, 150, 150, 150, 150, 150, 150, 150,
			200, 200, 200, 200, 200, 200, 200, 200,
			500, 500, 500, 500, 500, 500, 500, 500,
			550, 550, 550, 550, 550, 550, 550, 550,
			600, 600, 600, 600, 600, 600, 600, 600,
			250, 300, 350, 400, 450, 500,
			250, 300, 350, 400, 450, 500
			};
	
	int wallCoordinatesY[] = {
			100, 150, 200, 300, 400, 500, 550, 600,
			100, 150, 200, 300, 400, 500, 550, 600,
			100, 150, 200, 300, 400, 500, 550, 600,
			100, 150, 200, 300, 400, 500, 550, 600,
			100, 150, 200, 300, 400, 500, 550, 600,
			100, 150, 200, 300, 400, 500, 550, 600,
			150, 150, 150, 150, 150, 150,
			550, 550, 550, 550, 550, 550
			};
	
	//koordinate neuni�ljivih zidov
	int hardWallCoordinatesX[] = {50, 50, 300, 300, 350, 350, 350, 350, 400, 400, 650, 650, 350, 350};
	int hardWallCoordinatesY[] = {50, 650, 300, 350, 300, 350, 250, 400, 350, 300, 50, 650, 50, 650};
	// seznam uni�ljivih zidov, 1 - �e stoji, 0, uni�en
	int wallsToPlace[] = new int[60];
	
	// Definiramo spremenljivke tipa slik
	private ImageIcon breakBrickImage;
	private ImageIcon solidBrickImage;
	
	public Wall()
	{
		// Dolo�imo sli�ice
		breakBrickImage=new ImageIcon("Wall.png");
		solidBrickImage=new ImageIcon("HardWall.png");	
		// Naza�etku vsi zidovi �e stojimo zato na�timamo vse na 1
		for(int i=0; i < wallCoordinatesX.length; i++)
		{
			wallsToPlace[i] = 1;
		}
	}
	
	// izri�e vse zidove
	public void drawWall(Component c, Graphics g)
	{
		for(int i=0; i< wallCoordinatesX.length;i++)
		{			if(wallsToPlace[i]==1)
			{
					breakBrickImage.paintIcon(c, g, wallCoordinatesX[i],wallCoordinatesY[i]);
			}
		}
	}
	
	// �e neuni�ljive
	public void drawHardWall(Component c, Graphics g)
	{
		for(int i=0; i< hardWallCoordinatesX.length;i++)
		{			
			solidBrickImage.paintIcon(c, g, hardWallCoordinatesX[i],hardWallCoordinatesY[i]);
		}
	}
	
	// �ekira dotik z drugimi objekti
	// �e se zazna dotik pri uni�ljivih zidovih, ga izbri�emo
	public boolean checkWallCollision(int x, int y, boolean bullet)
	{
		boolean collided = false;
		int collisionSize;
		
		if (bullet)
		{
			collisionSize = 10;
		}
		else
		{
			collisionSize = 50;
		}
		
		for(int i=0; i< wallCoordinatesX.length; i++)
		{
			if(wallsToPlace[i]==1)
			{
				if(new Rectangle(x, y, collisionSize, collisionSize).intersects(new Rectangle(wallCoordinatesX[i], wallCoordinatesY[i], 50, 50)))
				{
					// "izbri�emo zid"
					wallsToPlace[i] = 0;
					collided = true;
					break;
				}
			}
		}
		
		return collided;
	}
	// gledamo dotike �e za neuni�ljive zidove
	public boolean checkHardWallCollision(int x, int y, boolean bullet)
	{
		boolean collided = false;
		int collisionSize;
		
		if (bullet)
		{
			collisionSize = 10;
		}
		else
		{
			collisionSize = 50;
		}
		
		for(int i=0; i< hardWallCoordinatesX.length; i++)
		{		
			if(new Rectangle(x, y, collisionSize, collisionSize).intersects(new Rectangle(hardWallCoordinatesX[i], hardWallCoordinatesY[i], 50, 50)))
			{		
				collided = true;
				break;
			}			
		}
		
		return collided;
	}
}