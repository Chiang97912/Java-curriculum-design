package popstar.timemode;
import javax.swing.JLabel;

/** 
 * 同步倒计时类
 * @author Peter
 *
 */
public class TimerThread extends JLabel implements Runnable{
	/** 父窗口 */
	public TimeModeMainFrame mf;
	public TimerThread(TimeModeMainFrame mf) {
		this.mf = mf;
	}

	@Override
	public void run() {
		while(mf.time>0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mf.updateTime();
			mf.judge();
		}
	}
}
