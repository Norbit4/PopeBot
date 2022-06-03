package pl.norbit.popebot.mongo

import org.bson.Document

class ChannelManager constructor(private val database: MongoDB) {

    fun getChannels(): List<ChannelRecord>{
        val channels = database.getChannels()
        val listDocs = ArrayList<Document>()
        val listChannels = ArrayList<ChannelRecord>()

        if(listDocs.isNotEmpty()) {
            channels.forEach(listDocs::add)

            for (listDoc in listDocs) {

                listChannels.add(ChannelRecord(listDoc.getString("CHANNEL_ID"), listDoc.getString("SERVER_ID")))
            }
        }

        return listChannels
    }

    fun addChannel(channel: ChannelRecord){
        database.addChannel(channel)
    }

    fun removeChannel(id: String){
        database.removeChannel("SERVER_ID", id)
    }

    fun closeDBConnection(){
        database.close()
    }
}