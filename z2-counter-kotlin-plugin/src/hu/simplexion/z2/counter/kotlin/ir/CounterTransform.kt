/*
 * Copyright © 2020-2021, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */
package hu.simplexion.z2.counter.kotlin.ir

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.*
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.name
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.fileEntry
import org.jetbrains.kotlin.ir.util.statements

class CounterTransform(
    private val counterContext: CounterPluginContext
) : IrElementTransformerVoidWithContext() {

    val irFactory = counterContext.irContext.irFactory

    override fun visitFunctionNew(declaration: IrFunction): IrStatement {
        val body = declaration.body
        if (body != null && declaration.startOffset >= 0) {
            declaration.body = irCounter(declaration, body)
        }
        return super.visitFunctionNew(declaration)
    }

    //  CALL 'public final fun increment (fileName: kotlin.String, line: kotlin.Int, offset: kotlin.Int): kotlin.Unit declared in hu.simplexion.z2.counter.runtime.Counters' type=kotlin.Unit origin=null
    //        $this: GET_OBJECT 'CLASS IR_EXTERNAL_DECLARATION_STUB OBJECT name:Counters modality:FINAL visibility:public superTypes:[kotlin.Any]' type=hu.simplexion.z2.counter.runtime.Counters
    //        fileName: CONST String type=kotlin.String value="a.kt"
    //        line: CONST Int type=kotlin.Int value=12
    //        offset: CONST Int type=kotlin.Int value=12

    private fun irCounter(
        function: IrFunction,
        body: IrBody
    ): IrBlockBody {
        return DeclarationIrBuilder(counterContext.irContext, function.symbol).irBlockBody {
            + irCall(
                counterContext.incrementFunction
            ).apply {
                dispatchReceiver = irGetObject(counterContext.counterObject)
                putValueArgument(0, irString(function.file.name))
                putValueArgument(1, irString(function.name.asString()))
                putValueArgument(2, irInt(function.fileEntry.getLineNumber(function.startOffset) + 1))
                putValueArgument(3, irInt(function.fileEntry.getColumnNumber(function.startOffset) + 1))
            }
            for (statement in body.statements) + statement
        }
    }

}
