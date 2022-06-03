package pl.norbit.popebot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import pl.norbit.popebot.TaskManager

class Clear : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {

        if(event.name == "clear"){
            val guild = event.guild

            if (guild != null) {
                TaskManager.channelManager.removeChannel(guild.id)
            }
            event.reply("Cleared!").queue()
        }
    }
}