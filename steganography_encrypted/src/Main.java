import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Some variables was written in spanish.
 * @author Juan Pablo Camargo Lasso
 * Universidad El Bosque
 * Universidad Nacional De Colombia
 * Bogota - Colombia
 */
public class Main{

	String format;
	int height;
	int width;
	int grayLevel;
	String image_full;
	Viewer viewer;
	int[][] matrizActual;
	Image actual;
	Image imMain;
	ImgProcessed imageProcessed;
	Image mixed;
	int tim;

	public Main(Viewer viewer) {
		this.viewer = viewer;
	}

	//Read the big image
	void read_imageMain() throws Exception{
		JFileChooser search = new JFileChooser("./img");
		search.showOpenDialog(search);
		FileInputStream f = new FileInputStream(search.getSelectedFile());
		DataInputStream d = new DataInputStream(f);
		format = d.readLine();
		//Read in format P3 ASCCI PPM -------------------------------------------------------
		if(format.equalsIgnoreCase("p3")) {
			Scanner sc = new Scanner(search.getSelectedFile());
			format = sc.nextLine();
			width = sc.nextInt();
			height = sc.nextInt();
			grayLevel = sc.nextInt();
			if(width != actual.width*16 || height != actual.height*16) {
				throw new Exception("La imagen no cumple con las dimensiones establecidas");
			}
			System.out.println(format +" "+ height +" "+ width +" "+grayLevel);
			String[][] string_image= new String[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					string_image[i][j] = sc.nextInt()+" "+sc.nextInt()+" "+sc.nextInt();
				}
			}
			imMain = new Image(format, height, width, null, string_image);
		}
		//Read in format P6 Binary PPM -------------------------------------------------------
		else if(format.equalsIgnoreCase("p6")) {
			String line = d.readLine();
			Scanner s = new Scanner(line);	
			width = s.nextInt();
			height = s.nextInt();
			line = d.readLine();
			tim = Integer.parseInt(JOptionPane.showInputDialog("How many will overcome the large image to the small image?"));
			if(width != actual.width*tim || height != actual.height*tim) {
				throw new Exception("La imagen no cumple con las dimensiones establecidas");
			}
			System.out.println(format +" "+ height +" "+ width +" "+grayLevel);
			s = new Scanner(line);
			grayLevel = s.nextInt();
			String[][] matriz_image= new String[height][width];
			byte b;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					b = (byte) (d.readUnsignedByte());
					int newB=(int) (b & 0xFF);
					matriz_image[i][j] = newB+" ";
					b = (byte) (d.readUnsignedByte());
					newB=(int) (b & 0xFF);
					matriz_image[i][j] +=newB+" "; 
					b = (byte) (d.readUnsignedByte());
					newB=(int) (b & 0xFF);
					matriz_image[i][j] +=newB; 
				}
			}
			imMain = new Image(format, height, width, null, matriz_image);
		}
	}

	//Read the small image
	void read_image() throws Exception {
		JFileChooser search = new JFileChooser("./img");
		search.showOpenDialog(search);
		FileInputStream f = new FileInputStream(search.getSelectedFile());
		DataInputStream d = new DataInputStream(f);
		format = d.readLine();
		//Read in format P3 ASCCI PPM -------------------------------------------------------
		if(format.equalsIgnoreCase("p3")) {
			Scanner sc = new Scanner(search.getSelectedFile());
			format = sc.nextLine();
			width = sc.nextInt();
			height = sc.nextInt();
			grayLevel = sc.nextInt();
			System.out.println(format +" "+ height +" "+ width +" "+grayLevel);
			String[][] string_image= new String[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					string_image[i][j] = sc.nextInt()+" "+sc.nextInt()+" "+sc.nextInt();
				}
			}
			viewer.showImagePPM(height, width, string_image);
			actual = new Image(format, height, width, null, string_image);
		}
		//Read in format P6 Binary PPM -------------------------------------------------------
		else if(format.equalsIgnoreCase("p6")) {
			String line = d.readLine();
			Scanner s = new Scanner(line);	
			width = s.nextInt();
			height = s.nextInt();
			line = d.readLine();
			s = new Scanner(line);
			grayLevel = s.nextInt();
			System.out.println(format +" "+ height +" "+ width +" "+grayLevel);
			String[][] matriz_image= new String[height][width];
			byte b;
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					b = (byte) (d.readUnsignedByte());
					int newB=(int) (b & 0xFF);
					matriz_image[i][j] = newB+" ";
					b = (byte) (d.readUnsignedByte());
					newB=(int) (b & 0xFF);
					matriz_image[i][j] +=newB+" "; 
					b = (byte) (d.readUnsignedByte());
					newB=(int) (b & 0xFF);
					matriz_image[i][j] +=newB; 
				}
			}
			viewer.showImagePPM(height, width, matriz_image);
			actual = new Image(format, height, width, null, matriz_image);
		}
	}	

	//Write the image mixed in file PPM format
	public void writeImg() throws Exception {
		JFileChooser jF1= new javax.swing.JFileChooser(); 
		String ruta = ""; 
		if(jF1.showSaveDialog(null)==jF1.APPROVE_OPTION){ 
			ruta = jF1.getSelectedFile().getAbsolutePath(); 
			System.out.println(ruta);
			//File img = new File(ruta+"."+format);
			File img = null;
			img = new File(ruta+".ppm");
			FileWriter fw = new FileWriter(img);
			PrintWriter wtr = new PrintWriter(fw);
			wtr.println(mixed.format);
			wtr.println(mixed.width);
			wtr.println(mixed.height);
			wtr.println(255);
			JOptionPane.showMessageDialog(null, "Wait, the writing will ended in a moment \n Push OK to start", "OK", JOptionPane.DEFAULT_OPTION);
			if(mixed.format.equalsIgnoreCase("p3")||mixed.format.equalsIgnoreCase("p6")) {
				for (int i = 0; i < mixed.imgPPM.length; i++) {
					String line = "";
					for (int j = 0; j < mixed.imgPPM[i].length; j++) {
						line+=mixed.imgPPM[i][j]+" ";
					}
					wtr.println(line);
				}
				fw.close();
				wtr.close();
			}
		}
	}

	//Replace the pixel from big image with the pixel from small image
	public void mixImages() {
		String[][] matriz_image= imMain.imgPPM;
		String[][] matriz_min = actual.imgPPM;
		int ii = 0;
		int jj = 0;
		for(int i = 0; i < actual.height ; i++){
			for(int j = 0; j < actual.width ; j++) {
				matriz_image[ii][jj] = matriz_min[i][j];
				jj+=tim;
			}
			jj = 0;
			ii += tim;
		}
		JOptionPane.showMessageDialog(null, "Mix finalized", "OK", JOptionPane.DEFAULT_OPTION);
		mixed = new Image("P3", imMain.height, imMain.width, null, matriz_image);
		try {
			JOptionPane.showMessageDialog(null, "Now you can save the image", "OK", JOptionPane.DEFAULT_OPTION);
			writeImg();
			JOptionPane.showMessageDialog(null, "Writing finished", "OK", JOptionPane.DEFAULT_OPTION);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void detector() {
		String result = actual.detector();
		JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.DEFAULT_OPTION);
	}
}
