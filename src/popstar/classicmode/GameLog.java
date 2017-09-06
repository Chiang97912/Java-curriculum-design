package popstar.classicmode;
import java.io.Serializable;
/**
 * 游戏记录类
 * @author Peter
 *
 */
public class GameLog implements Serializable{
	/** 按钮图标名数组 */
	private int imageTag[];
	/** 按钮的显隐性数组 */
	private boolean isVisible[];
	/** 按钮数组长度 */
	private int len;
	/** 游戏的等级 */
	private int level;
	/** 游戏的目标分数 */
	private int goal;
	/** 当前获得分数 */
	private int score;
	/**
	 * 
	 * @param imageTag 按钮的图标数组
	 * @param isVisible 按钮显隐性数组
	 * @param len 按钮数组的长度
	 * @param level 游戏的等级
	 * @param goal 游戏的目标分数
	 * @param score 游戏当前得分
	 */
	GameLog(int imageTag[],boolean isVisible[],int len,int level,int goal,int score) {
		this.imageTag = imageTag;
		this.isVisible = isVisible;
		this.len = len;
		this.level = level;
		this.goal = goal;
		this.score = score;
	}
	/** 过去游戏等级 */
	public int getLevel() {
		return level;
	}
	/** 设置游戏等级 */
	public void setLevel(int level) {
		this.level = level;
	}
	/** 获取游戏的目标分数 */
	public int getGoal() {
		return goal;
	}
	/** 设置游戏的目标分数 */
	public void setGoal(int goal) {
		this.goal = goal;
	}
	/** 获取游戏的当前分数 */
	public int getScore() {
		return score;
	}
	/** 设置游戏当前的分数 */
	public void setScore(int score) {
		this.score = score;
	}
	/** 获取imageTag数组 */
	public int[] getImageTag() {
		return imageTag;
	}
	/** 设置imageTag数组 */
	public void setImageTag(int[] imageTag) {
		this.imageTag = imageTag;
	}
	/** 获取MainFrame中的isVisible数组 */
	public boolean[] getIsVisible() {
		return isVisible;
	}
	/** 设置MainFrame中的isVisible数组 */
	public void setIsVisible(boolean[] isVisible) {
		this.isVisible = isVisible;
	}
	/** 获取数组buttons的长度 */
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
}
