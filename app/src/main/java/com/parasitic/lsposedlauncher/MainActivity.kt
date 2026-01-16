package com.parasitic.lsposedlauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import java.io.DataOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread {
            // We removed the text from here...
            runRootCommand()

            runOnUiThread {
                finishAffinity()
            }
        }.start()
    }

    private fun runRootCommand() {
        // ...and put it directly here. Now there is no "Parameter" warning!
        val cmd = "am start-activity -a android.intent.action.MAIN -c org.lsposed.manager.LAUNCH_MANAGER -n com.android.shell/.BugreportWarningActivity"

        try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes(cmd + "\n")
            os.writeBytes("exit\n")
            os.flush()
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}