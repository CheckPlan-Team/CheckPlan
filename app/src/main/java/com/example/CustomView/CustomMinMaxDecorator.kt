package com.example.CustomView

import android.graphics.Color
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import kotlinx.coroutines.awaitAll

//(오늘)이후 날짜 꾸미기
class CustomMinMaxDecorator(min : CalendarDay, max : CalendarDay) : DayViewDecorator{

    private val minDay = min //현재 날짜의 시작 지점
    private val maxDay = max //현재 날짜의 끝 지점

    //설명 : 현재 날짜(minDay)가 day보다 이전 날짜라면 true, 아니면 false
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.let { minDay.isAfter(it) }!!
    }


    //설명 : 이전 날짜 색상을 d2d2d2(밝은 회색?)로 설정
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(Color.parseColor("#d2d2d2")))

    }
}