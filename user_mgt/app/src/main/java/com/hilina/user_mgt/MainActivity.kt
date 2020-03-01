package com.hilina.user_mgt


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var databaseHelper: DatabaseHelper


    private val sharedPrefFile = "SP_INFO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        val sharedIdValue = sharedPreferences.getString("Username","")

        if(sharedIdValue.equals("") || sharedIdValue == null) {
        }else{
            var intent = Intent(applicationContext,Display_Users::class.java)
            startActivity(intent)
        }

        databaseHelper = DatabaseHelper(this)
        val sp = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        val shUn = sp.getString("Username", "")
        if (!sharedIdValue.equals("")){
            var intent = Intent(applicationContext,Display_Users::class.java)
            startActivity(intent)
        }


        register_button.setOnClickListener {
            var registerIntent = Intent(applicationContext, AddUser::class.java)
            startActivity(registerIntent)

            display_button.setOnClickListener {
                var displayIntent = Intent(applicationContext, Display_Users::class.java)
                startActivity(displayIntent)
            }

            login_button.setOnClickListener {
                var USERNAME = login_userName.getText().toString()
                var PASSWORD = login_password.getText().toString()

                var li = DataManager.fetchAllUsers(databaseHelper);
                var i = 0
                while (i <= li.lastIndex){
                    var v = li.get(i)
                    var RealUserName = v.uName
                    var RealPassword = v.password
                    if(USERNAME == RealUserName){
                        if (PASSWORD == RealPassword){
                            var intent = Intent(applicationContext,Display_Users::class.java)
                            startActivity(intent)
                            val editor = sp.edit()
                            editor.putString("Username",USERNAME)
                            editor.putString("Password",PASSWORD)
                            editor.apply()
                        }else{
                            Toast.makeText(applicationContext,"Password is incorrect",Toast.LENGTH_SHORT).show()
                        }
                    }
                    i++
                }
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
