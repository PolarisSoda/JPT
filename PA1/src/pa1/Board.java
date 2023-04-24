package pa1;
import java.util.Scanner;

public class Board implements Game {
	Player player;
	Ghost ghost;
	Key key;
	Door door;
	char[][] board;
	
	public Board() {		
		/* add your code, you can add parameter, too */
		board = new char[][] { 
				{' ','X',' ',' ',' ',' ','X',' ','X',' ',' ',' ',' ',' ',' '}, 
				{' ','X',' ','X','X','X','X',' ','X',' ','X',' ','X','X',' '}, 
				{' ','X',' ',' ',' ','X',' ',' ','X',' ','X',' ',' ','X',' '}, 
				{' ','X',' ','X',' ','X',' ','X','X',' ','X','X',' ','X',' '}, 
				{' ',' ',' ','X',' ','X',' ',' ',' ',' ',' ',' ',' ',' ',' '}, 
				{' ','X','X','X',' ',' ',' ','X','X','X','X','X','X','X',' '}, 
				{' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' ',' ',' ','X',' '}, 
				{'X','X',' ','X','X','X',' ','X','X','X','X','X',' ','X',' '}, 
				{' ',' ',' ',' ',' ',' ',' ','X',' ',' ',' ',' ',' ','X',' '}, 
				{' ','X','X',' ','X','X',' ','X',' ','X','X','X',' ','X',' '}, 
				{' ','X',' ',' ',' ',' ',' ','X',' ',' ',' ','X',' ','X',' '}, 
				{' ','X',' ','X','X','X','X','X','X','X','X','X',' ','X',' '}, 
				{' ','X',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X',' '}, 
				{' ','X','X','X',' ','X','X','X','X','X','X','X',' ','X',' '}, 
				{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '} 
		};
		//board[j][i]에서 j가 세로 i가 가로다.
	}
	
	boolean Approx(int x,int y) {
		if(x>= 0 && x <= 14 && y>=0 && y<=14) {
			return true;
		} else {
			return false;
		}
	}
	
	public void printBoard() {
		char[][] temp = new char[15][15];
		for(int i=0; i<=14; i++) {
			for(int j=0; j<=14; j++) {
				temp[i][j] = board[i][j];
			}
		}
		
		temp[this.ghost.getY()][this.ghost.getX()] = 'G';
		if(this.key.getY() != -1) {
			temp[this.key.getY()][this.key.getX()] = 'K';
		}
		temp[this.door.getY()][this.door.getX()] = 'D';
		temp[this.player.getY()][this.player.getX()] = 'P';
		for(int i=0; i<=14; i++) {
			for(int j=0; j<=14; j++) {
				if(temp[i][j] == 'X' || temp[i][j] == '■') {
					System.out.print("■ ");
				} else if(temp[i][j] == 'F') {
					System.out.print("  ");
				} else {
					System.out.print(temp[i][j] + " ");
				}
				
			}
			System.out.println("");
		}
	}
		 
	public void initObjects() {
		Scanner in = new Scanner(System.in);
		int[] sx = {-1,-1,-1};
		int[] sy = {-1,-1,-1};
		int cnt = 0;
		
		//-----------플레이어를 정합니다.
		while(true) {
			System.out.println("input player x,y (0~14) :");
			int x = in.nextInt();
			int y = in.nextInt();
			if(Approx(x,y) && this.board[y][x] == ' ') {
				this.player = new Player(x,y);
				sx[cnt] = x;
				sy[cnt] = y;
				cnt++;
				break;
			}
		}
		//-------기신을 정합니다.
		while(true) {
			System.out.println("input ghost x,y (0~14) :");
			int x = in.nextInt();
			int y = in.nextInt();
			if(Approx(x,y)) {
				boolean dup = false;
				for(int i=0; i<cnt; i++) {
					if(x == sx[i] && y == sy[i]) {
						dup = true;
						break;
					}
				}
				if(dup == true) {
					continue;
				}
				this.ghost = new Ghost(x,y);
				sx[cnt] = x;
				sy[cnt] = y;
				cnt++;
				break;
			}
		}
		//----열쇠
		while(true) {
			System.out.println("input key x,y (0~14) :");
			int x = in.nextInt();
			int y = in.nextInt();
			if(Approx(x,y) && this.board[y][x] == ' ') {
				boolean dup = false;
				for(int i=0; i<cnt; i++) {
					if(x == sx[i] && y == sy[i]) {
						dup = true;
						break;
					}
				}
				if(dup == true) {
					continue;
				}
				this.key = new Key(x,y);
				sx[cnt] = x;
				sy[cnt] = y;
				cnt++;
				break;
			}
		}
		//-----문
		while(true) {
			System.out.println("input door x,y (0~14) :");
			int x = in.nextInt();
			int y = in.nextInt();
			if(Approx(x,y) && this.board[y][x] == ' ') {
				boolean dup = false;
				for(int i=0; i<cnt; i++) {
					if(x == sx[i] && y == sy[i]) {
						dup = true;
						break;
					}
				}
				if(dup == true) {
					continue;
				}
				this.door = new Door(x,y);
				break;
			}
		}
	}
	
	public void movePlayer() {
		int[] dx = {1,0,-1,0};
		int[] dy = {0,1,0,-1};
		Scanner in = new Scanner(System.in);
		char kb = in.next().charAt(0);
		int bias = -1;
		if(kb == 'd') {
			bias = 1;
		} else if(kb == 'u') {
			bias = 3;
		} else if(kb == 'l') {
			bias = 2;
		} else if(kb == 'r') {
			bias = 0;
		} else {
			return;
		}
		int px = this.player.getX();
		int py = this.player.getY();
		int nx = px+dx[bias];
		int ny = py+dy[bias];
		if(Approx(nx,ny) && this.board[ny][nx] != 'X') {
			this.player.move(nx,ny);
		}
		px = this.player.getX();
		py = this.player.getY();
		int kx = this.key.getX();
		int ky = this.key.getY();
		if(kx == px && ky == py) {
			this.key.setX(-1);
			this.key.setY(-1);
		}
	}	
	
	public void moveGhost() {
		int px = this.player.getX();
		int py = this.player.getY();
		this.ghost.move(px,py);
		if(this.isFinish(true)) {
			return;
		}
		this.printBoard();
		this.movePlayer();
	}
	public boolean isFinish(boolean inside) {
		int px = this.player.getX();
		int py = this.player.getY();
		int gx = this.ghost.getX();
		int gy = this.ghost.getY();
		int dx = this.door.getX();
		int dy = this.door.getY();
		int kx = this.key.getX();
		if(kx == -1 && px == dx && py == dy) {
			return true;
		} else if(gx == px && gy == py) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isFinish() {
		int px = this.player.getX();
		int py = this.player.getY();
		int gx = this.ghost.getX();
		int gy = this.ghost.getY();
		int dx = this.door.getX();
		int dy = this.door.getY();
		int kx = this.key.getX();
		if(kx == -1 && px == dx && py == dy) {
			System.out.println("YOU WIN");
			return true;
		} else if(gx == px && gy == py) {
			System.out.println("YOU LOSE");
			return true;
		} else {
			return false;
		}
	}
}
