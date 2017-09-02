import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

public class MenuView extends JFrame{
	private JButton playButton;
	private JButton continueButton;
	private JButton timeModeButton;
	private JButton helpButton;
	private JButton exitButton;
	private JLabel label;
	private int width;
	private int height;
	private String imagePath;
	private ImageIcon im;
	private MainFrame mf;
	MenuView mv = this;
	
	AudioClip sound;
	MenuView() {
		label = new JLabel();
		
		loadImage();
		loadButton();
		this.setSize(width,height);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);

		this.addMouseMotionListener(new MouseEventListener(this));
		this.addMouseListener(new MouseEventListener(this));
		
		HighestScoreLabel hsl = new HighestScoreLabel(width);
		new Thread(hsl).start();
		label.add(hsl);
		File file = new File("sounds/background.wav");
		try {
			sound = Applet.newAudioClip(file.toURL());
			sound.play();
			sound.loop();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
	}

	class MouseEventListener implements MouseInputListener {
	     
	     Point origin;
	     JFrame frame;
	     Point p;
	     
	     public MouseEventListener(JFrame frame) {
	       this.frame = frame;
	       origin = new Point();
	     }
	     
	     @Override
	     public void mouseClicked(MouseEvent e) {
	    	 
	     }
	     
	     @Override
	     public void mousePressed(MouseEvent e) {}
	 
	     @Override
	     public void mouseReleased(MouseEvent e) {}
	
	     @Override
	     public void mouseEntered(MouseEvent e) {}
	     
	
	     @Override
	     public void mouseExited(MouseEvent e) {
	       this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	     }
	 
	
	     @Override
	     public void mouseDragged(MouseEvent e) {
	       p = this.frame.getLocation();
	       this.frame.setLocation(
	         p.x + (e.getX() - origin.x), 
	         p.y + (e.getY() - origin.y));
	     }
	 
	     @Override
	     public void mouseMoved(MouseEvent e) {
	    	 
	     }
	}
	 
	private void loadButton() {
		playButton = new JButton();
		playButton.setBounds(150,150,100,30);
		playButton.setBackground(new Color(251,216,96));
		playButton.setText("新游戏");
		playButton.setFont(new Font("",Font.PLAIN,16));
		playButton.setBorderPainted(false);
		playButton.setFocusable(false);
		playButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream fos = new FileOutputStream("game.log");
					fos.write(new byte[0]);
					fos.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				mf = new MainFrame(mv);
				mv.setVisible(false);
				sound.stop();
				File file = new File("sounds/readygo.wav");
				try {
					AudioClip sound = Applet.newAudioClip(file.toURL());
					sound.play();
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		continueButton = new JButton();
		continueButton.setText("继续");
		continueButton.setFont(new Font("",Font.PLAIN,16));
		continueButton.setBorderPainted(false);
		continueButton.setFocusable(false);
		continueButton.setBackground(new Color(251,216,96));
		continueButton.setBounds(150,200,100,30);
		continueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameLog gameLog = null;
				try {
					FileInputStream fis = new FileInputStream("game.log");
					ObjectInputStream ois = new ObjectInputStream(fis);
					gameLog = (GameLog)ois.readObject();
					ois.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (ClassNotFoundException e3) {
					e3.printStackTrace();
				}
				mf = new MainFrame(mv);
				mf.setLevel(gameLog.getLevel());
				mf.setGoal(gameLog.getGoal());
				mf.setScore(gameLog.getScore());
				mf.setImageTagAll(gameLog.getImageTag());
				mf.setButtons(gameLog.getIsVisible());
				mf.setVisible(true);
				MenuView.this.setVisible(false);
				sound.stop();
			}
			
		});
		
		/*File f = new File("game.log");
		if(f.length()==0) {
			continueButton.setEnabled(false);
		}*/
		timeModeButton = new JButton("限时模式");
		timeModeButton.setFont(new Font("",Font.PLAIN,16));
		timeModeButton.setBounds(150,250,100,30);
		timeModeButton.setBackground(new Color(251,216,96));
		timeModeButton.setBorderPainted(false);
		timeModeButton.setFocusable(false);
		timeModeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TimeModeMainFrame(MenuView.this);
				mv.setVisible(false);
				mv.sound.stop();
			}
			
		});
		
		helpButton = new JButton("游戏帮助");
		helpButton.setFont(new Font("",Font.PLAIN,16));
		helpButton.setBounds(150,300,100,30);
		helpButton.setBackground(new Color(251,216,96));
		helpButton.setBorderPainted(false);
		helpButton.setFocusable(false);
		helpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				HelpDialog helpDialog = new HelpDialog(MenuView.this);
				
			}
			
		});
		exitButton = new JButton("退出游戏");
		exitButton.setFont(new Font("",Font.PLAIN,16));
		exitButton.setBounds(150,350,100,30);
		exitButton.setBackground(new Color(251,216,96));
		exitButton.setBorderPainted(false);
		exitButton.setFocusable(false);
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		label.add(playButton);
		label.add(continueButton);
		label.add(timeModeButton);
		label.add(helpButton);
		label.add(exitButton);
		
	}
	private void loadImage() {
		imagePath = "icons/background.jpg";
		im = new ImageIcon(imagePath);
		label.setIcon(im);
		width = im.getIconWidth();
		height = im.getIconHeight();
		this.add(label);
	}
}
