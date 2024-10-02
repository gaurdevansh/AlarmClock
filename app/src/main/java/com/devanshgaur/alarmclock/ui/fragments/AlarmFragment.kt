package com.devanshgaur.alarmclock.ui.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.devanshgaur.alarmclock.AlarmReceiver
import com.devanshgaur.alarmclock.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

class AlarmFragment : Fragment() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmBtn: Button
    private lateinit var view: View
    private lateinit var seconds: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_alarm, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarmBtn = view.findViewById(R.id.alarm_btn)
        seconds = view.findViewById(R.id.seconds)

        alarmBtn.setOnClickListener {
            val sec = seconds.text.toString().toInt()
            setAlarm(sec)
        }
    }

    private fun showTimePickerDilaog(seconds: Int) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        /*TimePickerDialog(requireContext(), {_, selectedHour, selectedMinute ->
            setAlarm(selectedHour, selectedMinute)
        }, hour, minute, true).show()*/
    }

    private fun setAlarm(seconds: Int) {
        /*val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
        val alarmTime = LocalDateTime.now()
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)*/

        alarmManager = requireContext().getSystemService(AlarmManager::class.java)
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 101, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000 + seconds * 1000,
            pendingIntent
        )
        Toast.makeText(requireContext(), "Alarm successfully set!", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager.cancel(
            pendingIntent
        )
    }
}