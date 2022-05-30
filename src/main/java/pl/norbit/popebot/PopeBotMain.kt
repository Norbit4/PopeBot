package pl.norbit.popebot

import net.dv8tion.jda.api.JDA
import pl.norbit.popebot.builder.BotBuilder
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.system.exitProcess

private val executorService: ExecutorService = Executors.newFixedThreadPool(2)
private const val prefix: String = "[PopeBot]"

fun start() {

    val botBuilder = BotBuilder.getBuilder(Settings.TOKEN)
    val bot = botBuilder.build()

    waitingTask(bot)
    cmdTask()
    println("$prefix HI! :)")
}

fun waitingTask(bot: JDA){

    executorService.submit{
        while (true){
            val date = LocalDateTime.now()
            val hour = date.hour
            val min = date.minute
            val sec = date.second

            if(hour == Settings.HOUR && min == Settings.MIN && sec == Settings.SEC){

                val voiceChannel = bot.awaitReady().getVoiceChannelById(Settings.CHANNEL_ID)
                val audioManager = voiceChannel?.guild?.audioManager
                audioManager?.openAudioConnection(voiceChannel)

                if (voiceChannel != null) {
                    PlayerManager.playerManager.loadEndPlay(voiceChannel,Settings.PATH)
                }

                Thread.sleep(60_000)
                audioManager?.closeAudioConnection()
            }
            Thread.sleep(1000)
        }
    }
}

fun cmdTask(){

    val scanner = Scanner(System.`in`)

    executorService.submit{
        while (true) {
            when (scanner.nextLine()) {
                ("stop"), "end" -> {
                    println("$prefix bye! :c")
                    executorService.shutdown()
                    exitProcess(0)
                }
                "info" -> {
                    val message: String =
                        "$prefix The track starts at: " + Settings.HOUR + ":" + Settings.MIN + ":" + Settings.SEC
                    println(message)
                }
                else -> {
                    println("$prefix invalid cmd!")
                }
            }

        }
    }
}