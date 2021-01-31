package dev.dotworld.contentprovider

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.dotworld.contentprovider.entity.EmployeeEntity


class EmployeeAdapter : ListAdapter<EmployeeEntity, EmployeeAdapter.EmpViewHolder>(ID_COMPARATOR) {
    companion object {
        private val ID_COMPARATOR = object : DiffUtil.ItemCallback<EmployeeEntity>() {
            override fun areItemsTheSame(oldItem: EmployeeEntity,newItem: EmployeeEntity): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: EmployeeEntity,newItem: EmployeeEntity): Boolean {
                return oldItem._id == newItem._id
            }
        }
    }

    class EmpViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.nameRe)
        private val salary: TextView = itemView.findViewById(R.id.salaryRe)
        private val id: TextView = itemView.findViewById(R.id.idRe)
        private val editBtn: ImageButton = itemView.findViewById(R.id.edit)
        private val layout =itemView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.layoutRecycler)

        fun bind(employeeEntity: EmployeeEntity) {
            Log.d("Employee di", "${employeeEntity._id}: ")
            Log.d("Employee name", "${employeeEntity.name}: ")
            Log.d("Employee salary", "${employeeEntity.salary}:: ")
            name.text=employeeEntity.name
            salary.text=employeeEntity.salary
            id.text=employeeEntity._id.toString()
            layout.setOnClickListener(View.OnClickListener { v ->
                Toast.makeText(
                    itemView.context,
                    "selected "+employeeEntity._id.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            })

        }

        companion object {
            fun create(parent: ViewGroup): EmpViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return EmpViewHolder(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder {
        return EmpViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
        val value = getItem(position)
        holder.bind(value)
    }
}



