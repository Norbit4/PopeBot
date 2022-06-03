package pl.norbit.popebot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import pl.norbit.popebot.MainPopeBot
import pl.norbit.popebot.TaskManager
import pl.norbit.popebot.mongo.ChannelRecord

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
                            TaskManager.channelManager.addChannel(ChannelRecord(channelID, guild.id))
                        }

                        event.reply("Channel added!").queue()
                    }else{
                        event.reply("Channel with this id does not exist!").queue()
                    }
                }else{
                    event.reply("Invalid format! Id must be numeric!").queue()
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