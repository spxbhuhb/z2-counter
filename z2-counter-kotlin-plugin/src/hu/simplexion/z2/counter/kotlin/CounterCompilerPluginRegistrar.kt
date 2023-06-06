/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.counter.kotlin

import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterConfigurationKeys.DUMP
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterConfigurationKeys.PLUGIN_LOG_DIR
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterConfigurationKeys.PRINT_DUMPS
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterDumpPoint
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterGenerationExtension
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterOptions
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

/**
 * Registers the extensions into the compiler.
 */
@OptIn(ExperimentalCompilerApi::class)
class CounterCompilerPluginRegistrar(
    val dumpPoints: List<CounterDumpPoint> = emptyList(),
    val printDumps: Boolean = false,
    val pluginLogDir: String? = null
) : CompilerPluginRegistrar() {

    override val supportsK2 = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {

        val options = CounterOptions(
            configuration.get(DUMP) ?: dumpPoints,
            configuration.get(PRINT_DUMPS) ?: printDumps,
            configuration.get(PLUGIN_LOG_DIR) ?: pluginLogDir
        )

        IrGenerationExtension.registerExtension(CounterGenerationExtension(options))
    }

}
