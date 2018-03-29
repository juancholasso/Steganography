import java.math.BigDecimal;
import java.math.BigInteger;

import javax.swing.JOptionPane;

/**
 * Some variables was written in spanish.
 * @author Juan Pablo Camargo Lasso
 * Universidad El Bosque
 * Universidad Nacional De Colombia
 * Bogota - Colombia
 */

public class Image {

	String format;
	int height;
	int width;
	int grayLevel;
	String imgPPM[][];
	int quantGray;

	public Image(String format, int heigth, int width, int imgPGM[][], String imgPPM[][]) {
		this.format = format;
		this.height = heigth;
		this.width = width;
		this.imgPPM = imgPPM;
	}

	//This method does the following
	//Example: Vector["as","co","tr","ze","xh"] -> max_vector -> ["as","as","co","tr","ze","xh","xh"]
	public String[] max_vector(String[] vector) {
		String[] max_vector = new String[vector.length+2];
		max_vector[0] = vector[0];
		max_vector[max_vector.length-1] = vector[vector.length-1];
		for (int i = 1; i < max_vector.length-1; i++) {
			max_vector[i]= vector[i-1];
		}
		return max_vector;
	}

	//This Methods could detect if a image contains another image
	public String detector(){
		JOptionPane.showMessageDialog(null, "Wait, the detection will ended in a moment \n Push OK to start", "OK", JOptionPane.DEFAULT_OPTION);
		String[][] matriz_work = new String[height+2][width+2];
		matriz_work[0] = max_vector(imgPPM[0]);
		matriz_work[matriz_work.length-1] = max_vector(imgPPM[imgPPM.length-1]);
		int countRarePixels = 0;
		for (int i = 1; i < matriz_work.length-1; i++) {
			matriz_work[i] = max_vector(imgPPM[i-1]);
		}
		for (int i = 1; i < matriz_work.length-1; i++) {
			for (int j = 1; j < matriz_work[i].length-1; j++) {
				String px1 [] = matriz_work[i-1][j-1].split(" ");
				String px2 [] = matriz_work[i-1][j].split(" ");
				String px3 [] = matriz_work[i-1][j+1].split(" ");
				String px4 [] = matriz_work[i][j-1].split(" ");
				String px5Center [] = matriz_work[i][j].split(" ");
				String px6 [] = matriz_work[i][j+1].split(" ");
				String px7 [] = matriz_work[i+1][j-1].split(" ");
				String px8 [] = matriz_work[i+1][j].split(" ");
				String px9 [] = matriz_work[i+1][j+1].split(" ");

				int addColorR = Integer.parseInt(px1[0])+Integer.parseInt(px2[0])+Integer.parseInt(px3[0])
				+Integer.parseInt(px4[0])+Integer.parseInt(px6[0])+Integer.parseInt(px7[0])+Integer.parseInt(px8[0])
				+Integer.parseInt(px9[0]);

				int addColorG = Integer.parseInt(px1[1])+Integer.parseInt(px2[1])+Integer.parseInt(px3[1])
				+Integer.parseInt(px4[1])+Integer.parseInt(px6[1])+Integer.parseInt(px7[1])+Integer.parseInt(px8[1])
				+Integer.parseInt(px9[1]);

				int addColorB = Integer.parseInt(px1[2])+Integer.parseInt(px2[2])+Integer.parseInt(px3[2])
				+Integer.parseInt(px4[2])+Integer.parseInt(px6[2])+Integer.parseInt(px7[2])+Integer.parseInt(px8[2])
				+Integer.parseInt(px9[2]);

				int colorRCenter = Integer.parseInt(px5Center[0]) * 8;
				int colorGCenter = Integer.parseInt(px5Center[1]) * 8;
				int colorBCenter = Integer.parseInt(px5Center[2]) * 8;

				int rounds = 0;
				
				addColorR = addColorR - colorRCenter;
				addColorG = addColorG - colorGCenter;
				addColorB = addColorB - colorBCenter;

				if(addColorR < -400 || addColorR > 400)
					rounds ++;
				if(addColorG < -400 || addColorG > 400)
					rounds ++;
				if(addColorB < -400 || addColorB > 400)
					rounds ++;
				
				if(rounds == 3)
					countRarePixels++;
			}
		}
		int sizeDiv16 = ((height/16) * (width/16));
		int sizeDiv12 = ((height/12)* (width/12));
		int sizeDiv8 = ((height/8) * (width/8));
		int sizeDiv4 = ((height/4) * (width/4));
		double porcent16 = (countRarePixels*100/sizeDiv16);
		double porcent12 = (countRarePixels*100/sizeDiv12);
		double porcent8 = (countRarePixels*100/sizeDiv8);
		double porcent4 = (countRarePixels*100/sizeDiv4);
		System.out.println(countRarePixels);
		System.out.println("The probability that the images contains other image is: \n"
				+"The number of rare pixels is: "+countRarePixels
				+ "If images is 16 minor: "+porcent16
				+ "\nIf images is 12 minor: "+porcent12
				+ "\nIf images is 8 minor: "+porcent8
				+ "\nIf images is 4 minor: "+porcent4);
		
		return ("The probability that the images contains other image is: \n"
				+"The number of rare pixels is: "+countRarePixels
				+ "\nIf images is 16 minor: "+porcent16
				+ "%\nIf images is 12 minor: "+porcent12
				+ "%\nIf images is 8 minor: "+porcent8
				+ "%\nIf images is 4 minor: "+porcent4+"%");
	}
}

