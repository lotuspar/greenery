package gio.blue.greenery.gdscript.syntax

import com.intellij.lang.SyntaxTreeBuilder
import gio.blue.greenery.gdscript.lexer.TokenLibrary

class OpExSyntaxBuildContextParser(context: SyntaxParserBuildContext, builder: SyntaxTreeBuilder) :
    SyntaxParserBuildContextAssociate(
        context,
        builder
    ) {

    fun parse(): Boolean {
        if (TokenLibrary.UNARY_OPERATORS.contains(tokenType)) return parseFromUnaryStart()
        if (TokenLibrary.NEGATION_OPERATORS.contains(tokenType)) return parseFromNotStart()

        return false
    }

    /**
     * Unary start
     *
     * ... (here! unary operator) (expression)
     */
    private fun parseFromUnaryStart(): Boolean {
        assertSet(TokenLibrary.UNARY_OPERATORS)

        val marker = mark()

        next()

        if (!context.expressions.parse()) {
            marker.error(message("SYNTAX.unary.invalid.target"))
            return false
        }

        marker.done(SyntaxLibrary.PREFIX_EXPRESSION)
        return true
    }

    /**
     * Not start
     *
     * ... (here! not keyword or !) (expression)
     */
    private fun parseFromNotStart(): Boolean {
        assertSet(TokenLibrary.NEGATION_OPERATORS)

        val marker = mark()

        next()

        if (!context.expressions.parse()) {
            marker.error(message("SYNTAX.negate.invalid.target"))
            return false
        }

        marker.done(SyntaxLibrary.PREFIX_EXPRESSION)
        return true
    }
}