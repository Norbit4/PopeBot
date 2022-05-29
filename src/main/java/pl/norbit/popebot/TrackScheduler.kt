package pl.norbit.popebot

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter
import com.sedmelluq.discord.lavaplayer.track.AudioTrack
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason
import java.util.concurrent.LinkedBlockingQueue

class TrackScheduler constructor(private val audioPlayer: AudioPlayer): AudioEventAdapter() {

    private val blockingQueue: LinkedBlockingQueue<AudioTrack> = LinkedBlockingQueue()

    fun queue(track: AudioTrack){

        if(!audioPlayer.startTrack(track, true)){
            blockingQueue.offer(track)
        }
    }

    private fun nextTrack(){
        audioPlayer.startTrack(blockingQueue.poll(), false)
    }

    override fun onTrackEnd(player: AudioPlayer?, track: AudioTrack?, endReason: AudioTrackEndReason?) {
        if(endReason?.mayStartNext == true){
            nextTrack()
        }
    }
}