package pa1;

public class Ghost extends GameObject{
	
	public Ghost(int x,int y) {
		/* add your code, you can add parameter, too */
		this.setX(x);
		this.setY(y);
	}	
	
	boolean Approx(int x,int y) {
		if(x>= 0 && x <= 14 && y>=0 && y<=14) {
			return true;
		} else {
			return false;
		}
	}
	
	public void move(int px,int py) {
		int gx = this.getX();
		int gy = this.getY();
		if(px < gx) {
			gx--;
		} else if(px > gx) {
			gx++;
		}
		if(py < gy) {
			gy--;
		} else if(py > gy) {
			gy++;
		}
		this.setX(gx);
		this.setY(gy);
	}
	
}
