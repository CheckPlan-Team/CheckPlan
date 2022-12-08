package com.example.CustomView

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kotlinx.coroutines.awaitAll

class CustomMinMaxDecorator(min : CalendarDay, max : CalendarDay) : DayViewDecorator{

    val minDay = min
    val maxDay = max
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.let { minDay.isAfter(it) }!!
    }


    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#d2d2d2")))

    }
}