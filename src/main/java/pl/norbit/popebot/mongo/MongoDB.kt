package pl.norbit.popebot.mongo


import com.mongodb.BasicDBObject
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import org.bson.Document
import pl.norbit.popebot.Settings

class MongoDB {
    private val mongoClient: MongoClient
    private val collection: MongoCollection<Document>

    init{
        val mongoURI: String = "mongodb://" + Settings.HOST + ":" + Settings.PORT

        mongoClient = MongoClients.create(mongoURI)
        val db = mongoClient.getDatabase(Settings.DATABASE)
        collection = db.getCollection(Settings.COLLECTION)
    }

    fun getChannels(): FindIterable<Document> {
        return collection.find()
    }

    fun addChannel(channelRecord: ChannelRecord) {

        val dbObject = BasicDBObject()
        dbObject.append("SERVER_ID", channelRecord.serverID)

        val channel = getChannel(dbObject)

        val doc = Document()
            .append("SERVER_ID", channelRecord.serverID)
            .append("CHANNEL_ID", channelRecord.channelID)

        if(channel == null) {

            collection.insertOne(doc)
        } else{

            collection.replaceOne(channel, doc)
        }
    }

    private fun getChannel(obj: BasicDBObject): Document?{

        return collection.find(obj).first()
    }

    fun getChannel(key: String, id: String): Document?{
        val dbObject = BasicDBObject()
        dbObject.append(key, id)

        return collection.find(dbObject).first()
    }

    fun removeChannel(key: String, id: String){
        val dbObject = BasicDBObject()
        dbObject.append(key, id)
        collection.replaceOne(dbObject, Document())
    }

    fun close(){
        mongoClient.close()
    }
}