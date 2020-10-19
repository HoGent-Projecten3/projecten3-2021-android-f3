package com.example.faith


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.login_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var service: GebruikerClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)
        val txtNaam = findViewById<EditText>(R.id.txtNaam);
        val txtWachtwoord = findViewById<EditText>(R.id.txtWachtwoord);




        val retrofit = Retrofit.Builder().baseUrl("http://192.168.1.37:45455/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(GebruikerClient::class.java)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin?.setOnClickListener()
        {
            if (txtNaam.text.isEmpty() || txtWachtwoord.text.isEmpty())
            {
                Toast.makeText(
                    this@LoginActivity,
                    "Gelieve alle gegevens in te geven", Toast.LENGTH_LONG
                ).show()
            } else {
                login();
            }

        }
    }

    private fun login() {
        var login = Login(txtNaam.text.toString(), txtWachtwoord.text.toString())
        val call = service.login("account",login);

        call.enqueue(object : Callback<Gebruiker> {
            override fun onResponse(call: Call<Gebruiker>, response: Response<Gebruiker>) {
                if (response.isSuccessful) {
                    println("success")
                    Toast.makeText(
                        this@LoginActivity,
                        response.message(), Toast.LENGTH_LONG


                    ).show()
                }
            }

            override fun onFailure(call: Call<Gebruiker>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "${t.message}", Toast.LENGTH_LONG)
                    .show()
                println("begin---")
                for (stackTraceElement in t.stackTrace) {

                    println(stackTraceElement)
                }

            }
        })

    }
}
