package com.company.alferov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.alferov.databinding.ActivityRegistrBinding
import com.google.firebase.database.*

class Registr : AppCompatActivity() {
    lateinit var binding: ActivityRegistrBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regReg.setOnClickListener {

            val name = binding.regName.text.toString()
            val pass = binding.regPass.text.toString()

            try {

                val phone =binding.regPhone.text.toString()
                val code = binding.regCode.text.toString().toInt()

                if (  code!=123){
                    Toast.makeText(this,"Проверьете код админа", Toast.LENGTH_SHORT).show()

                }
                else{
                    val admin= Admin(phone,name,pass)

                    database.child("User").addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (!snapshot.child(binding.regPhone.text.toString()).exists()) {
                                database.child("User").child(binding.regPhone.text.toString()).setValue(admin)
                                Toast.makeText(this@Registr, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                Toast.makeText(this@Registr, "Пользователь с такими данными уже есть", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
            catch (ex: Exception){
                Toast.makeText(this,"Телефон и код цифрами!!", Toast.LENGTH_SHORT).show()
            }



        }
    }
}