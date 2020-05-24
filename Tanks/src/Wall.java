import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Wall{
	// Koordinate unièljivih zidov
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
	
	//koordinate neunièljivih zidov
	int hardWallCoordinatesX[] = {50, 50, 300, 300, 350, 350, 350, 350, 400, 400, 650, 650, 350, 350};
	int hardWallCoordinatesY[] = {50, 650, 300, 350, 300, 350, 250, 400, 350, 300, 50, 650, 50, 650};
	// seznam uniæljivih zidov, 1 - še stoji, 0, unièen
	int wallsToPlace[] = new int[60];
	
	// Definiramo spremenljivke tipa slik
	private ImageIcon breakBrickImage;
	private ImageIcon solidBrickImage;
	
	public Wall()
	{
		// Doloèimo slièice
		breakBrickImage=new ImageIcon("Wall.png");
		solidBrickImage=new ImageIcon("HardWall.png");	
		// Nazaèetku vsi zidovi še stojimo zato naštimamo vse na 1
		for(int i=0; i < wallCoordinatesX.length; i++)
		{
			wallsToPlace[i] = 1;
		}
	}
	
	// izriše vse zidove
	public void drawWall(Component c, Graphics g)
	{
		for(int i=0; i< wallCoordinatesX.length;i++)
		{			if(wallsToPlace[i]==1)
			{
					breakBrickImage.paintIcon(c, g, wallCoordinatesX[i],wallCoordinatesY[i]);
			}
		}
	}
	
	// še neunièljive
	public void drawHardWall(Component c, Graphics g)
	{
		for(int i=0; i< hardWallCoordinatesX.length;i++)
		{			
			solidBrickImage.paintIcon(c, g, hardWallCoordinatesX[i],hardWallCoordinatesY[i]);
		}
	}
	
	// Èekira dotik z drugimi objekti
	// èe se zazna dotik pri unièljivih zidovih, ga izbrišemo
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
					// "izbrišemo zid"
					wallsToPlace[i] = 0;
					collided = true;
					break;
				}
			}
		}
		
		return collided;
	}
	// gledamo dotike še za neunièljive zidove
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