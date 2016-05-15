package ru.dageev.compiler.domain

/**
 * Created by dageev
 *  on 15-May-16.
 */
enum class CompareSign(val compareName: String) {
    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS("<"),
    GREATER(">"),
    LESS_OR_EQUAL("<="),
    GRATER_OR_EQUAL(">=")
}