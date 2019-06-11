package application;

public class LifeThread implements Runnable {

	public WindowMainController controller;
	private boolean doStop = false;

	
	public LifeThread(WindowMainController class1) {
	this.controller = class1;	
		
	}
	
    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    @Override
    public void run() {
        while(keepRunning()) {
            // keep doing what this thread should do.
            System.out.println("Running");
            controller.DisplayMap()

            try {
                Thread.sleep(3L * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
  }
