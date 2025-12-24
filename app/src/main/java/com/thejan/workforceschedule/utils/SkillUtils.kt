package com.thejan.workforceschedule.utils


object SkillUtils {

    fun parseSkills(skillsJson: String): List<String> {
        return skillsJson
            .removePrefix("[")
            .removeSuffix("]")
            .replace("\"", "")
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }
}