package com.example.planview

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.checkplan.R

//리사이클러 뷰에 플랜목록을 읽어와서 보여줌


//뷰 어댑터
class PlanViewAdapter (private val context: Context, private val planViewList:ArrayList<planviewVO>) : RecyclerView.Adapter<PlanViewAdapter.PlanViewHolder>() {

    //뷰 홀더
    inner class PlanViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val planName = itemView.findViewById<TextView>(R.id.PlanNameView)
        //private val planTimeRemaining = itemView.findViewById<Chronometer>(R.id.TimeRemainingView)

        //항목의 뷰 데이터 초기화
        fun bind(planVO : planviewVO, context: Context){
            //플랜명, 날짜, 시간을 초기화해야함
            planName.text=planVO.planName

        }
    }

    //뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemview, parent, false)
        return PlanViewHolder(view)
    }

    //뷰 홀더 데이터 초기화
    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        holder.bind(planViewList[position], context)
    }

    //항목 수 확인
    override fun getItemCount(): Int {
        return planViewList.size
    }
}