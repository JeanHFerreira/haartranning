package principal;

public class Quadrado {
  public int x1, x2, y1, y2;
  
  public Quadrado(int x1, int x2,
           int y1, int y2){
	  
	int troca;
	if (x1 > x2){
	  troca = x2;
	  x2 = x1;
	  x1 = troca; 
	 }
	   
	if (y1 > y2){
	  troca = y2;
	  y2 = y1;
	  y1 = troca; 
	}
	  
    this.x1 = x1 ;
    this.x2 = x2 ;
    this.y1 = y1 ;
    this.y2 = y2 ;
  }
  
}
