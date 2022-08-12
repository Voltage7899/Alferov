package com.company.alferov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.alferov.databinding.ActivityAdminPlaceBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminPlace : AppCompatActivity(),ListAdapter.ClickListener {
    lateinit var binding: ActivityAdminPlaceBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var ListAdapter:ListAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navLeftMenu.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.add->{
                    startActivity(Intent(this,Add::class.java))
                }

            }
            binding.drawer.closeDrawer(GravityCompat.START)
            true
        }


        binding.recyclerAdmin.layoutManager=  GridLayoutManager(this,2)
        ListAdapter= ListAdapter(this)
        binding.recyclerAdmin.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())

        val simpleCallback =object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id =ListAdapter?.deleteItem(viewHolder.adapterPosition)
                database.getReference("Cars").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (id != null) {
                            database.getReference("Cars").child(id.toString()).removeValue()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }

        }
        val itemTouchHelper= ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerAdmin)
    }


    fun getData():ArrayList<Car>{



        val List=ArrayList<Car>()
        database.getReference("Cars").get().addOnSuccessListener {
            for (el in it.children){
                var car=el.getValue(Car::class.java)
                if(car!=null){
                    List.add(car)
                    ListAdapter?.loadListToAdapter(List)
                }

            }
        }
        return List
    }


    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}