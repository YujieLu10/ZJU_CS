package myGobang;

public class Ruler {

	private int[][] qizi;
	
	public Ruler(int[][] qizi){
		this.qizi=qizi;
	}
	
	/**
	 * 1 ºÚÆåÊ¤
	 * 2 °×ÆåÊ¤
	 * @return
	 */
	public int isOver(){
		for(int x=0;x<15;x++){
			for(int y=0;y<15;y++){
				
				//ºá
				if(x+4<=14&&qizi[x][y]==1&&qizi[x+1][y]==1&&qizi[x+2][y]==1&&qizi[x+3][y]==1&&qizi[x+4][y]==1){
					return 1;
				}
				if(x+4<=14&&qizi[x][y]==2&&qizi[x+1][y]==2&&qizi[x+2][y]==2&&qizi[x+3][y]==2&&qizi[x+4][y]==2){
					return 2;
				}
				
				//Êú
				if(y+4<=14&&qizi[x][y]==1&&qizi[x][y+1]==1&&qizi[x][y+2]==1&&qizi[x][y+3]==1&&qizi[x][y+4]==1){
					return 1;
				}
				
				if(y+4<=14&&qizi[x][y]==2&&qizi[x][y+1]==2&&qizi[x][y+2]==2&&qizi[x][y+3]==2&&qizi[x][y+4]==2){
					return 2;
				}
				//×óÏÂÐ±
				if(x-4>=0&&y+4<=14&&qizi[x][y]==1&&qizi[x-1][y+1]==1&&qizi[x-2][y+2]==1&&qizi[x-3][y+3]==1&&qizi[x-4][y+4]==1){
					return 1;
				}
				if(x-4>=0&&y+4<=14&&qizi[x][y]==2&&qizi[x-1][y+1]==2&&qizi[x-2][y+2]==2&&qizi[x-3][y+3]==2&&qizi[x-4][y+4]==2){
					return 2;
				}
				
				//ÓÒÏÂÐ±
				if(x+4<=14&&y+4<=14&&qizi[x][y]==1&&qizi[x+1][y+1]==1&&qizi[x+2][y+2]==1&&qizi[x+3][y+3]==1&&qizi[x+4][y+4]==1){
					return 1;
				}
				if(x+4<=14&&y+4<=14&&qizi[x][y]==2&&qizi[x+1][y+1]==2&&qizi[x+2][y+2]==2&&qizi[x+3][y+3]==2&&qizi[x+4][y+4]==2){
					return 2;
				}
			}
		}
			
		
		return 0;
	}
	
	public int calScore(int x,int y,int color){
		
		return 0;
	}

	public int[][] getQizi() {
		return qizi;
	}

	public void setQizi(int[][] qizi) {
		this.qizi = qizi;
	}
	
}