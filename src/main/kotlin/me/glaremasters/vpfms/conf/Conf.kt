package me.glaremasters.vpfms.conf

import ch.jalu.configme.SettingsManagerImpl
import ch.jalu.configme.configurationdata.ConfigurationDataBuilder
import ch.jalu.configme.migration.PlainMigrationService
import ch.jalu.configme.resource.YamlFileResource
import me.glaremasters.vpfms.conf.sections.PluginSettings
import java.io.File

internal class Conf(file: File) : SettingsManagerImpl(YamlFileResource(file.toPath()), ConfigurationDataBuilder.createConfiguration(SECTION), PlainMigrationService()) {

    private companion object {
        private val SECTION = listOf(PluginSettings::class.java)
    }
}