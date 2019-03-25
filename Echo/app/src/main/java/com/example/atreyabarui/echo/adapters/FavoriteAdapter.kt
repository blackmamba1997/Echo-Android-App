package com.example.atreyabarui.echo.adapters

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.atreyabarui.echo.R
import com.example.atreyabarui.echo.Songs
import com.example.atreyabarui.echo.fragments.SongPlayingFragment

class FavoriteAdapter(_songDetails: ArrayList<Songs>, _context: Context) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null

    object Statified {
        var mediaPlayer: MediaPlayer? = null
    }

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)

        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        if (songDetails == null) {
            return 0

        } else {
            return (songDetails as ArrayList<Songs>).size
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)
        holder.trackTitle?.text = songObject?.songTitle
        holder.trackArtist?.text = songObject?.artist
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment = SongPlayingFragment()
            var args = Bundle()
            args.putString("songArtist", songObject?.artist)
            args.putString("path", songObject?.songData)
            args.putString("songTitle", songObject?.songTitle)
            args.putInt("songId", songObject?.songID?.toInt() as Int)
            args.putInt("songPosition", position)
            args.putParcelableArrayList("songData", songDetails)
            args.putString("FavAdapter", "success")
            songPlayingFragment?.arguments = args
            FavoriteAdapter.Statified.mediaPlayer = SongPlayingFragment.Statified.mediaPlayer
            (mContext as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment)
                    .addToBackStack("SongPlayingFragmentFavorite")
                    .commit()
        })

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            trackTitle = view.findViewById(R.id.trackTitle)
            trackArtist = view.findViewById(R.id.trackArtist)
            contentHolder = view.findViewById(R.id.contentRow)
        }
    }

}

