package com.example.letschat_hibro.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTime(): String {
    // Get the current date and time
    val current = LocalDateTime.now()

    // Define the formatter for the custom format
    val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)
    val dayFormatter = DateTimeFormatter.ofPattern("d")
    val monthFormatter = DateTimeFormatter.ofPattern("/MMMM/", Locale.ENGLISH)
    val yearFormatter = DateTimeFormatter.ofPattern("/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

    // Format the current date and time
    val dayOfWeek = current.format(dayOfWeekFormatter)
    val day = current.format(dayFormatter)
    val month = current.format(monthFormatter)
    val year = current.format(yearFormatter)
    val time = current.format(timeFormatter)

    // Combine the formatted parts into the final string
    return "$dayOfWeek $day $month $year $time".lowercase()
}
