package popstar;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JTextArea;

/**
 * 游戏的关于对话框
 * @author Peter
 *
 */
public class AboutDialog extends JDialog {
	/** 该对话框的父窗口 */
	private Window parentWindow;
	/**
	 * 
	 * @param parentWindow 对话框的父窗口
	 */
	public AboutDialog(Window parentWindow) {
		super(parentWindow);
		this.parentWindow = parentWindow;
		init();
	}
	/** 对话框的初始化函数 */
	public void init() {
		FontClass fontClass = new FontClass("fonts/STCAIYUN.TTF",19f);
		Font font = fontClass.loadFont();
		JTextArea textArea = new JTextArea();
		textArea.setFont(font);
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
