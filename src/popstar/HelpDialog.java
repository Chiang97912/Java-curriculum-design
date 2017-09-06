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
 * 游戏帮助对话框
 * @author Peter
 *
 */
public class HelpDialog extends JDialog{
	/** 帮助对话框的父窗口 */
	private Window parentWindow;
	/**
	 * 
	 * @param parentWindow 帮助对话框的父窗口
	 */
	public HelpDialog(Window parentWindow) {
		super(parentWindow);
		this.parentWindow = parentWindow;
		init();
	}
	/** 帮助对话框的初始化函数 */
	public void init() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",18f);
		Font font = fontClass.loadFont();
		JLabel label1 = new JLabel("小提示");
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setVerticalAlignment(JLabel.CENTER);
		label1.setFont(new Font("",1,22));
		label1.setForeground(Color.LIGHT_GRAY);
		this.add(label1,BorderLayout.NORTH);
		JTextArea textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setText("###经典模式###：\n1.一次消除越多同色方块获得分数越高\n2.关卡结束时剩余方块越少获得分数越高\n###限时模式###：\n"
				+ "初始限时40秒\n一次连消星星越多，奖励加时越多！\n6秒连消+1秒\n7秒连消+2秒\n依次类推");
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
