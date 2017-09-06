package popstar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
/**
 * ��Ϸ�����Ի���
 * @author Peter
 *
 */
public class HelpDialog extends JDialog{
	/** �����Ի���ĸ����� */
	private Window parentWindow;
	/**
	 * 
	 * @param parentWindow �����Ի���ĸ�����
	 */
	public HelpDialog(Window parentWindow) {
		super(parentWindow);
		this.parentWindow = parentWindow;
		init();
	}
	/** �����Ի���ĳ�ʼ������ */
	public void init() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",18f);
		Font font = fontClass.loadFont();
		JLabel label1 = new JLabel("С��ʾ");
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setVerticalAlignment(JLabel.CENTER);
		label1.setFont(new Font("",1,22));
		label1.setForeground(Color.LIGHT_GRAY);
		this.add(label1,BorderLayout.NORTH);
		JTextArea textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setText("###����ģʽ###��\n1.һ������Խ��ͬɫ�����÷���Խ��\n2.�ؿ�����ʱʣ�෽��Խ�ٻ�÷���Խ��\n###��ʱģʽ###��\n"
				+ "��ʼ��ʱ40��\nһ����������Խ�࣬������ʱԽ�࣡\n6������+1��\n7������+2��\n��������");
		textArea.setFont(new Font("",0,22));
		textArea.setFocusable(false);
		textArea.setLineWrap(true);
		this.add(textArea,BorderLayout.CENTER);
		this.setResizable(false);
		this.setModal(true);
		this.setSize(300,450);
		this.setLocationRelativeTo(parentWindow);
		this.setVisible(true);
	}
}
