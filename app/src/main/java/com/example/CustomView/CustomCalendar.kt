package com.example.CustomView

import android.icu.util.Calendar
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

//MarterialCalendarView를 커스탐하여 만든 Calendar
class CustomCalendar (var mCalendar : MaterialCalendarView){

    var startTimeCalendar = Calendar.getInstance()//월/일/년/시간으로 초기화된 Calendar객체
    var endTimeCalendar = Calendar.getInstance()//월/일/년/시간으로 초기화된 Calendar객체

    val currentYear = startTimeCalendar.get(Calendar.YEAR)//현재 연도
    val currentMonth = startTimeCalendar.get(Calendar.MONTH)//현재 월
    val currentDate = startTimeCalendar.get(Calendar.DATE)//현재 월의 날짜

    val stCalendarDay = CalendarDay.from(currentYear,currentMonth,currentDate)//현재 년/월/일에 맞는 요일
    val enCalendarDay = CalendarDay.from(endTimeCalendar.get(Calendar.YEAR),endTimeCalendar.get(Calendar.MONTH),endTimeCalendar.get(Calendar.DATE))
    val sundayDecorator = CustomSundayDecorator()
    val saturdayDecorator = CustomSaturdayDecorator()
    val todayDecorator = CustomTodayDecorator(mCalendar.context)
    val minmaxDecorator = CustomMinMaxDecorator(stCalendarDay,enCalendarDay)



    //마지막 달 설정
    fun setEndTimeCalendar(){
        endTimeCalendar.set(Calendar.MONTH, currentMonth+3)
    }

    //CustomCalendar 설정
    fun editCalendar(){
        mCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear-100,currentMonth,1))
            .setMaximumDate(CalendarDay.from(currentYear+100,currentMonth,endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)))
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()
    }

    //Decorator 적용
    fun applyDecorator(){
        mCalendar.addDecorators(sundayDecorator,saturdayDecorator,todayDecorator,minmaxDecorator)
    }

}