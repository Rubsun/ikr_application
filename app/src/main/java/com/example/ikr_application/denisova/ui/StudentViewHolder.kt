package com.example.ikr_application.denisova.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.denisova.domain.models.Student

class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameView: TextView by lazy { itemView.findViewById<TextView>(R.id.name) }
    private val gradeView: TextView by lazy { itemView.findViewById<TextView>(R.id.grade) }

    fun bind(item: Student?) {
        when {
            item == null -> {
                nameView.text = null
                gradeView.text = null
            }
            else -> {
                nameView.text = itemView.context.getString(R.string.denisova_student_name_pattern, item.fullName)
                gradeView.text = itemView.context.getString(R.string.denisova_student_grade_pattern, item.grade)
            }
        }
    }
}
