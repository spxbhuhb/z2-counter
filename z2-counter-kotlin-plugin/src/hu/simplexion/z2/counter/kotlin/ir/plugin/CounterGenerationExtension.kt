/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.counter.kotlin.ir.plugin

import hu.simplexion.z2.counter.kotlin.ir.CounterPluginContext
import hu.simplexion.z2.counter.kotlin.ir.CounterTransform
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.util.dump

internal class CounterGenerationExtension(
    val options: CounterOptions
) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {

        CounterPluginContext(
            pluginContext,
            options
        ).apply {

            CounterDumpPoint.Before.dump(this) {
                output("DUMP BEFORE", moduleFragment.dump())
            }

            // --------  transformation  --------

            moduleFragment.accept(CounterTransform(this), null)

            // --------  finishing up  --------

            CounterDumpPoint.After.dump(this) {
                output("DUMP AFTER", moduleFragment.dump())
            }

        }
    }
}

