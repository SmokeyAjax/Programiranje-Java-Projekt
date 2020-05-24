import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) 
	{	
		// ostvarimo okno za prikazovanje in naredimo objekt gameSesion
		JFrame frame = new JFrame();
		GameSesion gameSesion = new GameSesion();
		
		// Nastavimo preference za igralno okno
		frame.setSize(new Dimension(760, 780));
		frame.setTitle("Tanks");	
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Povemo kaj se prikazuje, ter nastavimo, da se prikazuje
		frame.add(gameSesion);
		frame.setVisible(true);
	}
}