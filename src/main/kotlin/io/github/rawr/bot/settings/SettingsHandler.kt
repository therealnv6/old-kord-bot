package io.github.rawr.bot.settings

import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType

object SettingsHandler
{
    const val version = "0.1"

    private val store = DataHandler
        .createStoreType<String, Settings>(DataStoreType.MONGO) {
            this.id = "settings"
        }

    var settings: Settings? = null

    fun loadSettings()
    {
        store.retrieve(version) {
            this.settings = it
        }

        if (this.settings == null)
        {
            this.settings = Settings()
        }
    }

    fun saveSettings()
    {
        store.store(version, settings!!)
    }
}