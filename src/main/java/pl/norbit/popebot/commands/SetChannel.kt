package pl.norbit.popebot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import pl.norbit.popebot.MainPopeBot
import pl.norbit.popebot.builder.EmbedBuilder
import pl.norbit.popebot.mongo.ChannelRecord
import java.awt.Color

class SetChannel : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {

        if(event.name == "channel"){

            if(event.options.isNotEmpty()) {

                val channelID = event.getOption("channelid")?.asString
                if (channelID != null && checkIdIsNumeric(channelID)) {

                    val voiceChannelById = event.jda.awaitReady().getVoiceChannelById(channelID)

                    if(voiceChannelById != null){
                        val guild = event.guild

                        if (guild != null) {
                            MainPopeBot.getTaskManager().channelManager.addChannel(ChannelRecord(channelID, guild.id))
                        }

                        val embedBuilder = EmbedBuilder.getBuilder("Channel added")
                        embedBuilder.setColor(Color.GREEN)

                        event.reply("").addEmbeds(embedBuilder.build()).queue()
                    }else{
                        val embedBuilder = EmbedBuilder.getBuilder("Channel with this id does not exist!")
                        embedBuilder.setColor(Color.RED)

                        event.reply("").addEmbeds(embedBuilder.build()).queue()
                    }
                }else{
                    val embedBuilder = EmbedBuilder.getBuilder("Invalid format! Id must be numeric!")
                    embedBuilder.setColor(Color.RED)

                    event.reply("").addEmbeds(embedBuilder.build()).queue()
                }
            }
        }

    }
    private fun checkIdIsNumeric(id: String): Boolean{

        if(id.toLongOrNull() != null){

            return true
        }
        return false
    }

}