package popstar;
/**
 * 同步计时消除提示信息类
 * @author Peter
 *
 */
public class SuspendThread implements Runnable {
	/** 对象等待时间 */
	public int millis = 500;
	private Object obj;
	public SuspendThread(Object obj) {
		this.obj = obj;
	}

	@Override
	public  void run() {
		synchronized(obj) { //对象锁 是当前操作对象锁死在该线程中
			try {
				obj.wait(millis); //obj对象等待millis毫秒
				suspendMethod(); //暂停一段时间后再执行该方法
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/** 子类继承该类后要想在一定时间后执行一段代码就要重写suspendMethod方法，并将代码块放到该方法中 */
	public void suspendMethod() {
		
	}
	
}
