package popstar.classicmode;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.*;

/** 
 * 游戏中方块图标按钮的监听器
 * @author Peter
 *
 */
public class ButtonListener implements ActionListener {
	/** 游戏主界面 */
	MainFrame mf;
	/** 游戏主界面的图标按钮数组 */
	JButton[] buttons;
	int i,j;
	/** 图标按钮的行数 */
	int ROWS;
	/** 图标按钮的列数 */
	int COLS;
	/** buttons数组的索引 */
	int index;
	/** 按钮图标的名称 */
	int imageName;
	/** 鼠标点击方块有可以消除的needPop值就为true,否者为false。默认是false */
	boolean needPop = false;
	/** 用于打开文件 */
	File file;
	/** 用于打开音频 */
	AudioClip sound;
	/** 储存一次点击一共消除的数量 */
	int popedNum = 1;
	/**
	 * 
	 * @param mf 游戏主界面类对象
	 * @param buttons 按钮数组
	 * @param i 横坐标索引值
	 * @param j 纵坐标索引值
	 * @param ROWS 按钮行数
	 * @param COLS 按钮列数
	 */
	ButtonListener(MainFrame mf,JButton[] buttons,int i,int j,int ROWS,int COLS) {
		this.mf = mf;
		this.buttons = buttons;
		this.i = i;
		this.j = j;
		this.ROWS = ROWS;
		this.COLS = COLS;
		
		file = new File("sounds/pop_star.wav");
		try {
			sound = Applet.newAudioClip(file.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = null;
		b = (JButton)e.getSource();
		index = Integer.valueOf(b.getActionCommand());
		imageName = mf.getImageTag(index);
		search(i,j);
		fillGap();
		mf.setPopedNum(popedNum);
		mf.updateScore();
		if(mf.score>=mf.goal) {
			mf.addFirstCompleted();
		}
		mf.missionCompleted();
		mf.judge();
		mf.setTipLabel();
		
	}
	
	/** 填充横向和纵向的空白按钮 */
	public void fillGap() {
		fillVGap();
		fillHGap();
	}
	/** 填充纵向的空白按钮 */
	public void fillVGap() {
		for(int i=ROWS-1;i>0;i--) {
			for(int j=COLS-1;j>=0;j--) {
				if(!buttons[i*COLS+j].isVisible()) {
					if(buttons[(i-1)*COLS+j].isVisible()) {
						buttons[i*COLS+j].setIcon(buttons[(i-1)*COLS+j].getIcon());
						mf.setImageTag(i*COLS+j,mf.getImageTag((i-1)*COLS+j));
						buttons[(i-1)*COLS+j].setVisible(false);
						mf.setImageTag((i-1)*COLS+j, -1);
						buttons[i*COLS+j].setVisible(true);
						fillVGap();
					}
				}
			}
		}
	}
	/** 填充横向的空白按钮 */
	public void fillHGap() {
		for(int j=0;j<COLS;j++) {
			if(isAllColumnHidden(j)&&j<COLS-1) {
				for(int k=0;k<ROWS;k++) {
					if(buttons[k*COLS+j+1].isVisible()) {
						buttons[k*COLS+j].setIcon(buttons[k*COLS+j+1].getIcon());
						mf.setImageTag(k*COLS+j, mf.getImageTag(k*COLS+j+1));
						mf.setImageTag(k*COLS+j+1, -1);
						buttons[k*COLS+j+1].setVisible(false);
						buttons[k*COLS+j].setVisible(true);
					}
				}
			}
		}
		boolean flag = false;
		for(int j=0;j<COLS-1;j++) {
			if(isAllColumnHidden(j)&&!isAllColumnHidden(j+1)) {
				flag = true;
				break;
			}
		}
		if(flag) {
			fillHGap();
		}
	}
	/** 判断某一列是否全部为空白按钮 */
	public boolean isAllColumnHidden(int j) {
		boolean flag=true;
		for(int k=ROWS-1;k>=0;k--) {
			 if(buttons[k*COLS+j].isVisible()) {
				 flag = false;
				 break;
			 }
		}
		return flag;
	}
	
	/** 当鼠标点击一个按钮然后search函数通过递归向上下左右不断搜索和点下的按钮图片相同的按钮，直到碰到不同的按钮图标停止 */
	public void search(int i,int j) {
		popedNum = 1;
		needPop = false;
		searchLeft(i,j);
		searchRight(i,j);
		searchUp(i,j);
		searchDown(i,j);
		if(needPop) {
			sound.play();
		}
		
	}
	/** 向左递归搜索如果发现相同图片的按钮就将该按钮消除 */
	public void searchLeft(int i,int j) {
		if(i>=0&&i<=(ROWS-1)&&j>0&&j<=(COLS-1)) {
			if(mf.getImageTag(i*COLS+j-1) == imageName) {
				buttons[i*COLS+j-1].setVisible(false);
				mf.setImageTag(i*COLS+j-1, -1);
				searchLeft(i,j-1);
				searchUp(i,j-1);
				searchDown(i,j-1);
				buttons[i*COLS+j].setVisible(false);
				mf.setImageTag(i*COLS+j, -1);
				needPop = true;
				popedNum++;
			}
		}
	}
	/** 向右递归搜索如果发现相同图片的按钮就将该按钮消除 */
	public void searchRight(int i,int j) {
		if(i>=0&&i<=(ROWS-1)&&j>=0&&j<(COLS-1)) {
			if(mf.getImageTag(i*COLS+j+1) == imageName) {
				buttons[i*COLS+j+1].setVisible(false);
				mf.setImageTag(i*COLS+j+1, -1);
				searchRight(i,j+1);
				searchUp(i,j+1);
				searchDown(i,j+1);
				buttons[i*COLS+j].setVisible(false);
				mf.setImageTag(i*COLS+j, -1);
				needPop = true;
				popedNum++;
			}
		}
	}
	/** 向上递归搜索如果发现相同图片的按钮就将该按钮消除 */
	public void searchUp(int i,int j) {
		if(i>0&&i<=(ROWS-1)&&j>=0&&j<=(COLS-1)) {
			if(mf.getImageTag((i-1)*COLS+j) == imageName) {
				buttons[(i-1)*COLS+j].setVisible(false);
				mf.setImageTag((i-1)*COLS+j, -1);
				searchUp(i-1,j);
				searchLeft(i-1,j);
				searchRight(i-1,j);
				buttons[i*COLS+j].setVisible(false);
				mf.setImageTag(i*COLS+j, -1);
				needPop = true;
				popedNum++;
			}
		}
	}
	/** 向下递归搜索如果发现相同图片的按钮就将该按钮消除 */
	public void searchDown(int i,int j) {
		if(i>=0&&i<(ROWS-1)&&j>=0&&j<=(COLS-1)) {
			if(mf.getImageTag((i+1)*COLS+j) == imageName) {
				buttons[(i+1)*COLS+j].setVisible(false);
				mf.setImageTag((i+1)*COLS+j, -1);
				searchDown(i+1,j);
				searchLeft(i+1,j);
				searchRight(i+1,j);
				buttons[i*COLS+j].setVisible(false);
				mf.setImageTag(i*COLS+j, -1);
				needPop = true;
				popedNum++;
			}
		}
	}
}
