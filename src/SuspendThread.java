class SuspendThread implements Runnable {
	public int millis = 500;
	private Object obj;
	public SuspendThread(Object obj) {
		this.obj = obj;
	}

	@Override
	public  void run() {
		synchronized(obj) {
			try {
				obj.wait(millis);
				suspendMethod();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void suspendMethod() {
		
	}
	
}
