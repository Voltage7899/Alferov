package com.company.alferov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.alferov.databinding.ActivityRegSignBinding
import com.google.firebase.database.*

class RegSign : AppCompatActivity() {

    lateinit var binding: ActivityRegSignBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegSignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signRegBottomMenu.selectedItemId = R.id.admin;

        binding.signRegBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.user -> {
                    val admin = Intent(this, MainActivity::class.java)
                    startActivity(admin)
                }

            }

            true
        }
        binding.register.setOnClickListener {
            startActivity(Intent(this, Registr::class.java))
        }
        binding.sing.setOnClickListener {
            database.child("User").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.child(binding.phone.text.toString()).exists()) {
                        val userCurrentData: Admin? = snapshot.child(binding.phone.text.toString()).getValue(Admin::class.java)


                        if (userCurrentData?.phone.equals(binding.phone.text.toString()) && userCurrentData?.pass.equals(
                                binding.password.text.toString()))
                        {
                            Toast.makeText(this@RegSign, "Вы вошли как админ", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegSign, AdminPlace::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RegSign, "Неверные данные", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this@RegSign, "Номера не существует", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        }
    }
}
