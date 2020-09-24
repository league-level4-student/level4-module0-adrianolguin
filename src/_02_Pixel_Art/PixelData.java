package _02_Pixel_Art;

import java.io.Serializable;

public class PixelData implements Serializable{
	public Pixel[][] pixels;
	
	public PixelData(Pixel[][] pixels) {
		this.pixels = pixels;
	}

}
