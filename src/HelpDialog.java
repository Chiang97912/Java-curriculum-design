import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog{
	private Window parentWindow;
	HelpDialog(Window parentWindow) {
		super(parentWindow);
		this.parentWindow = parentWindow;
		init();
	}
	public void init() {
		JLabel label1 = new JLabel("小提示");
		label1.setHorizontalAlignment(JLabel.CENTER);
		label1.setVerticalAlignment(JLabel.CENTER);
		label1.setFont(new Font("",1,22));
		label1.setForeground(Color.LIGHT_GRAY);
		this.add(label1,BorderLayout.NORTH);
		JTextArea textArea = new JTextArea();
		textArea.setText("1.一次消除越多同色方块获得分数越高\n2.关卡结束时剩余方块越少获得分数越高");
		textArea.setFont(new Font("",0,22));
		textArea.setFocusable(false);
		textArea.setLineWrap(true);
		this.add(textArea,BorderLayout.CENTER);
		this.setResizable(false);
		this.setModal(true);
		this.setSize(300,300);
		this.setLocationRelativeTo(parentWindow);
		this.setVisible(true);
	}
}
