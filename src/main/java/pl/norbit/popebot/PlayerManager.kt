package pl.norbit.popebot

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.VoiceChannel

class PlayerManager {

    companion object{
        var playerManager: PlayerManager = PlayerManager()
    }

    private var musicManagers: Map<Long, GuildMusicManager> = HashMap()
    private var audioPlayerManager: AudioPlayerManager = DefaultAudioPlayerManager()

    init {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager)
        AudioSourceManagers.registerLocalSource(audioPlayerManager)
    }

    private fun getMusicManager(guild: Guild): GuildMusicManager{
        if (!musicManagers.containsKey(guild.idLong)) {
            val guildMusicManager = GuildMusicManager(audioPlayerManager)
            guild.audioManager.sendingHandler = guildMusicManager.sendHandler
            return guildMusicManager;
        }
        return musicManagers.getValue(guild.idLong)
    }

    fun loadEndPlay(voiceChannel: VoiceChannel, trackURL: String){
        val musicManager = getMusicManager(voiceChannel.guild)

        audioPlayerManager.loadItemOrdered(musicManager, trackURL,  LoadResult(musicManager))
    }
}
