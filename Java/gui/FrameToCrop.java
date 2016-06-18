package gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import principal.Evento;
import principal.Quadrado;

public class FrameToCrop extends JFrame{

	private static final long serialVersionUID = 1L;
	
	ArrayList<String> listaArquivos,arquivoPositivas,arquivoNegativas;
	ArrayList<Evento> eventos;
	Evento evento;
	int indiceArquivo,quantidadeLote,x1,x2,y1,y2,qtdPositiva,qtdNegativa,numNeg,quantidadeRepeticao;
	JButton btProxImagem, btAddPositiva, btPaint,btDesfazer;
	JLabel lbImagem, lbImagemAux;
	PainelButtons painelButton;
	ImageIcon logo;
	BufferedImage imagem, imagemAuxiliar;
	
	public FrameToCrop (String nomeArquivo, int quantidadeLote, int numNeg){
		this.x1 = this.x2 = this.y1 = this.y2 = 0;
		this.arquivoPositivas = new ArrayList<String>();
		this.arquivoNegativas = new ArrayList<String>();
		this.listaArquivos = new ArrayList<String>();
		this.quantidadeLote = quantidadeLote;
		this.numNeg = numNeg;
		this.indiceArquivo = 0;
		this.qtdPositiva = 0;
		this.qtdNegativa = 0;
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(480,300);
		this.btProxImagem = new JButton("Proxima imagem");
		this.btAddPositiva = new JButton("Imagem positiva");
		this.btPaint = new JButton("Pintar");
		this.btDesfazer = new JButton("Desfazer último");
		this.lbImagem = new JLabel("");
		this.lbImagemAux = new JLabel("");
		this.setLayout(new BorderLayout());
		this.painelButton = new PainelButtons();
		this.painelButton.add(this.btProxImagem);
		this.painelButton.add(this.btAddPositiva);
		this.painelButton.add(this.btPaint);
		//this.painelButton.add(this.btDesfazer);
		this.add(BorderLayout.CENTER,this.painelButton);
		this.add(BorderLayout.NORTH,this.lbImagem);
		this.add(BorderLayout.SOUTH,this.lbImagemAux);
		this.btProxImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clickBtProximo();
            }
        });
		
		this.btAddPositiva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clickBtAddPositiva();
            }
        });
		
		this.btPaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clickBtPaint();
            }
        });
		
		this.btDesfazer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	clickBtDesfazer();
            }
        });
		
		lbImagem.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
            	lbImagemMouseClicked(evt);
            }
        });
		
		Scanner ler = new Scanner(nomeArquivo);
	    String nome = ler.nextLine();
	    try {
	      FileReader arq = new FileReader(nome);
	      BufferedReader lerArq = new BufferedReader(arq);
	      String linha = lerArq.readLine(); 
	      while (linha != null) {
	        this.listaArquivos.add(linha);
	        linha = lerArq.readLine();
	      }
	      System.out.println(this.listaArquivos.size() + " arquivos foram obtidos");
	      arq.close();
	      if (this.listaArquivos.size()%quantidadeLote!=0){
	        System.err.println("A quantidade do lote não corresponde a quantidade de arquivos");
	        System.out.println("O programa será finalizado");
	        System.exit(0);
	      }else{
	        this.quantidadeRepeticao = this.listaArquivos.size()/quantidadeLote;
	        this.eventos = new ArrayList<Evento>(this.quantidadeRepeticao);
	        this.abrirImagem();
	      }
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo TXT: %s.\n",
	          e.getMessage());
	        System.out.println("O programa será finalizado");
	        System.exit(0);
	    }
	}
	
	public void gravarArquivos(){
		FileWriter gravador;
		PrintWriter printer;
		try {
			gravador = new FileWriter("./positivas.txt");
			printer = new PrintWriter(gravador);
			for(int i=0; i<this.arquivoPositivas.size(); i++){
				printer.printf("%s%n",this.arquivoPositivas.get(i));
			}
			gravador.close();
			System.out.println("Arquivo positivas.txt gerado com sucesso");
			gravador = new FileWriter("./negativas.txt");
			printer = new PrintWriter(gravador);
			for(int i=0; i<this.arquivoNegativas.size(); i++){
				printer.printf("%s%n",this.arquivoNegativas.get(i));
			}
			gravador.close();
			System.out.println("Arquivo negativas.txt gerado com sucesso");
		} catch (IOException e) {
			System.err.printf("Erro ao gravar os arquivos de imagens\nErro:%s.\n",
	          e.getMessage());
			System.out.println("O programa será finalizado");
	        System.exit(0);
		}
		
		
	}
	
	public void realizarAcoesLote(){
		this.setVisible(false);
		System.out.println("Realizando ações do lote");
		this.x1 = this.x2 = this.y1 = this.y2 = 0;
		for (int l = 1; l<this.quantidadeLote; l++){
			for (int i = 0; i<this.eventos.size();i++){
				this.abrirImagem();
				for (int j = 0; j<this.eventos.get(i).getListaPositivos().size();j++){
					this.x1 = this.eventos.get(i).getListaPositivos().get(j).x1;
				    this.x2 = this.eventos.get(i).getListaPositivos().get(j).x2;
				    this.y1 = this.eventos.get(i).getListaPositivos().get(j).y1;
				    this.y2 = this.eventos.get(i).getListaPositivos().get(j).y2;
				    this.cropAndSave();
				    this.paintBlack();
				}
				for (int j = 0; j<this.eventos.get(i).getListaNegativos().size();j++){
					this.x1 = this.eventos.get(i).getListaNegativos().get(j).x1;
				    this.x2 = this.eventos.get(i).getListaNegativos().get(j).x2;
				    this.y1 = this.eventos.get(i).getListaNegativos().get(j).y1;
				    this.y2 = this.eventos.get(i).getListaNegativos().get(j).y2;
				    this.paintBlack();
				}
				this.cropNegatives();
			}
		}
	}
	
	private void clickBtProximo(){
		this.eventos.add(this.evento);
		this.cropNegatives();
		if (this.quantidadeLote>1 && (this.listaArquivos.size())%this.quantidadeRepeticao == 0){
			this.realizarAcoesLote();
			this.gravarArquivos();
			System.out.println("O programa será finalizado");
			this.dispose();
			return;
		} else{
			this.abrirImagem();
		}
	}
	
	public void cropAndSave(){
		this.corrigirVariaveis();
		try {
			this.qtdPositiva++;
			BufferedImage imagemPositiva = this.imagem.getSubimage(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
			ImageIO.write(imagemPositiva, "bmp", new File("./Positivas/P"+this.qtdPositiva+".bmp"));
			this.arquivoPositivas.add("Positivas/P"+this.qtdPositiva+".bmp "+
			                         "1 0 0 "+(imagemPositiva.getWidth()-1)+" "+ (imagemPositiva.getHeight()-1));
			System.out.println("Imagem P"+this.qtdPositiva+".bmp salva com sucesso");
		} catch (IOException e) {
			System.out.println("Erro ao salvar a imagem P"+this.indiceArquivo+".bmp");
		}
	}
	
	public void cropNegatives(){
		try {
			for(int i = 0; i<this.numNeg; i++){
				Random rand = new Random();
				this.x1 = rand.nextInt(this.imagem.getWidth()-10);
				this.x2 = rand.nextInt(this.imagem.getWidth());
				this.y1 = Math.abs(rand.nextInt(this.imagem.getHeight()-10));
				this.y2 = Math.abs(rand.nextInt(this.imagem.getHeight()));
				this.corrigirVariaveis();
				this.qtdNegativa++;
				BufferedImage imagemNegativa = this.imagem.getSubimage(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
				ImageIO.write(imagemNegativa, "bmp", new File("./Negativas/N"+this.qtdNegativa+".bmp"));
				this.arquivoNegativas.add("Negativas/N"+this.qtdNegativa+".bmp");
				System.out.println("Imagem N"+this.qtdNegativa+".bmp salva com sucesso");
			}
		} catch (IOException e) {
			System.out.println("Erro ao salvar a imagem N"+this.indiceArquivo+".bmp");
		}
	}
	
	public void corrigirVariaveis(){
	  int troca;
	  if (this.x1==this.x2){
		  this.x2+=10;
	  }
	  
	  if (this.y1==this.y2){
		  this.y2+=10;
	  }
	  
	  if (this.x1<0){
		this.x1 = 0;  
	  }
	  
	  if (this.x2<0){
        this.x2 = 10;  
      }
	  
	  if (this.y1<0){
		this.y1 = 0;  
	  }
	  
	  if (this.y2<0){
        this.y2 = 10;  
      }
	  
	  if (this.y1>this.imagem.getHeight()){
		  this.y1=this.imagem.getHeight()-10;  
	  }
	  
	  if (this.y2>this.imagem.getHeight()){
		  this.y2=this.imagem.getHeight()-9;  
	  }

	  if (this.x1>this.imagem.getWidth()){
		  this.x1=this.imagem.getWidth()-10;  
	  }
	  
	  if (this.x2>this.imagem.getWidth()){
		  this.x2=this.imagem.getWidth()-9;  
	  }
	  
     if (this.x1>this.x2){
		  troca = this.x1;
		  this.x1 = this.x2;
		  this.x2 = troca;
	  }
     
	  if (this.y1>this.y2){
		  troca = this.y1;
		  this.y1 = this.y2;
		  this.y2 = troca;
	  }
	  	    
	}
	
	public void paintBlack(){
	  this.corrigirVariaveis();
	  for (int i=this.x1; i<=this.x2; i++){
	    for (int j=this.y1; j<=this.y2; j++){
	      this.imagem.setRGB(i, j, 0);	   
	    }
	  }
	  this.logo = new ImageIcon(this.imagem);
      this.lbImagem.setIcon(this.logo);
	}
	
	private void clickBtAddPositiva(){
		this.cropAndSave();
		this.paintBlack();
		this.evento.getListaPositivos().add(new Quadrado(this.x1,this.x2,this.y1,this.y2));
	}

    
	private void clickBtDesfazer(){
		
	}
	
	private void clickBtPaint(){
	  this.paintBlack();
      this.logo = new ImageIcon(this.imagem);
      this.lbImagem.setIcon(this.logo);
      this.evento.getListaNegativos().add(new Quadrado(this.x1, this.x2,this.y1,this.y2));
	}
	
	private void lbImagemMouseClicked(java.awt.event.MouseEvent evt){
      if (!evt.isShiftDown()){
        this.x1 = evt.getX();
        this.y1 = evt.getY();			
      } else {
        this.x2 = evt.getX();
        this.y2 = evt.getY();
      }
      this.desenharQuadrado();
	}
	
	public void desenharQuadrado(){
        this.corrigirVariaveis();
        this.logo = new ImageIcon(this.imagem.getSubimage(this.x1, this.y1, this.x2-this.x1, this.y2-this.y1));
	    this.lbImagemAux.setIcon(this.logo);
	    this.lbImagemAux.setText("x1 = "+this.x1+"; y1= "+this.y1+"; x2 = "+this.x2+"; y2 = "+this.y2); 
	}
	
	public void abrirImagem(){
		this.evento = new Evento();
		this.x1 = this.x2 = this.y1 = this.y2 = 0;
		if (this.listaArquivos.isEmpty()){
			System.out.println(this.listaArquivos.size());
			this.dispose();
			return;
		}
		
		System.out.println("Imagem "+this.indiceArquivo+": "+this.listaArquivos.get(0)); 
		this.indiceArquivo++;
		if(this.listaArquivos.size()==1 || ((this.quantidadeLote>1) && ((this.listaArquivos.size()-1)%this.quantidadeRepeticao)==0)){
			this.btProxImagem.setText("Finalizar");
		}
		try{
			this.lbImagem.setText("");
			this.lbImagem.setLocation(new Point(0,0));
			this.imagem = ImageIO.read(new File(this.listaArquivos.get(0)));
			this.logo = new ImageIcon(this.imagem);
			this.setSize(((this.imagem.getWidth())>550)?(this.imagem.getWidth()):550,this.logo.getIconHeight()*2+100);
			if (this.logo == null){
				System.out.println("(NULL) Erro na imagem "+(this.indiceArquivo-1)+": "+this.listaArquivos.get(0)); 
				this.lbImagem.setText("Erro na Imagem");
				this.listaArquivos.remove(0);
				this.clickBtProximo();
			}else{
			  this.lbImagem.setIcon(this.logo);
			  this.listaArquivos.remove(0);
			}
		} catch(Exception ex){
			System.out.println("Erro na imagem "+(this.indiceArquivo-1)+": "+this.listaArquivos.get(0)); 
			this.lbImagem.setText("Erro na Imagem");
			this.listaArquivos.remove(0);
			this.clickBtProximo();
		}

	}
}
