package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.type.ClassType

/**
 * Created by dageev
 * on 11/2/16.
 */
class FieldAccess(val identifier: String, val classType: ClassType) : Expression(classType)