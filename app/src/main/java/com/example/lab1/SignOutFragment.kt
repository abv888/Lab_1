package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI

class SignOutFragment: Fragment(R.layout.sign_out) {
    private lateinit var btnDelete: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Определяем текущего пользователя */
        btnDelete = view.findViewById(R.id.btn_del_acc)

        btnDelete.setOnClickListener {
            /* Разлогиниваем текущего пользователя */
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    /* Если всё хорошо, то переходим на первое активити и сбрасываем историю переходов */
                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
        }
    }
}