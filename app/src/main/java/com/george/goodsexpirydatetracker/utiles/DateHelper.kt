package com.george.goodsexpirydatetracker.utiles

import com.george.goodsexpirydatetracker.models.DateSections
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class DateHelper(val long: Long) {

    var dateSections: DateSections

    init {
       dateSections = dateSectionExtractor()
    }


    fun timestampConverter(): String {
        val stamp = Timestamp(long)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = Date(stamp.time)
        return sdf.format(date)
    }

    fun dateSectionExtractor() : DateSections {
        val date = timestampConverter()
        val splitter = date.split('/')
        return DateSections(
            day = splitter[0].toInt(),
            month = splitter[1].toInt(),
            year = splitter[2].toInt()
        )
    }




}

