package com.example.lab1

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class ChangePersonalDataFragment: Fragment(R.layout.change_personal_data) {
    private lateinit var navController: NavController
    private lateinit var imageView: ImageView
    private lateinit var inputName: EditText
    private lateinit var inputEmail: EditText
    private lateinit var button: Button

    private var uri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Находим элементы с layout */
        navController = Navigation.findNavController(view);
        imageView = view.findViewById(R.id.imageView)
        inputName = view.findViewById(R.id.edit_text_name)
        inputEmail = view.findViewById(R.id.edit_text_email)
        button = view.findViewById(R.id.btn_change)

        /* Получаем текущего пользователя и запоминаем некоторые данные */
        val user = Firebase.auth.currentUser!!

        var name = user.displayName
        var email = user.email
        uri = user.photoUrl

        /* Записываем некоторые данные */
        inputName.setText(name)
        inputEmail.setText(email)

        if (uri != null)
            Glide.with(this).load(user.photoUrl).into(imageView)

        /* По нажатию на изображение запускаем активити с галереей */
        imageView.setOnClickListener {
            getContent.launch("image/*")
        }

        /* По нажатию на кнопку сохраняем данные */
        button.setOnClickListener {
            /* Запоминаем что находится в textView */
            name = inputName.text.toString()
            email = inputEmail.text.toString()


            if (uri != null || email!!.isBlank() || name!!.isBlank()) {
                /* Если все данные есть составляем запрос на изменение имени и фотографии пользователя */
                val profileUpdates = userProfileChangeRequest {
                    displayName = name
                    photoUri = uri
                }
                user.updateProfile(profileUpdates)
                    /* Если запрос завершился удовлетворительно, то изменяем данные */
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            user.updateEmail(email!!)
                                /* Также с email пользователя */
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        Toast.makeText( context, "Данные успешно изменены.", Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }
                                    else{
                                        Toast.makeText( context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                        else{
                            Toast.makeText( context, "Что-то пошло не так!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else {
                Toast.makeText( context, "Какие-то данные не добавлены!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /* Записываем картинку в imageView и запоминаем (после выбора картинки в галереи) */
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent())  { uri: Uri? ->
        imageView.setImageURI(uri)
        this.uri = uri
    }
}