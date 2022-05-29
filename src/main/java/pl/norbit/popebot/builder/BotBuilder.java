package pl.norbit.popebot.builder;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class BotBuilder {

    public static JDABuilder getBuilder(String token){

        return JDABuilder.createDefault(token)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.watching("KIDS PORNO"));
    }

}
