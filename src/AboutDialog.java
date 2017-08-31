import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JTextArea;

public class AboutDialog extends JDialog {
	private Window parentWindow;
	public AboutDialog(Window parentWindow) {
		super(parentWindow);
		this.parentWindow = parentWindow;
		init();
	}
	public void init() {
		JTextArea textArea = new JTextArea();
		textArea.setText("本游戏版权归peter、mbw、hzh所有\n游戏中的图片和音效版权归popstar（掌游公司的消灭星星）版权所有\n"
				+ "联系邮箱：1273468372@qq.com");
		textArea.setLineWrap(true);
		textArea.setFocusable(false);
		textArea.setFont(new Font("",0,20));
		
		this.add(textArea,BorderLayout.CENTER);
		this.setResizable(false);
		this.setModal(true);
		this.setSize(300, 300);
		this.setLocationRelativeTo(parentWindow);
		this.setVisible(true);
	}
}
