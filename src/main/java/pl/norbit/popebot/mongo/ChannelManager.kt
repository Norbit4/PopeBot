package pl.norbit.popebot.mongo

import com.mongodb.BasicDBObject
import org.bson.Document

class ChannelManager constructor(private val database: MongoDB) {

    fun getChannels(): List<ChannelRecord>{
        val channels = database.getChannels()
        val listDocs = ArrayList<Document>()
        val listChannels = ArrayList<ChannelRecord>()

        channels.forEach(listDocs::add)

        for (listDoc in listDocs) {
            val channelID = listDoc.getString("CHANNEL_ID")
            val serverID = listDoc.getString("SERVER_ID")

            if(channelID != null && serverID != null) {
                listChannels.add(ChannelRecord(channelID, serverID))
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

    fun getChannelID(id: String): String? {

        val dbObject = BasicDBObject()
        dbObject.append("SERVER_ID", id)

        val channel = database.getChannel(dbObject)

        if(channel != null){

            return channel.getString("CHANNEL_ID")
        }
        return null;
    }
}