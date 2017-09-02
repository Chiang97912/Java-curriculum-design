import javax.swing.JLabel;

public class TimerThread extends JLabel implements Runnable{
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
