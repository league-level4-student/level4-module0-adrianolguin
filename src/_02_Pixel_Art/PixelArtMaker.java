package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PixelArtMaker implements MouseListener, ActionListener{
	private JButton save;
	private JButton clear;
	private JButton load;
	
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	ColorSelectionPanel csp;
	
	public void start() {
		gip = new GridInputPanel(this);	
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);
		
		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		csp = new ColorSelectionPanel();
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		
		save = new JButton("save");
		save.addActionListener(this);
		window.add(save);
		
		clear = new JButton("Clear");
		clear.addActionListener(this);
		window.add(clear);
		
		load = new JButton("load");
		load.addActionListener(this);
		window.add(load);
		
		gp.repaint();
		gp.addMouseListener(this);
		window.pack();
		
		gp.loadPixelData();
	}
	
	public static void main(String[] args) {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			gp.savePixelData();
		} else if(e.getSource() == clear) {
			gp.initializePixels();
			gp.repaint();
		} else if(e.getSource() == load) {
			gp.loadPixelData();
			gp.repaint();
		}
	}
}
