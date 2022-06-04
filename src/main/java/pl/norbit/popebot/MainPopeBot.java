package pl.norbit.popebot;

import pl.norbit.popebot.config.ConfigManager;

public class MainPopeBot {

    public static void main(String[] args) {

        ConfigManager.onStart();
        TaskManager.run();
    }
}
