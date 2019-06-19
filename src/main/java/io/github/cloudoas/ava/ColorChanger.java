package io.github.cloudoas.ava;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class ColorChanger {
	private Set<String> rgbs = new HashSet<>();
	private Set<String> reds = new HashSet<>();
	private Set<String> greens = new HashSet<>();
	private Set<String> blues = new HashSet<>();
	
	public String getRGBString(Color color) {
		
		if (null!=color) {
			return String.format("%d,%d,%d", color.getRed(), color.getGreen(), color.getBlue());
		}
		
		return StringUtils.EMPTY;
	}
	
	public void collectColor(Color color) {
		this.collectColor(getRGBString(color), Integer.toString(color.getRed()), Integer.toString(color.getGreen()), Integer.toString(color.getBlue()));
	}
	
	public void collectColor(String rgb, String red, String green, String blue) {
		if (!rgbs.contains(rgb)) {
			rgbs.add(rgb);
		}
		
		if (!reds.contains(red)) {
			reds.add(red);
		}
		
		if (!greens.contains(green)) {
			greens.add(green);
		}
		
		if (!blues.contains(blue)) {
			blues.add(blue);
		}
	}
	
	public void printColors() {
		System.out.println("RGB LIST:\n" + String.join("\n", rgbs));
		System.out.println("RED LIST:\n" + String.join("\n", reds));
		System.out.println("GREEN LIST:\n" + String.join("\n", greens));
		System.out.println("BLUE LIST:\n" + String.join("\n", blues));
	}

	public static void main(String[] args) throws IOException {
		if (args.length<2) {
			System.out.println("Usage: java io.github.cloudoas.ava.ColorChanger <srcFileName> <outFileName>");
			
			System.exit(1);
		}
		
		String sourceFileName = args[0];
		String outputFileName = args[1];
		
		System.out.println("read file " + sourceFileName);
		
        BufferedImage bufferedImage = ImageIO.read(new File(sourceFileName));
        int height = bufferedImage.getHeight(), width = bufferedImage.getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color( bufferedImage.getRGB( x, y ) );
                
                Color newColor = null;
                
                if (color.getRed() > 200 && color.getGreen() > 200 && color.getBlue() > 200) {
                	newColor = Color.WHITE;
                }else {
                	int blue = color.getBlue();
                	
                	if (blue > 100) {
                		blue = 100;
                	}
                	
                	newColor = new Color(11, 61, 146);
                }
                
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        
        System.out.println("writing file " + outputFileName);
        
        File outputfile = new File(outputFileName);
        ImageIO.write(bufferedImage, "jpg", outputfile);
        
        System.out.println("Done.");
    }
}
