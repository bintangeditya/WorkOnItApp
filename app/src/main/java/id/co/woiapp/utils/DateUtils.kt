package id.co.woiapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    var parser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    var formatterTIE: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm")

    //    var output: String = formatter.format(parser.parse("2018-12-14T09:55:00"))
//    var output: Date? = parser.parse("2018-12-14T09:55:00")

    fun apiToDate(str: String?): Date {
        if (!str.isNullOrBlank())
            return parser.parse(str)!!
        return Date()
    }

    fun dateToStr(date: Date) = formatterTIE.format(date)

    fun dateToStrApi(date: Date) = parser.format(date)


}
