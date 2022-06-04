package pl.norbit.popebot.config

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pl.norbit.popebot.TaskManager
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths

class ConfigManager {

    companion object {
        private const val path = "config.json"

        @JvmStatic
        fun onStart(){
            val file = File(path)
            if (!file.exists()) {
                val gson: Gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .setPrettyPrinting()
                    .create()

                val writer = PrintWriter(FileWriter(path))
                val config = ConfigObject("", "", "", "", 27017, "")

                writer.write(gson.toJson(config))
                writer.append()
                writer.close()
            } else {
                getConfig()
            }
        }

        private fun getConfig() {
            val gson = Gson()

            val reader = Files.newBufferedReader(Paths.get(path))
            val configObject =  gson.fromJson(reader, ConfigObject::class.java)
            reader.close()

            val token = configObject.token
            val host = configObject.host
            val database = configObject.database
            val collection = configObject.collection
            val watchingStatus = configObject.watching_status

            if(token.isNotEmpty() && host.isNotEmpty() && database.isNotEmpty() && collection.isNotEmpty()
                && watchingStatus.isNotEmpty()){
                Settings.set(configObject)
            }else{
                println("Set all fields in config.json")
                Thread.sleep(2000)
                TaskManager.closeAll()
            }
        }
    }
}