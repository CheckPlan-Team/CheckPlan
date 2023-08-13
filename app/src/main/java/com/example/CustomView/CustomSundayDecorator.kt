package com.example.CustomView

import android.graphics.Color
import android.icu.util.Calendar
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

//일요일 꾸미기
class CustomSundayDecorator : DayViewDecorator {

    //설명 : day가 일주일 중 일요일인지 확인
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        var weekDay = day!!.calendar.get(Calendar.DAY_OF_WEEK)//요일 정보 얻기
        return weekDay == Calendar.SUNDAY//weekDay가 일요일이면 true를 반환
    }

    //설명 : 일요일 색상을 빨간색으로 설정
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan( ForegroundColorSpan(Color.RED))
    }
}