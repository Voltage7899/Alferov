package com.company.alferov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.alferov.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity(),ListAdapter.ClickListener {
    lateinit var binding: ActivityMainBinding
    var ListAdapter:ListAdapter?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.admin -> {
                    val admin = Intent(this, RegSign::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.recUser.layoutManager= GridLayoutManager(this,2)
        ListAdapter = ListAdapter(this)
        binding.recUser.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<Car>{



        val List=ArrayList<Car>()
        database.getReference("Cars").get().addOnSuccessListener {
            for (i in it.children){
                var party=i.getValue(Car::class.java)
                if(party!=null){
                    List.add(party)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }
    override fun onClick(car: Car) {
        startActivity(Intent(this, MoreInfo::class.java).apply {
            putExtra("car",car)

        })
    }
    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}