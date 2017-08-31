import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.*;
public class ButtonListener implements ActionListener {
	MainFrame mf;
	JButton[] buttons;
	int i,j;
	int ROWS;
	int COLS;
	int flag = 0;
	int index;
	int imageName;
	boolean needPop = false;
	File file;
	AudioClip sound;
	int popedNum = 1;
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
	public void fillGap() {
		fillVGap();
		fillHGap();
	}
	//fill the gap of vertical
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
	//fill the gap of horizontal
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
