package pa1;

public class Player extends GameObject{
	
	public Player(int x,int y) {
		/* add your code, you can add parameter, too */
		this.setX(x);
		this.setY(y);
	}	
	public void move(int x,int y) {
		this.setX(x);
		this.setY(y);
	}
}
