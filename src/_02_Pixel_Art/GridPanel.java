package _02_Pixel_Art;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int windowWidth;
	private int windowHeight;
	private int pixelWidth;
	private int pixelHeight;
	private int rows;
	private int cols;
	
	private static final String DATA_FILE = "src/_02_Pixel_Art/pixelData";

	// 1. Create a 2D array of pixels. Do not initialize it yet.

	Pixel[][] pixels;

	private Color color;

	public GridPanel(int w, int h, int r, int c) {
		this.windowWidth = w;
		this.windowHeight = h;
		this.rows = r;
		this.cols = c;

		this.pixelWidth = windowWidth / cols;
		this.pixelHeight = windowHeight / rows;

		color = Color.BLACK;

		setPreferredSize(new Dimension(windowWidth, windowHeight));

		// 2. Initialize the pixel array using the rows and cols variables.
		pixels = new Pixel[cols][rows];

		// 3. Iterate through the array and initialize each element to a new pixel.
		initializePixels();
				
	}

	public void setColor(Color c) {
		color = c;
	}

	public void clickPixel(int mouseX, int mouseY) {
		// 5. Use the mouseX and mouseY variables to change the color
		// of the pixel that was clicked. *HINT* Use the pixel's dimensions.

		int pixelX = mouseX / pixelWidth;
		int pixelY = mouseY / pixelHeight;

		pixels[pixelX][pixelY].color = color;

	}

	public void savePixelData() {
		PixelData data = new PixelData(pixels);
		try {
			FileOutputStream fos = new FileOutputStream(new File(DATA_FILE));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadPixelData() {
		try {
			FileInputStream fis = new FileInputStream(new File(DATA_FILE));
			ObjectInputStream ois = new ObjectInputStream(fis);
			PixelData data = (PixelData) ois.readObject();
			pixels = data.pixels;
			for (int i = 0; i < data.pixels.length; i++) {
				for (int j = 0; j < data.pixels[0].length; j++) {
					System.out.println(data.pixels[i][j].x + ", " + data.pixels[i][j].y + " - " + data.pixels[i][j].color);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		// 4. Iterate through the array.
		// For every pixel in the list, fill in a rectangle using the pixel's color.
		// Then, use drawRect to add a grid pattern to your display.
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				g.setColor(pixels[i][j].color);
				g.fillRect(i * pixelWidth, j * pixelHeight, pixelWidth, pixelHeight);
				g.setColor(Color.black);
				g.drawRect(i * pixelWidth, j * pixelHeight, pixelWidth, pixelHeight);
			}
		}

	}
	
	public void initializePixels() {
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				pixels[i][j] = new Pixel(i, j);
			}
		}
	}
	
}
