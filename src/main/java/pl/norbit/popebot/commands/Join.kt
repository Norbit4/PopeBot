package pl.norbit.popebot.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import pl.norbit.popebot.MainPopeBot
import pl.norbit.popebot.TaskManager
import pl.norbit.popebot.builder.EmbedBuilder
import java.awt.Color

class Join: ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {

        if(event.name == "join"){
            val guild = event.guild

            if (guild != null) {
                val channelID = MainPopeBot.getTaskManager().channelManager.getChannelID(guild.id)

                if(channelID != null) {
                    MainPopeBot.getTaskManager().playMusic(channelID)
                    val embedBuilder = EmbedBuilder.getBuilder("Joined!")
                    embedBuilder.setColor(Color.GREEN)

                    event.reply("").addEmbeds(embedBuilder.build()).queue()
                } else{
                    val embedBuilder = EmbedBuilder.getBuilder("You need to set the channel!")
                    embedBuilder.setColor(Color.RED)

                    event.reply("").addEmbeds(embedBuilder.build()).queue()
                }
            }
        }
    }
}
