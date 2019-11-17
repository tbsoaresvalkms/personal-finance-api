package com.coelhocaique.finance.core.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.util.*

fun <T : Any> T.logger(): Logger = LoggerFactory.getLogger(javaClass)

fun generateReferenceDate(date: LocalDate) = date.toString()
        .replace("-", "")
        .substring(0, 6)

fun formatToUUID(st: String): UUID {
    val uuid = st.replace("-", "")
    return UUID.fromString(uuid.substring(0, 8)
            .plus("-")
            .plus(uuid.substring(8, 12))
            .plus("-")
            .plus(uuid.substring(12, 16))
            .plus("-")
            .plus(uuid.substring(16, 20))
            .plus("-")
            .plus(uuid.substring(20)))
}