package com.example.CustomView

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.checkplan.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
//오늘 꾸미기
class CustomTodayDecorator(context : Context) : DayViewDecorator {

    private var drawable : Drawable= context.resources.getDrawable(R.drawable.style_only_radius_40,null)

    //day가 오늘인지 확인
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(CalendarDay.today())!!
    }

    //오늘에 회색 테두리 적용
    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}