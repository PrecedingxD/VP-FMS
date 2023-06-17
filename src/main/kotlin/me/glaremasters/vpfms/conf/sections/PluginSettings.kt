package me.glaremasters.vpfms.conf.sections

import ch.jalu.configme.Comment
import ch.jalu.configme.SettingsHolder
import ch.jalu.configme.properties.Property
import ch.jalu.configme.properties.PropertyInitializer.newProperty

internal object PluginSettings : SettingsHolder {

    @JvmField
    @Comment("Your FMCS Server ID")
    val SERVER_ID: Property<String> = newProperty("server_id", "123456789")

    @JvmField
    @Comment("FMCS API URL")
    val API_URL: Property<String> = newProperty("api_url", "")

    @JvmField
    @Comment("Enable Debug Messages?")
    val DEBUG: Property<Boolean> = newProperty("debug", false)

    @JvmField
    @Comment("Would you like to require a user's inventory to not be full before voting? (Highly Suggested)")
    val INVENTORY_CHECK: Property<Boolean> = newProperty("inventory_check", true)

    @JvmField
    val VOTED_RECENTLY: Property<String> = newProperty("messages.voted_recently", "&cYou have already voted recently!")

    @JvmField
    val VOTE_SUCCESS: Property<String> = newProperty("messages.vote_success", "&aThank you for voting!")

    @JvmField
    val INVENTORY_MESSAGE: Property<String> = newProperty("messages.inventory_message", "&cYour inventory is full! Please clear some space before voting!")
}