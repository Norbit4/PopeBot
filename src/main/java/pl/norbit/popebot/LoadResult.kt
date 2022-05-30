package pl.norbit.popebot

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist
import com.sedmelluq.discord.lavaplayer.track.AudioTrack

class LoadResult constructor(private var musicManager: GuildMusicManager) : AudioLoadResultHandler{

    override fun trackLoaded(track: AudioTrack?) {
        if (track != null) {
            musicManager.trackScheduler.queue(track)
        }
    }

    override fun playlistLoaded(playlist: AudioPlaylist?) {

        if(playlist != null) {
            val tracks: List<AudioTrack> = playlist.tracks
            if(tracks.isNotEmpty()){
                musicManager.trackScheduler.queue(tracks[0])
            }
        }
    }

    override fun noMatches() {
        TODO("Not yet implemented")
    }

    override fun loadFailed(exception: FriendlyException?) {
        TODO("Not yet implemented")
    }
}