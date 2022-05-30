package pl.norbit.popebot.builder

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import pl.norbit.popebot.Settings

object BotBuilder {
    fun getBuilder(token: String?): JDABuilder {
        return JDABuilder.createDefault(token)
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .setActivity(Activity.watching(Settings.WATCHING_STATUS))
    }
}