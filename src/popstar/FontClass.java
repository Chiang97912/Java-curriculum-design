package popstar;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontClass {
	private float fontSize = 16f;
	private String fontFilePath;
	public FontClass(String fontFilePath) {
		this.fontFilePath = fontFilePath;
	}
	public FontClass(String fontFilePath,float fontSize) {
		this.fontFilePath = fontFilePath;
		this.fontSize = fontSize;
	}
	
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	
	public void setFontFilePath(String fontFilePath) {
		this.fontFilePath = fontFilePath;
	}
	
	public Font loadFont() {
		Font customFont = null;
		try {
		    //create×ÖÌå
		    customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFilePath)).deriveFont(fontSize);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //×¢²á×ÖÌå
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontFilePath)));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		return customFont;
	}
}
