package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainMenuFragment : Fragment(R.layout.main_menu) {
    private lateinit var navController: NavController
    private lateinit var btnSignOutFragment: Button
    private lateinit var btnAvatarName: Button
    private lateinit var btnZone: Button
    private lateinit var btnDelete: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Находим элементы с layout */
        navController = Navigation.findNavController(view);
        btnSignOutFragment = view.findViewById(R.id.btn_sign_out_fragment)
        btnAvatarName = view.findViewById(R.id.btn_avatar_name)
        btnZone = view.findViewById(R.id.btn_zone)
        btnDelete = view.findViewById(R.id.btn_delete)

        loadData(view)

        /* Для каждой кнопки задаём переход по фрагментам */
        btnSignOutFragment.setOnClickListener {
            navController.navigate(R.id.action_mainMenu_to_signOutFragment)
        }
        btnAvatarName.setOnClickListener {
            navController.navigate(R.id.action_mainMenu_to_changePersonalDataFragment)
        }
        btnZone.setOnClickListener {
            navController.navigate(R.id.action_mainMenu_to_zone)
        }
        btnDelete.setOnClickListener {
            navController.navigate(R.id.action_mainMenu_to_deleteAccountFragment)
        }
    }

    private fun loadData(view: View) {
        /* Находим элементы с layout */
        val nameText: TextView = view.findViewById(R.id.textName)
        val image: ImageView = view.findViewById(R.id.imageView)
        val emailText: TextView = view.findViewById(R.id.textEmail)
        val authText: TextView = view.findViewById(R.id.textAuth)

        /* Определяем текущего пользователя */
        val user = Firebase.auth.currentUser!!

        /* Показываем данные пользователя: фото, имя, email, метод аутентификации */
        user.let {
            if (user.photoUrl != null)
                Glide.with(this).load(user.photoUrl).into(image)
            nameText.text = user.displayName
            emailText.text = user.email
            for (profile in it.providerData) {
                authText.text = profile.providerId
            }
        }
    }
}