import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;


public class Player1Projectile {
	private double x, y;
	
	public Player1Projectile(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	// oravnavamo smer metka
	public void move(String face)
	{
		if(face.equals("right"))
			x += 5;
		else if(face.equals("left"))
			x -= 5;
		else if(face.equals("up"))
			y -= 5;
		else
			y +=5;
	}
	
	// izri�emo metek
	public void draw(Graphics g)
	{
		g.setColor(Color.blue);
		g.fillOval((int) x, (int) y, 10, 10);
	}
	
	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}

}