package gio.blue.greenery.gdscript.ast

import gio.blue.greenery.gdscript.ast.base.Expression
import gio.blue.greenery.gdscript.ast.base.Statement

interface ClassNameStatement : Statement {
    fun getExpression(): Expression
}

