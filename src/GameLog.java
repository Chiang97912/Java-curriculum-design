import java.io.Serializable;

public class GameLog implements Serializable{
	private int imageTag[];
	private boolean isVisible[];
	private int len;
	private int level;
	private int goal;
	private int score;
	GameLog(int imageTag[],boolean isVisible[],int len,int level,int goal,int score) {
		this.imageTag = imageTag;
		this.isVisible = isVisible;
		this.len = len;
		this.level = level;
		this.goal = goal;
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	public int[] getImageTag() {
		return imageTag;
	}
	public void setImageTag(int[] imageTag) {
		this.imageTag = imageTag;
	}
	public boolean[] getIsVisible() {
		return isVisible;
	}
	public void setIsVisible(boolean[] isVisible) {
		this.isVisible = isVisible;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
}
