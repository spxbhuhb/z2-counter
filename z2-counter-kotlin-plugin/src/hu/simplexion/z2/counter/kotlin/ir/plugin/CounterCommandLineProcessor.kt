/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.counter.kotlin.ir.plugin

import hu.simplexion.z2.counter.kotlin.OPTION_NAME_DUMP_POINT
import hu.simplexion.z2.counter.kotlin.OPTION_NAME_PLUGIN_LOG_DIR
import hu.simplexion.z2.counter.kotlin.OPTION_NAME_PRINT_DUMPS
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.compiler.plugin.CliOptionProcessingException
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
class CounterCommandLineProcessor : org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor {
    companion object {

        val OPTION_DUMP = CliOption(
            OPTION_NAME_DUMP_POINT, "string", "Dump data at specified points of plugin run (${CounterDumpPoint.optionValues().joinToString { ", " }}).",
            required = false, allowMultipleOccurrences = true
        )
        val OPTION_PRINT_DUMPS = CliOption(
            OPTION_NAME_PRINT_DUMPS, "boolean", "Use println for output instead of the compiler logging framework",
            required = false, allowMultipleOccurrences = false
        )
        val OPTION_PLUGIN_LOG_DIR = CliOption(
            OPTION_NAME_PLUGIN_LOG_DIR, "string", "Save plugin output into a file in this directory.",
            required = false, allowMultipleOccurrences = false
        )
    }

    override val pluginId = "z2-counter"

    override val pluginOptions = listOf(
        OPTION_DUMP,
        OPTION_PRINT_DUMPS,
        OPTION_PLUGIN_LOG_DIR
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option) {
            OPTION_DUMP -> configuration.appendList(CounterConfigurationKeys.DUMP, value.toDumpPoint())
            OPTION_PRINT_DUMPS -> configuration.put(CounterConfigurationKeys.PRINT_DUMPS, value.toBooleanStrictOrNull() ?: false)
            OPTION_PLUGIN_LOG_DIR -> configuration.put(CounterConfigurationKeys.PLUGIN_LOG_DIR, value)
            else -> throw CliOptionProcessingException("Unknown option: ${option.optionName}")
        }
    }

    private fun String.toDumpPoint(): CounterDumpPoint =
        CounterDumpPoint.fromOption(this) ?: throw CliOptionProcessingException("Unknown dump point: $this")

}