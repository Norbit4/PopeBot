package pl.norbit.popebot.music

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager

class GuildMusicManager constructor(audioPlayerManager: AudioPlayerManager){

    private var audioPlayer: AudioPlayer = audioPlayerManager.createPlayer()
    var trackScheduler: TrackScheduler = TrackScheduler(audioPlayer)
    var sendHandler: AudioPlayerSendHandler = AudioPlayerSendHandler(audioPlayer)

    init {
        audioPlayer.addListener(trackScheduler)
    }
}