/**
 * Some variables was written in spanish.
 * @author Juan Pablo Camargo Lasso
 * Universidad El Bosque
 * Universidad Nacional De Colombia
 * Bogota - Colombia
 */

public class ImgProcessed {
	public String format;
	int levelgray;
	int height;
	int width;
	int imgPGM[][];
	String imgPPM[][];

	
	public ImgProcessed(String format, int heigth, int width, int imgPGM[][], int levelgray, String imgPPM[][]) {
		this.format = format;
		this.height = heigth;
		this.width = width;
		this.imgPGM = imgPGM;
		this.levelgray = levelgray;
		this.imgPPM = imgPPM;
	}
}
