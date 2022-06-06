package pl.norbit.popebot.builder

import net.dv8tion.jda.api.EmbedBuilder

object EmbedBuilder {

    fun getBuilder(desc: String): EmbedBuilder{
        val embedBuilder = EmbedBuilder()
        embedBuilder.setDescription(desc)
        return embedBuilder
    }
}