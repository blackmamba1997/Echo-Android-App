package com.example.atreyabarui.echo.activities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.atreyabarui.echo.R
import com.example.atreyabarui.echo.activities.MainActivity.statified.notificationManager
import com.example.atreyabarui.echo.activities.MainActivity.statified.trackNotificationBuilder
import com.example.atreyabarui.echo.adapters.NavigationDrawerAdapter
import com.example.atreyabarui.echo.fragments.MainScreenFragment
import com.example.atreyabarui.echo.fragments.SongPlayingFragment

class MainActivity : AppCompatActivity() {
    var navigationDrawerIconList: ArrayList<String> = arrayListOf()
    var images_for_navdrawer = intArrayOf(R.drawable.navigation_allsongs, R.drawable.navigation_favorites
            , R.drawable.navigation_settings, R.drawable.navigation_aboutus)

    object statified {
        var drawerlayout: DrawerLayout? = null
        var notificationManager: NotificationManager? = null
        var trackNotificationBuilder: Notification? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        navigationDrawerIconList.add("All Songs")
        navigationDrawerIconList.add("Favourites")
        navigationDrawerIconList.add("Settings")
        navigationDrawerIconList.add("About Us")
        MainActivity.statified.drawerlayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this@MainActivity, MainActivity.statified.drawerlayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MainActivity.statified.drawerlayout?.addDrawerListener(toggle)
        toggle.syncState()
        val mainScreenFragment = MainScreenFragment()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment, mainScreenFragment, "MainScreenFragment")
                .commit()

        var _navigationAdapter = NavigationDrawerAdapter(navigationDrawerIconList, images_for_navdrawer, this)
        _navigationAdapter.notifyDataSetChanged()
        var navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = _navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)


    }

    override fun onStart() {
        super.onStart()
        try {
            notificationManager?.cancel(1978)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this@MainActivity, System.currentTimeMillis().toInt(),
                intent, 0)
        trackNotificationBuilder = Notification.Builder(this)
                .setContentTitle("Now Playing")
                .setContentText(SongPlayingFragment.Statified.currentSongHelper?.songTitle)
                .setSmallIcon(R.drawable.echo_icon)

                .setContentIntent(pIntent)
                .setOngoing(true)
                .setAutoCancel(true)
                .build()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        try {
            if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                notificationManager?.notify(1978, trackNotificationBuilder)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            notificationManager?.cancel(1978)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
