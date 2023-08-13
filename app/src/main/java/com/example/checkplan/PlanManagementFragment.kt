package com.example.checkplan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CustomView.CustomCalendar
import com.example.planview.PlanViewAdapter
import com.example.planview.planviewVO
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlanManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlanManagementFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private var planList = arrayListOf<planviewVO>(planviewVO("추가된 플랜"))//플랜이 저장된 리스트
    private lateinit var mainActivity : MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_plan_management, container, false)

        val TodoBtn = view.findViewById<Button>(R.id.AddTodo)
        //할일 추가 버튼 클릭시 inputTodoActivity가 호출
        TodoBtn.setOnClickListener {
            var mainintent : Intent = Intent(mainActivity, InputTodoActivity::class.java)
            startActivity(mainintent)
        }
        //InputTodoActivity에서 데이터 받아오기

        //날짜를 선택했을때 그 날짜에 작성된 플랜 리스트를 리사이클러에 표시(아직 해결 못함)
        val mcalendar = view.findViewById<MaterialCalendarView>(R.id.Calendar)
        val customCalendar = CustomCalendar(mcalendar)
        customCalendar.editCalendar()
        customCalendar.applyDecorator()
        //현재 날짜 정보를 가져와야 함
        mcalendar.setOnDateChangedListener { widget, date, selected ->
            //오늘날짜보다 이전 날짜를 선택하면 일정 추가 버튼을 비활성화(visible)
            if(date.day<CalendarDay.today().day){
                TodoBtn.visibility = View.INVISIBLE
            }else{
                TodoBtn.visibility = View.VISIBLE
            }
        }


        // Inflate the layout for this fragment
        return view
    }

    //프래그먼트가 부모 액티비티에 부착될때 호출
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    //뷰 데이터 조작이 가능
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAdapter = PlanViewAdapter(mainActivity, planList)
        val recycler_view = view.findViewById<RecyclerView>(R.id.Recycler_View)
        recycler_view.adapter = mAdapter

        val layout = LinearLayoutManager(mainActivity)
        recycler_view.layoutManager = layout
        recycler_view.setHasFixedSize(true)



    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlanManagementFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlanManagementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}