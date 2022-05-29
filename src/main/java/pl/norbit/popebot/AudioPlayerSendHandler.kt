package pl.norbit.popebot

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame
import net.dv8tion.jda.api.audio.AudioSendHandler
import java.nio.Buffer
import java.nio.ByteBuffer

class AudioPlayerSendHandler constructor(private var audioPlayer: AudioPlayer): AudioSendHandler{

    private var byteBuffer: ByteBuffer = ByteBuffer.allocate(1024)
    private var frame: MutableAudioFrame = MutableAudioFrame()

    init{
        frame.setBuffer(byteBuffer)
    }

    override fun canProvide(): Boolean {
        return audioPlayer.provide(frame)
    }

    override fun provide20MsAudio(): ByteBuffer {
        val buffer: Buffer = byteBuffer.flip() as Buffer
        return buffer as ByteBuffer
    }

    override fun isOpus(): Boolean {
        return true
    }
}