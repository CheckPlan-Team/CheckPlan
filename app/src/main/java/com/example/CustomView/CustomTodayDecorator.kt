package com.example.CustomView

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.checkplan.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
//캘린더에서 오늘을 꾸며주는 클래스
class CustomTodayDecorator(context : Context) : DayViewDecorator {


    private var drawable : Drawable= context.resources.getDrawable(R.drawable.style_only_radius_40,null)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(CalendarDay.today())!!
    }


    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}