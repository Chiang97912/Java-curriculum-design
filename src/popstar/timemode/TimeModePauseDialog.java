package popstar.timemode;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import popstar.AboutDialog;
import popstar.FontClass;
import popstar.HelpDialog;
import popstar.MenuView;

/** 
 * 限时模式的暂停游戏对话框
 * @author Peter
 *
 */
public class TimeModePauseDialog extends JDialog {
	private JPanel mainPanel;
	private JButton menuViewButton, tipsButton, aboutDialogButton,backButton;
	private TimeModeMainFrame mainFrame;
	private MenuView mv;
	private JButton buttons[];
	TimeModePauseDialog(MenuView mv,TimeModeMainFrame mainFrame,JButton[] buttons) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		this.mv = mv;
		this.buttons = buttons;
		init();
	}
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
		menuViewButton.setBounds(85,30,120,30);
		tipsButton.setBounds(85,80,120,30);
		aboutDialogButton.setBounds(85,130,120,30);
		backButton.setBounds(85,180,120,30);
		this.add(mainPanel);
		this.setIconImage(new ImageIcon("icons/pass_1.png").getImage());
		this.setUndecorated(true);
		this.setResizable(false);
		this.setSize(280,280);
		this.setLocationRelativeTo(mainFrame);
		this.setModal(true);
		this.setVisible(true);
	}
	class MenuViewButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//get the information of buttons
			boolean isVisible[] = new boolean[buttons.length];
			for(int i=0;i<buttons.length;i++) {
				isVisible[i] = buttons[i].isVisible();
			}
			TimeModePauseDialog.this.setVisible(false);
			TimeModePauseDialog.this.dispose(); //彻底销毁窗口
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
			new HelpDialog(TimeModePauseDialog.this);
		}
		
	}
	
	class AboutDialogButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AboutDialog(TimeModePauseDialog.this);
		}
		
	}
	class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.thread = new Thread(new TimerThread(mainFrame));
			mainFrame.thread.start();
			TimeModePauseDialog.this.setVisible(false);
			TimeModePauseDialog.this.dispose();
		}
	}
}
