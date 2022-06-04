package pl.norbit.popebot.config

class ConfigObject constructor(val token: String,
                               val watching_status: String,
                               val database: String,
                               val host: String,
                               val port: Int,
                               val collection: String
                                ){
}