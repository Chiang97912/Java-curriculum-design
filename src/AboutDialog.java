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
		textArea.setText("����Ϸ��Ȩ��peter��mbw��hzh����\n��Ϸ�е�ͼƬ����Ч��Ȩ��popstar�����ι�˾���������ǣ���Ȩ����\n"
				+ "��ϵ���䣺1273468372@qq.com");
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
