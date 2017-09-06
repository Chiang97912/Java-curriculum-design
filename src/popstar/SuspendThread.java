package popstar;
/**
 * ͬ����ʱ������ʾ��Ϣ��
 * @author Peter
 *
 */
public class SuspendThread implements Runnable {
	/** ����ȴ�ʱ�� */
	public int millis = 500;
	private Object obj;
	public SuspendThread(Object obj) {
		this.obj = obj;
	}

	@Override
	public  void run() {
		synchronized(obj) { //������ �ǵ�ǰ�������������ڸ��߳���
			try {
				obj.wait(millis); //obj����ȴ�millis����
				suspendMethod(); //��ͣһ��ʱ�����ִ�и÷���
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/** ����̳и����Ҫ����һ��ʱ���ִ��һ�δ����Ҫ��дsuspendMethod���������������ŵ��÷����� */
	public void suspendMethod() {
		
	}
	
}
