package io.datalbry.connector.plugin.config

enum class ProgrammingLanguage {
    KOTLIN;

    companion object {
        fun byName(name: String): ProgrammingLanguage {
            when (name.toLowerCase()) {
                "kotlin" -> return ProgrammingLanguage.KOTLIN
                else -> throw IllegalArgumentException("No such Programming language as part of the enum")
            }
        }
    }
}
