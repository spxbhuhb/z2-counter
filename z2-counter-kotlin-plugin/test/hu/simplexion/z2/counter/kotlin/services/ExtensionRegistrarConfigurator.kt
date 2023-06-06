package hu.simplexion.z2.counter.kotlin.services

import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterDumpPoint
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterGenerationExtension
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterOptions
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.EnvironmentConfigurator
import org.jetbrains.kotlin.test.services.TestServices

class ExtensionRegistrarConfigurator(testServices: TestServices) : EnvironmentConfigurator(testServices) {
    override fun CompilerPluginRegistrar.ExtensionStorage.registerCompilerExtensions(
        module: TestModule,
        configuration: CompilerConfiguration
    ) {
        IrGenerationExtension.registerExtension(
            CounterGenerationExtension(
                CounterOptions(
                    CounterDumpPoint.values().toList(),
                    printDumps = true,
                    null
                )
            )
        )
    }
}