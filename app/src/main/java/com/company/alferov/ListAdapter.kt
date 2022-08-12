package com.company.alferov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.alferov.databinding.ElementBinding
import com.squareup.picasso.Picasso

class ListAdapter(val clickListener: ClickListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var ListInAdapter = ArrayList<Car>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        holder.bind(ListInAdapter[position], clickListener)
    }

    override fun getItemCount(): Int {
        return ListInAdapter.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ElementBinding.bind(itemView)
        fun bind(car: Car, clickListener: ClickListener) {
            binding.elName.text = car.name
            binding.elPrice.text = car.price
            Picasso.get().load(car.link).fit().into(binding.elImage)

            itemView.setOnClickListener {

                clickListener.onClick(car)
            }

        }
    }

    fun loadListToAdapter(productList: ArrayList<Car>) {
        ListInAdapter = productList
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onClick(car: Car) {

        }
    }

    fun deleteItem(i: Int): String? {
        var id = ListInAdapter.get(i).id

        ListInAdapter.removeAt(i)

        notifyDataSetChanged()

        return id
    }
}