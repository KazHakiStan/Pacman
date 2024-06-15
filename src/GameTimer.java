
public class GameTimer implements Runnable {
    private volatile int totalTime = 0;
    private volatile boolean interrupted = false;
    private volatile boolean running = true;

    public GameTimer() {
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (interrupted) {
                    interrupt();
                } 
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            increment();
        }
    }

    public synchronized  int getTotalTime() {
        return this.totalTime;
    }

    public synchronized void increment() {
        this.totalTime += 1;
    }

    protected void stopTimer() {
        running = false;
    }

    protected void resumeTimer() {
        running = true;
    }

    protected void interrupt() {
        this.interrupted = true;
        this.stopTimer();
    }

    @Override
    public String toString() {
        return "Timer: " + totalTime;
    }
    
}
