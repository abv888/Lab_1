package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DeleteAccountFragment: Fragment(R.layout.delete_account) {
    private lateinit var btnDeleteAccount: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Находим элементы с layout */
        btnDeleteAccount = view.findViewById(R.id.btn_del_acc)

        btnDeleteAccount.setOnClickListener{
            /* Удаляем текущего пользователя */
            Firebase.auth.currentUser!!.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        /* Если всё хорошо, то переходим на первое активити и сбрасываем историю переходов */
                        val intent = Intent(activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Не удалось удалить.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}