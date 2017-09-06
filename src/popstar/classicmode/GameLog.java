package popstar.classicmode;
import java.io.Serializable;
/**
 * ��Ϸ��¼��
 * @author Peter
 *
 */
public class GameLog implements Serializable{
	/** ��ťͼ�������� */
	private int imageTag[];
	/** ��ť������������ */
	private boolean isVisible[];
	/** ��ť���鳤�� */
	private int len;
	/** ��Ϸ�ĵȼ� */
	private int level;
	/** ��Ϸ��Ŀ����� */
	private int goal;
	/** ��ǰ��÷��� */
	private int score;
	/**
	 * 
	 * @param imageTag ��ť��ͼ������
	 * @param isVisible ��ť����������
	 * @param len ��ť����ĳ���
	 * @param level ��Ϸ�ĵȼ�
	 * @param goal ��Ϸ��Ŀ�����
	 * @param score ��Ϸ��ǰ�÷�
	 */
	GameLog(int imageTag[],boolean isVisible[],int len,int level,int goal,int score) {
		this.imageTag = imageTag;
		this.isVisible = isVisible;
		this.len = len;
		this.level = level;
		this.goal = goal;
		this.score = score;
	}
	/** ��ȥ��Ϸ�ȼ� */
	public int getLevel() {
		return level;
	}
	/** ������Ϸ�ȼ� */
	public void setLevel(int level) {
		this.level = level;
	}
	/** ��ȡ��Ϸ��Ŀ����� */
	public int getGoal() {
		return goal;
	}
	/** ������Ϸ��Ŀ����� */
	public void setGoal(int goal) {
		this.goal = goal;
	}
	/** ��ȡ��Ϸ�ĵ�ǰ���� */
	public int getScore() {
		return score;
	}
	/** ������Ϸ��ǰ�ķ��� */
	public void setScore(int score) {
		this.score = score;
	}
	/** ��ȡimageTag���� */
	public int[] getImageTag() {
		return imageTag;
	}
	/** ����imageTag���� */
	public void setImageTag(int[] imageTag) {
		this.imageTag = imageTag;
	}
	/** ��ȡMainFrame�е�isVisible���� */
	public boolean[] getIsVisible() {
		return isVisible;
	}
	/** ����MainFrame�е�isVisible���� */
	public void setIsVisible(boolean[] isVisible) {
		this.isVisible = isVisible;
	}
	/** ��ȡ����buttons�ĳ��� */
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
}
