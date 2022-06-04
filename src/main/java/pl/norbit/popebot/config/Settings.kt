package pl.norbit.popebot.config

class Settings {

    companion object {
        var TOKEN = ""
        var PATH = System.getProperty("user.dir") + "/tracks/jp2.mp3"
        var WATCHING_STATUS = ""
        const val HOUR = 21
        const val MIN = 36
        const val SEC = 58
        var DATABASE = ""
        var HOST = ""
        var PORT = 27017
        var COLLECTION = ""

        fun set(config: ConfigObject){
            TOKEN = config.token
            WATCHING_STATUS = config.watching_status
            DATABASE = config.database
            HOST = config.host
            PORT = config.port
            COLLECTION = config.collection
        }
    }
}