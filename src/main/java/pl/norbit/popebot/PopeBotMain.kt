package pl.norbit.popebot

import net.dv8tion.jda.api.JDA
import pl.norbit.popebot.builder.BotBuilder
import java.time.LocalDateTime

fun main() {

    val botBuilder = BotBuilder.getBuilder(Settings.TOKEN)
    val bot = botBuilder.build()

    waitingHour(bot)
}

fun waitingHour(bot: JDA){

    while (true){

        val date = LocalDateTime.now()
        val hour = date.hour
        val min = date.minute
        val sec = date.second
        //println("$hour $min $sec")

        if(hour == Settings.HOUR && min == Settings.MIN && sec == Settings.SEC){

            val voiceChannel = bot.awaitReady().getVoiceChannelById(Settings.CHANNEL_ID)
            val audioManager = voiceChannel?.guild?.audioManager
            audioManager?.openAudioConnection(voiceChannel)

            if (voiceChannel != null) {
                PlayerManager.playerManager.loadEndPlay(voiceChannel,Settings.MUSIC_URL)
            }

            Thread.sleep(60_000)
            audioManager?.closeAudioConnection()
        }
        Thread.sleep(1000)
    }
}