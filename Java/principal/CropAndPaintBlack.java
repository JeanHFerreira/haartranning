package principal;

import gui.FrameToCrop;

public class CropAndPaintBlack {
  public static void main(String[] args) {
	String file="";
	int lote = 1;
	int numNeg = 100;
	System.out.println("*****************************************************************");  
	System.out.println("**  Argumentos:");
	System.out.println("**  -file nome do arquivo txt. Ex: Imagens.txt");
	System.out.println("**     obrigatório");
	System.out.println("**  -lote quantidade de lotes");
	System.out.println("**     valor default = 1");
	System.out.println("**     aplica-se para aplicar as mesmas ações em mais imagens");
	System.out.println("**  -numNeg quantidade de imagens negativas por imagem");
	System.out.println("**     valor default = 100");
	System.out.println("*****************************************************************"); 
	
	if (args.length>0){
	  for(int i = 0; i<args.length;i++){
		  if(i%2 == 1){
			  args[i].replace("-file", "");
			  args[i].replace("-lote", "");
			  args[i].replace("-numNeg", "");
		  }
		  if (args[i].equals("-file")){
			  file = (i+1>=args.length)?"INVÁLIDO":args[i+1];
		  }
		  if (args[i].equals("-lote")){
			  try{
			    lote = (i+1>=args.length)?-1:Integer.parseInt(args[i+1]);
			  } catch (NumberFormatException ex){
				lote = -1;
			  }
		  }
		  if (args[i].equals("-numNeg")){
			  try{
				  numNeg = (i+1>=args.length)?-1:Integer.parseInt(args[i+1]);
			  } catch (NumberFormatException ex){
				  numNeg = -1;
			  }
		  }
	  }
	  System.out.println("Arquivo: "+file);
	  System.out.println("Quantidade de lotes: "+lote);
	  if (!file.equals("INVÁLIDO") && lote >= 1 && numNeg >= 0){
	    FrameToCrop frame = new FrameToCrop(file,lote,numNeg);
	    frame.setVisible(true);
	  }else{
		 System.out.println("A aplicação será encerrada pois há argumentos inválidos");
	  }
	} else{
	  System.out.println("Argumento 0 (Local txt) necesário");
	}
  }
}
