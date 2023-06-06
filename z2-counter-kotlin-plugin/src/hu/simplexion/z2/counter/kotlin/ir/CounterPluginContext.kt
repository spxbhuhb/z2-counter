/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.counter.kotlin.ir

import hu.simplexion.z2.counter.kotlin.RUNTIME_OBJECT
import hu.simplexion.z2.counter.kotlin.RUNTIME_PACKAGE
import hu.simplexion.z2.counter.kotlin.ir.plugin.CounterOptions
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.util.functions
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.appendText
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile

class CounterPluginContext(
    val irContext: IrPluginContext,
    options: CounterOptions,
) {
    val dumpPoints = options.dumpPoints
    val printDumps = options.printDumps

    val pluginLogDir: Path? = options.pluginLogDir?.let { Paths.get(options.pluginLogDir).also { it.createDirectories() } }
    val pluginLogTimestamp: String = DateTimeFormatter.ofPattern("yyyyMMdd'-'HHmmss").format(LocalDateTime.now())
    val pluginLogFile: Path? = pluginLogDir?.resolve("counter-log-$pluginLogTimestamp.txt").also { it?.createFile() }

    val counterObject =
        checkNotNull(irContext.referenceClass(ClassId(FqName(RUNTIME_PACKAGE), Name.identifier(RUNTIME_OBJECT)))) {
            "Missing counter object. Maybe the gradle dependency on \"hu.simplexion.z2:z2-counter\" is missing."
        }

    val incrementFunction =
        checkNotNull(counterObject.functions.firstOrNull { it.owner.name.asString() == "increment" }) {
            "Missing increment function. Most probably a plugin error."
        }

    fun output(title: String, content: String, declaration: IrDeclaration? = null) {

        val longTitle = "\n\n====  $title  ================================================================\n"

        pluginLogFile?.appendText("$longTitle\n\n$content")

        if (printDumps) {
            println(longTitle)
            println(content)
        }
    }
}

