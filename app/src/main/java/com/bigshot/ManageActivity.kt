package com.bigshot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jaredrummler.ktsh.Shell

class ManageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_manage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Bigshot")
            .setMessage("You are 10 seconds to init game")
            .setPositiveButton("Enable"){ dialog, id ->
                Thread{
                    Shell.SU.run("sh /storage/emulated/0/bigshot.sh enable") 
                }.start()
                finishAndRemoveTask()
            }
            .setNegativeButton("Disable"){ dialog, id ->
                Thread{
                    Shell.SU.run("sh /storage/emulated/0/bigshot.sh disable")
                }.start()
                finishAndRemoveTask()
            }
            .setOnDismissListener {
                finishAndRemoveTask()
            }

        builder.show()
    }
}