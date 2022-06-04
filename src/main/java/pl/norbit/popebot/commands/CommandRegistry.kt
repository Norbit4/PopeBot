package pl.norbit.popebot.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.OptionType

class CommandRegistry {

    companion object{
        fun register(bot: JDA){

            val guildById = bot.awaitReady().getGuildById("691008107345739826")

            //channel cmd
            guildById?.upsertCommand("channel", "add channel to join list")
                ?.addOption(OptionType.STRING, "channelid", "ch", true)
                ?.setDefaultEnabled(false)
                ?.queue()


            bot.upsertCommand("channel","add channel to join list")
                .addOption(OptionType.STRING,"channelid","ch", true)
                .queue()

            //clear cmd
            guildById?.upsertCommand("clear", "remove channel from join list")
                ?.setDefaultEnabled(false)
                ?.queue()

            bot.upsertCommand("clear","remove channel from join list")
                .queue()

            //join cmd
            guildById?.upsertCommand("join", "join the bot to the set channel")
                ?.setDefaultEnabled(false)
                ?.queue()

            bot.upsertCommand("join","join the bot to the set channel")
                .queue()

            bot.updateCommands().queue()
        }
    }
}