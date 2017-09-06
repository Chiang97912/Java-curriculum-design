package popstar.classicmode;

import java.awt.Color;
import java.awt.Font;
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

import popstar.AboutDialog;
import popstar.FontClass;
import popstar.HelpDialog;
import popstar.MenuView;

/**
 * 点击暂停游戏按钮后弹出该对话框
 * @author Peter
 *
 */
public class PauseDialog extends JDialog {
	/**  */
	private JPanel mainPanel;
	private JButton menuViewButton, tipsButton, aboutDialogButton,backButton;
	/** 游戏主界面类对象 */
	private MainFrame mainFrame;
	/** 主菜单界面类对象 */
	private MenuView mv;
	/** 按钮数组 */
	private JButton buttons[];
	
	/**
	 * 
	 * @param mv 主菜单界面类对象
	 * @param mainFrame 游戏界面类对象
	 * @param buttons 按钮数组
	 */
	PauseDialog(MenuView mv,MainFrame mainFrame,JButton[] buttons) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		this.mv = mv;
		this.buttons = buttons;
		init();
	}
	/** PauseDialog类的初始化函数 */
	public void init() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",20f);
		Font font = fontClass.loadFont();
		mainPanel = new JPanel();
		menuViewButton = new JButton("主菜单");
		menuViewButton.setFont(font);
		menuViewButton.setBackground(new Color(251,216,96));
		menuViewButton.setFocusable(false);
		menuViewButton.setBorderPainted(false);
		menuViewButton.addActionListener(new MenuViewButtonListener());
		tipsButton = new JButton("小提示");
		tipsButton.setFont(font);
		tipsButton.setBackground(new Color(251,216,96));
		tipsButton.setFocusable(false);
		tipsButton.setBorderPainted(false);
		tipsButton.addActionListener(new TipsButtonListener());
		aboutDialogButton = new JButton("关于");
		aboutDialogButton.setFont(font);
		aboutDialogButton.setBackground(new Color(251,216,96));
		aboutDialogButton.setBorderPainted(false);
		aboutDialogButton.setFocusable(false);
		aboutDialogButton.addActionListener(new AboutDialogButtonListener());
		backButton = new JButton("返回游戏");
		backButton.setFont(font);
		backButton.setBackground(new Color(251,216,96));
		backButton.setBorderPainted(false);
		backButton.setFocusable(false);
		backButton.addActionListener(new BackButtonListener());
		mainPanel.setLayout(null);
		mainPanel.add(menuViewButton);
		mainPanel.add(tipsButton);
		mainPanel.add(aboutDialogButton);
		mainPanel.add(backButton);
		menuViewButton.setBounds(100,30,120,30);
		tipsButton.setBounds(100,80,120,30);
		aboutDialogButton.setBounds(100,130,120,30);
		backButton.setBounds(100,180,120,30);
		this.add(mainPanel);
		this.setUndecorated(true);
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
		this.setResizable(false);
		this.setSize(300,300);
		this.setLocationRelativeTo(mainFrame);
		this.setModal(true);
		this.setVisible(true);
	}
	
	/** 主菜单按钮的监听器 */
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
	
	/** 提示按钮的监听器 */
	class TipsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new HelpDialog(PauseDialog.this);
		}
		
	}
	
	/** 关于对话框的监听器 */
	class AboutDialogButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AboutDialog(PauseDialog.this);
		}
		
	}
	class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PauseDialog.this.setVisible(false);
			PauseDialog.this.dispose();
		}
	}
}
