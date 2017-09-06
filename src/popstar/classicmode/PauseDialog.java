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
 * �����ͣ��Ϸ��ť�󵯳��öԻ���
 * @author Peter
 *
 */
public class PauseDialog extends JDialog {
	/**  */
	private JPanel mainPanel;
	private JButton menuViewButton, tipsButton, aboutDialogButton,backButton;
	/** ��Ϸ����������� */
	private MainFrame mainFrame;
	/** ���˵���������� */
	private MenuView mv;
	/** ��ť���� */
	private JButton buttons[];
	
	/**
	 * 
	 * @param mv ���˵����������
	 * @param mainFrame ��Ϸ���������
	 * @param buttons ��ť����
	 */
	PauseDialog(MenuView mv,MainFrame mainFrame,JButton[] buttons) {
		super(mainFrame);
		this.mainFrame = mainFrame;
		this.mv = mv;
		this.buttons = buttons;
		init();
	}
	/** PauseDialog��ĳ�ʼ������ */
	public void init() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",20f);
		Font font = fontClass.loadFont();
		mainPanel = new JPanel();
		menuViewButton = new JButton("���˵�");
		menuViewButton.setFont(font);
		menuViewButton.setBackground(new Color(251,216,96));
		menuViewButton.setFocusable(false);
		menuViewButton.setBorderPainted(false);
		menuViewButton.addActionListener(new MenuViewButtonListener());
		tipsButton = new JButton("С��ʾ");
		tipsButton.setFont(font);
		tipsButton.setBackground(new Color(251,216,96));
		tipsButton.setFocusable(false);
		tipsButton.setBorderPainted(false);
		tipsButton.addActionListener(new TipsButtonListener());
		aboutDialogButton = new JButton("����");
		aboutDialogButton.setFont(font);
		aboutDialogButton.setBackground(new Color(251,216,96));
		aboutDialogButton.setBorderPainted(false);
		aboutDialogButton.setFocusable(false);
		aboutDialogButton.addActionListener(new AboutDialogButtonListener());
		backButton = new JButton("������Ϸ");
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
	
	/** ���˵���ť�ļ����� */
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
			PauseDialog.this.dispose(); //�������ٴ���
			mainFrame.setVisible(false);
			mainFrame.dispose();
			mainFrame = null;
			mv.setVisible(true);
			mv.sound.play();
			mv.sound.loop();
		}
		
	}
	
	/** ��ʾ��ť�ļ����� */
	class TipsButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new HelpDialog(PauseDialog.this);
		}
		
	}
	
	/** ���ڶԻ���ļ����� */
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
