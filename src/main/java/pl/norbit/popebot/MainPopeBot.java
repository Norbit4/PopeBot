package pl.norbit.popebot;

import pl.norbit.popebot.config.ConfigManager;

public class MainPopeBot {

    private static TaskManager taskManager;
    public static void main(String[] args) {

        switch(ConfigManager.onStart()){
            case 0: {
                System.out.println("Created config.json");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            case -1: {
                System.out.println("Set all fields in config.json");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 1: {
                taskManager = new TaskManager();
                taskManager.run();
                break;
            }
        }
    }

    public static TaskManager getTaskManager() {
        return taskManager;
    }
}
