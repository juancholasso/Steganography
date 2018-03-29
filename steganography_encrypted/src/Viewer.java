import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
//The method main is in this Class
/**
 * Some variables was written in Spanish.
 * @author Juan Pablo Camargo Lasso
 * Universidad El Bosque
 * Universidad Nacional De Colombia
 * Bogota - Colombia
 */

//This program used images in format PPM 
public class Viewer extends JFrame implements ActionListener
{
	JButton b_Open;
	JButton b_OpenImageBig;
	JButton b_mix;
	JButton b_detector;

	
	Main main;
	JLabel label = new JLabel();
	JPanel panelButtons;
	boolean upImage = false;
	
	public Viewer() {
		main = new Main(this);
		this.setName("Image Processing");
		this.setTitle("Image Processing");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.pack();
		this.add(label, BorderLayout.CENTER);

		b_Open = new JButton("Open Image");
		b_Open.setActionCommand("b_Open");
		b_Open.setBackground(Color.BLACK);
		b_Open.setForeground(Color.white);
		b_Open.addActionListener(this);
		
		b_OpenImageBig = new JButton("Open Image Big");
		b_OpenImageBig.setActionCommand("b_OpenBig");
		b_OpenImageBig.setBackground(Color.BLACK);
		b_OpenImageBig.setForeground(Color.white);
		b_OpenImageBig.addActionListener(this);

		b_mix = new JButton("Mix");
		b_mix.setActionCommand("b_mix");
		b_mix.setBackground(Color.BLACK);
		b_mix.setForeground(Color.white);
		b_mix.addActionListener(this);
		
		b_detector = new JButton("Detector");
		b_detector.setActionCommand("b_detector");
		b_detector.setBackground(Color.BLACK);
		b_detector.setForeground(Color.white);
		b_detector.addActionListener(this);

		panelButtons = new JPanel();
		panelButtons.setLayout(new FlowLayout());
		panelButtons.add(b_Open);
		panelButtons.add(b_OpenImageBig);
		panelButtons.add(b_mix);
		panelButtons.add(b_detector);


		this.add(panelButtons, BorderLayout.SOUTH);
		this.setSize(900,600);
		this.setResizable(true);
		this.setBackground(Color.DARK_GRAY);
		this.setVisible(true);
	}

	//Print the image on user interface
	public void showImagePPM(int height, int width, String[][] matriz_image) {
		this.remove(label);
		validate();
		BufferedImage bi = new BufferedImage(width ,height,BufferedImage.TYPE_INT_RGB);
		ImageIcon icon = new ImageIcon( bi );
		label = new JLabel(icon);
		this.add(label, BorderLayout.CENTER);
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				String RGB[] = matriz_image[y][x].split(" ");
				Color co = new Color(Integer.parseInt(RGB[0]),Integer.parseInt(RGB[1]),Integer.parseInt(RGB[2]));
				int colorValur = co.getRGB();
				bi.setRGB(x, y, colorValur);
			}
		}
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(e.getActionCommand().equals("b_Open")) {
			try {
				main.read_image();
				JOptionPane.showMessageDialog(this, "The image was uploaded", "Image Uploaded", JOptionPane.DEFAULT_OPTION);
				upImage = true;
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "You have not selected any image", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("b_OpenBig")) {
			try {
				if(upImage == true) {
					main.read_imageMain();
					JOptionPane.showMessageDialog(this, "The image was uploaded","Image Uploaded", JOptionPane.DEFAULT_OPTION);
				}else
					JOptionPane.showMessageDialog(this, "You have not selected any image", "Warning", JOptionPane.WARNING_MESSAGE);
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "You have not selected any image", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("b_mix")) {
				if(upImage == true) {
					main.mixImages();
				}else
					JOptionPane.showMessageDialog(this, "You have not selected the big image", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		else if(e.getActionCommand().equals("b_detector")) {
			if(upImage == true) {
				main.detector();
			}else {
				try {
					main.read_image();
					JOptionPane.showMessageDialog(this, "The image was uploaded", "Image Uploaded", JOptionPane.DEFAULT_OPTION);
					upImage = true;
					main.detector();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "You have not selected any image", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}

	public static void main(String[] args) {
		new Viewer();
	}
}