package pl.norbit.popebot

import pl.norbit.popebot.builder.BotBuilder
import pl.norbit.popebot.commands.CommandRegistry
import pl.norbit.popebot.config.Settings
import pl.norbit.popebot.mongo.ChannelManager
import pl.norbit.popebot.mongo.MongoDB
import pl.norbit.popebot.music.PlayerManager
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.system.exitProcess

class TaskManager {
        val channelManager: ChannelManager = ChannelManager(MongoDB())
        private val executorService: ExecutorService = Executors.newCachedThreadPool()
        private val botBuilder = BotBuilder.getBuilder(Settings.TOKEN)
        private var bot = botBuilder.build()
        private val prefix: String = "[PopeBot]"


        fun run() {
            CommandRegistry.register(bot)

            waitingTask()
            cmdTask()
            println("$prefix HI! :)")
        }

        private fun waitingTask() {

            executorService.submit {
                while (true) {
                    val date = LocalDateTime.now()
                    val hour = date.hour
                    val min = date.minute
                    val sec = date.second

                    if (hour == Settings.HOUR && min == Settings.MIN && sec == Settings.SEC) {

                        playMusicAll()
                    }
                    Thread.sleep(1000)
                }
            }
        }


        private fun playMusicAll() {

            println(">>>[21:37]<<<")
            println("")
            println(
                "$prefix Pann kiedys stanal na brzeguuu [...] \n" +
                        "O Panie bo ty na mnie spojrzales! Twoje usta dzis wyrzekly me imie..."
            )
            println("")
            println(">>>[21:37]<<<")

            val channels = channelManager.getChannels()

            if (channels.isNotEmpty()) {
                for (channel in channels) {

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
        }

        fun playMusic(channelID: String){
            executorService.submit {
                val voiceChannel = bot.awaitReady().getVoiceChannelById(channelID)
                val audioManager = voiceChannel?.guild?.audioManager
                audioManager?.openAudioConnection(voiceChannel)

                if (voiceChannel != null) {
                    PlayerManager.playerManager.loadEndPlay(voiceChannel, Settings.PATH)
                }

                Thread.sleep(60_000)
                audioManager?.closeAudioConnection()
            }
        }

        private fun cmdTask() {

            val scanner = Scanner(System.`in`)

            executorService.submit {
                while (true) {
                    when (scanner.nextLine()) {
                        "stop", "end" -> {
                            println("$prefix bye! :c")

                            closeAll()
                        }
                        "info" -> {
                            val message: String =
                                "$prefix The track starts at: " + Settings.HOUR + ":" + Settings.MIN + ":" + Settings.SEC
                            println(message)
                        }
                        "channels", "c" ->{
                            val channels = channelManager.getChannels()

                            if(channels.isNotEmpty()) {
                                val channelsAmount = channels.size
                                val message = "$prefix Register channels: $channelsAmount"
                                println(message)
                                println("")

                                for ((i, channel) in channels.withIndex()) {
                                    val channelID = channel.channelID
                                    val serverID = channel.serverID

                                    println("Channel $i")
                                    println("chID: $channelID servID: $serverID")
                                    println("")
                                }
                            }else{
                                println("$prefix Empty list")
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

        private fun closeAll(){
            bot.shutdownNow()
            channelManager.closeDBConnection()
            executorService.shutdownNow()
            exitProcess(0)
        }
}
