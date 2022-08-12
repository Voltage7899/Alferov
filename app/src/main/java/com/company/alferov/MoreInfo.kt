package com.company.alferov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.alferov.databinding.ActivityMoreInfoBinding
import com.squareup.picasso.Picasso

class MoreInfo : AppCompatActivity() {
    lateinit var binding: ActivityMoreInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMoreInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item= intent.getSerializableExtra("car") as Car
        Picasso.get().load(item.link).fit().into(binding.descImage)

        binding.descName.setText(item.name)
        binding.descDesc.setText(item.desc)


        binding.descPrice.setText(item.price)




    }
}