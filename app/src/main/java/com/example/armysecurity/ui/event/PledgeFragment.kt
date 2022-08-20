package com.example.armysecurity.ui.event

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.armysecurity.R
import com.example.armysecurity.databinding.FragmentPledgeBinding
import kotlinx.coroutines.*


class PledgeFragment : Fragment() {
    private lateinit var binding:FragmentPledgeBinding
    private lateinit var button:List<ImageView>
    private lateinit var seekBar:List<SeekBar>
    private var played = arrayOf(false,false,false)
    private var player:MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentPledgeBinding.inflate(layoutInflater)
        button = listOf(binding.play1,binding.play2,binding.play3)
        seekBar = listOf(binding.seekbar1,binding.seekbar2,binding.seekbar3)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initListener()

        return binding.root
    }

    private fun clickButton(index:Int){
        if(played[index]){
            stopSong(index)
        }else{
            for(i in 0 until 3){
                if(played[i]){
                    stopSong(i)
                }
            }
            playSong(index)
        }
    }
    private fun stopSong(index:Int){
        if(!played[index])
            return
        played[index] = false
        clearMediaPlayer()
        button[index].setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_not_started_24, null))
        seekBar[index].progress = 0
    }

    private fun playSong(index:Int){
        played[index] = true
        button[index].setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_stop_circle_24, null))

        when(index){
            0-> player = MediaPlayer.create(context, R.raw.sound1)
            1-> player = MediaPlayer.create(context, R.raw.sound2)
            2-> player = MediaPlayer.create(context, R.raw.sound3)
        }
        seekBar[index].max = player?.duration!! / 1000
        player?.start()

        CoroutineScope(Dispatchers.IO).launch {
            if(player != null && player?.isPlaying == true){
                withContext(Dispatchers.Main){
                    seekBar[index].progress = player?.currentPosition!! / 1000
                }
                delay(1000)
            }
        }
    }
    private fun initListener(){
        binding.play1.setOnClickListener{
            clickButton(0)
        }
        binding.play2.setOnClickListener{
            clickButton(1)
        }
        binding.play3.setOnClickListener{
            clickButton(2)
        }

        for(i in 0 until 3){
            seekBar[i].setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    if(player != null && p2){
                        player?.seekTo(p1 * 1000);
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })
        }
    }

    private fun clearMediaPlayer() {
        player?.stop()
        player?.release()
        player = null
    }


    override fun onDestroy() {
        clearMediaPlayer()
        super.onDestroy()
    }
}