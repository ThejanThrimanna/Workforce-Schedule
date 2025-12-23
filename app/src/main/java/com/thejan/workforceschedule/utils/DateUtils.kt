package com.thejan.workforceschedule.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
object DateUtils {

    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")
    private val UI_FORMATTER = DateTimeFormatter.ofPattern("dd MMM")

    fun dateStringToMillis(date: String): Long {
        val localDate = LocalDate.parse(date, DATE_FORMATTER)
        return localDate
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun dateTimeStringToMillis(date: String, time: String): Long {
        val localDate = LocalDate.parse(date, DATE_FORMATTER)
        val localTime = LocalTime.parse(time, TIME_FORMATTER)

        return LocalDateTime.of(localDate, localTime)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }

    fun millisToDateString(millis: Long): String =
        java.time.Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DATE_FORMATTER)

    fun formatForUi(date: String): String =
        LocalDate.parse(date, DATE_FORMATTER)
            .format(UI_FORMATTER)

    fun localDateToMillis(date: LocalDate?): Long {
        return date
            ?.atStartOfDay(ZoneId.systemDefault())
            ?.toInstant()
            ?.toEpochMilli() ?: 0L
    }

    fun currentWeekStart(): LocalDate {
        return LocalDate.now()
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }

    fun currentWeekEnd(): LocalDate {
        return LocalDate.now()
            .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    }

    fun millisToDate(millis: Long): String {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("dd MMM yy"))
    }

    fun millisToTime(millis: Long): String {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}