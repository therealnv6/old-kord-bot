package io.github.rawr.bot.user

import dev.kord.core.entity.User
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.DataStoreType

object UserProfileHandler
{
    private val userData = hashMapOf<Long, UserProfile>()

    private val storage = DataHandler
        .createStoreType<Long, UserProfile>(DataStoreType.MONGO) {
            this.id = "profiles"
        }

    fun loadData()
    {
        this.storage.retrieveAll {
            this.userData[it.id] = it
        }
    }

    fun saveData()
    {
        this.userData.forEach {
            storage.store(it.key, it.value)
        }
    }

    fun User.getProfile(): UserProfile
    {
        return userData.computeIfAbsent(this.id.value.toLong()) {
            return@computeIfAbsent UserProfile(this.id.value.toLong())
        }
    }
}