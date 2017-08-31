import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class PauseDialog extends JDialog {
	private JPanel mainPanel;
	private JButton menuViewButton, tipsButton, aboutDialogButton;
	private MainFrame mainFrame;
	private MenuView mv;
	private JButton buttons[];
	PauseDialog(MenuView mv,MainFrame mainFrame,JButton[] buttons) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		this.mv = mv;
		this.buttons = buttons;
		init();
	}
	public void init() {
		mainPanel = new JPanel();
		menuViewButton = new JButton("主菜单");
		menuViewButton.setBorderPainted(false);
		menuViewButton.addActionListener(new MenuViewButtonListener());
		tipsButton = new JButton("小提示");
		tipsButton.setBorderPainted(false);
		tipsButton.addActionListener(new TipsButtonListener());
		
		aboutDialogButton = new JButton("关于");
		aboutDialogButton.setBackground(new Color(251,216,96));
		aboutDialogButton.setBorderPainted(false);
		aboutDialogButton.setFocusable(false);
		aboutDialogButton.addActionListener(new AboutDialogButtonListener());
		mainPanel.setLayout(null);
		mainPanel.add(menuViewButton);
		mainPanel.add(tipsButton);
		mainPanel.add(aboutDialogButton);
		this.add(mainPanel);
		this.setSize(300,300);
		this.setVisible(true);
		this.setLocationRelativeTo(mainFrame);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
		this.setModal(true);
		menuViewButton.setBounds(100,30,100,30);
		menuViewButton.setBackground(new Color(251,216,96));
		menuViewButton.setFocusable(false);
		tipsButton.setBounds(100,80,100,30);
		tipsButton.setBackground(new Color(251,216,96));
		tipsButton.setFocusable(false);
		aboutDialogButton.setBounds(100,130,100,30);
	}
	class MenuViewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//get the information of buttons
			boolean isVisible[] = new boolean[buttons.length];
			for(int i=0;i<buttons.length;i++) {
				isVisible[i] = buttons[i].isVisible();
			}
			GameLog gameLog = new GameLog(mainFrame.getImageTagAll(), isVisible, buttons.length, mainFrame.level,mainFrame.goal,mainFrame.score);
			try {
				FileOutputStream fos = new FileOutputStream("game.log");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(gameLog);
				oos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			PauseDialog.this.setVisible(false);
			PauseDialog.this.dispose(); //彻底销毁窗口
			mainFrame.setVisible(false);
			mainFrame.dispose();
			mainFrame = null;
			mv.setVisible(true);
			mv.sound.play();
			mv.sound.loop();
		}
		
	}
	
	class TipsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new HelpDialog(PauseDialog.this);
		}
		
	}
	
	class AboutDialogButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AboutDialog(PauseDialog.this);
		}
		
	}
}
