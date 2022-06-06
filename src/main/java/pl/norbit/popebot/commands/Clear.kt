package pl.norbit.popebot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import pl.norbit.popebot.MainPopeBot
import pl.norbit.popebot.TaskManager
import pl.norbit.popebot.builder.EmbedBuilder
import java.awt.Color

class Clear : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {

        if(event.name == "clear"){
            val guild = event.guild

            if (guild != null) {
                MainPopeBot.getTaskManager().channelManager.removeChannel(guild.id)
            }
            val embedBuilder = EmbedBuilder.getBuilder("Cleared!")
            embedBuilder.setColor(Color.GREEN)

            event.reply("").addEmbeds(embedBuilder.build()).queue()
        }
    }
}