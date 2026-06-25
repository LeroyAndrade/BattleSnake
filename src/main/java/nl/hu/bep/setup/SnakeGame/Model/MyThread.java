package nl.hu.bep.setup.SnakeGame.Model;

public class MyThread extends Thread {
    private String text;

    public MyThread(String s) {
        text = s;
    }

    // overridden method run() from superclass Thread
    public void run() {
        try {
            // Kleine pauze om te laten zien dat deze thread apart draait
            Thread.sleep(100);
        } catch (InterruptedException ioe) {
            ioe.printStackTrace();
        }

        System.out.println(text);
    }
}