package principal;

import java.util.ArrayList;

public class Evento {
   private ArrayList<Quadrado> listaPositivos;
   private ArrayList<Quadrado> listaNegativos;
   
   public Evento(){
     this.listaPositivos = new ArrayList<Quadrado>();
     this.listaNegativos = new ArrayList<Quadrado>();
   }
   
   public ArrayList<Quadrado> getListaPositivos(){
	   return this.listaPositivos;
   }
   
   public ArrayList<Quadrado> getListaNegativos(){
	   return this.listaNegativos;
   }
}
