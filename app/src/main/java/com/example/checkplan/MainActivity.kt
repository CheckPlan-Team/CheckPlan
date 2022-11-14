package com.example.checkplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.checkplan.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabBinding = ActivityMainBinding.inflate(layoutInflater)
        val tab1 : TabLayout.Tab = tabBinding.tabs.newTab()
        tab1.text = "계획 달성률"
        tabBinding.tabs.addTab(tab1)

        val tab2 : TabLayout.Tab = tabBinding.tabs.newTab()
        tab2.text = "일정 관리"
        tabBinding.tabs.addTab(tab2)

        val tab3 : TabLayout.Tab = tabBinding.tabs.newTab()
        tab3.text = "친구 관리"
        tabBinding.tabs.addTab(tab3)

        //탭을 선택하기 전에 기본 뷰로 fragmentOne을 출력
        supportFragmentManager.beginTransaction().replace(R.id.tabContent, PlanManagementFragment()).commit()
        tabBinding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            //탭 버튼을 눌렀을때 동작
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.addToBackStack(null)
                when(tab?.text){
                    "계획 달성률" -> transaction.replace(R.id.tabContent, PlanAchievementRateFragment())
                    "일정 관리" -> transaction.replace(R.id.tabContent, PlanManagementFragment())
                    "친구 관리" -> transaction.replace(R.id.tabContent, FriendManagementFragment())
                }
                transaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        setContentView(tabBinding.root)


    }



}