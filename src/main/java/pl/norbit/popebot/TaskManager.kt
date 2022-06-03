package pl.norbit.popebot

import net.dv8tion.jda.api.JDA
import pl.norbit.popebot.builder.BotBuilder
import pl.norbit.popebot.commands.CommandRegistry
import pl.norbit.popebot.mongo.ChannelManager
import pl.norbit.popebot.mongo.MongoDB
import pl.norbit.popebot.music.PlayerManager
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.system.exitProcess

class TaskManager {

    companion object {
        val channelManager: ChannelManager = ChannelManager(MongoDB())
        private val executorService: ExecutorService = Executors.newCachedThreadPool()
        private const val prefix: String = "[PopeBot]"

        @JvmStatic
        fun run() {
            val botBuilder = BotBuilder.getBuilder(Settings.TOKEN)
            val bot = botBuilder.build()

            CommandRegistry.register(bot)

            bot.updateCommands().queue()

            waitingTask(bot)
            cmdTask(bot)
            println("$prefix HI! :)")

            playMusic(bot)
        }

        private fun waitingTask(bot: JDA) {

            executorService.submit {
                while (true) {
                    val date = LocalDateTime.now()
                    val hour = date.hour
                    val min = date.minute
                    val sec = date.second

                    if (hour == Settings.HOUR && min == Settings.MIN && sec == Settings.SEC) {

                        playMusic(bot)
                    }
                    Thread.sleep(1000)
                }
            }
        }

        @JvmStatic
        fun playMusic(bot: JDA) {

            println(">>>[21:37]<<<")
            println("")
            println("$prefix Pann kiedys stanal na brzeguuu [...] \n" +
                    "O Panie bo ty na mnie spojrzales! Twoje usta dzis wyrzekly me imie...")
            println("")
            println(">>>[21:37]<<<")

            for (channel in channelManager.getChannels()) {

                executorService.submit {
                    val voiceChannel = bot.awaitReady().getVoiceChannelById(channel.channelID)
                    val audioManager = voiceChannel?.guild?.audioManager
                    audioManager?.openAudioConnection(voiceChannel)

                    if (voiceChannel != null) {
                        PlayerManager.playerManager.loadEndPlay(voiceChannel, Settings.PATH)
                    }

                    Thread.sleep(60_000)
                    audioManager?.closeAudioConnection()
                }
            }
        }

        private fun cmdTask(bot: JDA) {

            val scanner = Scanner(System.`in`)

            executorService.submit {
                while (true) {
                    when (scanner.nextLine()) {
                        "stop", "end" -> {
                            println("$prefix bye! :c")

                            bot.shutdownNow()
                            channelManager.closeDBConnection()
                            executorService.shutdownNow()
                            exitProcess(0)
                        }
                        "info" -> {
                            val message: String =
                                "$prefix The track starts at: " + Settings.HOUR + ":" + Settings.MIN + ":" + Settings.SEC
                            println(message)
                        }
                        "channels", "c" ->{
                            val channels = channelManager.getChannels()
                            val channelsAmount = channels.size
                            val message = "$prefix Register channels: $channelsAmount"
                            println(message)
                            println("")

                            for ((i, channel) in channelManager.getChannels().withIndex()) {
                                val channelID = channel.channelID
                                val serverID = channel.serverID

                                println("Channel $i")
                                println("chID: $channelID servID: $serverID")
                                println("")
                            }

                        }
                        "help", "?" -> {
                            println("$prefix Commands:")
                            println("stop - stop server")
                            println("channels - register channels")
                            println("info - get hour")
                        }
                        else -> {
                            println("$prefix invalid cmd!")
                        }
                    }
                }
            }
        }
    }
}