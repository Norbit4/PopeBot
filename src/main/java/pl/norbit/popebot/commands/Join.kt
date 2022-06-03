package pl.norbit.popebot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import pl.norbit.popebot.TaskManager

class Join: ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {

        if(event.name == "join"){
            val guild = event.guild

            if (guild != null) {
                val channelID = TaskManager.channelManager.getChannelID(guild.id)

                if(channelID != null) {
                    TaskManager.playMusic(channelID)
                    event.reply("Joined!").queue()
                } else{
                    event.reply("You need to set the channel!").queue()
                }
            }
        }
    }
}
