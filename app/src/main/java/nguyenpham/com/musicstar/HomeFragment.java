package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

import nguyenpham.com.model.Music;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Quang Nguyen on 8/3/2017.
 */

public class HomeFragment extends Fragment {
    RelativeLayout linear_home;
    TextView txtSong,txtSinger,txtStart,txtStop;
    ImageButton btnListMusic,btnFavorite,btnPlay,btnNext,btnPrevious,btnSearch,btnMenu;
    SeekBar seekBar;

    Animation animation;
    ImageView imgMusic;

    int REQUEST_LIST_MUSIC = 1;
    private Handler mHandler = new Handler();

    int flagPlay=0;
    MediaPlayer mpintro = new MediaPlayer();
    int length;
    int flag=0;
    String LAST_MUSIC = "LASTMUSIC";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Đọc file xml tạo ra đối tượng View
        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        addControls(view);
        addEvents(view);
        //linear_home.setBackgroundColor(Color.parseColor("#CFD8DC"));
        loadLastMusic();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(flag>0) {
            if(!((MainActivity) getActivity()).getData().getFilePath().equals("")) {
                Music music = new Music(((MainActivity) getActivity()).getData().getSong(),
                        ((MainActivity) getActivity()).getData().getSinger(),
                        ((MainActivity) getActivity()).getData().getFilePath());
                {
                    //Set data empty again
                    ((MainActivity) getActivity()).getData().setFilePath("");
                    //Call to play online
                    playOnline(music);
                }
            }
        }
        flag++;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_MUSIC) {
            if(resultCode == Activity.RESULT_OK){

                //Get data from ListMusicActivity
                String result=data.getStringExtra("filePath");
                String title = data.getStringExtra("title");
                String artish = data.getStringExtra("artish");
                Music music= new Music(title,artish,result);
                playOffline(music);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void addEvents(View view) {
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"font/VNI-Disney.ttf");
        txtSong.setTypeface(type);
        txtSinger.setTypeface(type);

        btnListMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ListMusicActivity.class);
                startActivityForResult(intent, REQUEST_LIST_MUSIC);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPauseMusic();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mpintro != null && b){
                    mpintro.seekTo(i);
                    mpintro.start();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void addControls(View view) {
        txtSong = view.findViewById(R.id.txtNameSong);
        txtSinger = view.findViewById(R.id.txtNameSinger);
        txtStart = view.findViewById(R.id.txtStart);
        txtStop = view.findViewById(R.id.txtStop);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        btnListMusic = view.findViewById(R.id.btnListMusic);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.imgImage);
        linear_home = view.findViewById(R.id.relativeLayout);
        seekBar = view.findViewById(R.id.seekBar);

    }

    private void playPauseMusic() {

        if(flagPlay%2==0)
        {
            {
//                //Play again the previous music
//                if (mpintro.getCurrentPosition() == 0) {
//                    mpintro.start();
//                    txtStart.setText("0:0");
//                    runSeekBar();
//                }
                //Change icon when resume
                btnPlay.setBackgroundResource(R.drawable.ic_pause_music);
                //Start at the last positon
                mpintro.seekTo(length);
                mpintro.start();
                flagPlay++;
                runSeekBar();
            }
        }else
        {
            //Change icon when pause
            btnPlay.setBackgroundResource(R.drawable.ic_play_music);
            //Pause and save currentpostion
            mpintro.pause();
            length=mpintro.getCurrentPosition();
            flagPlay++;
        }
    }

    private void playOnline(Music music)
    {
        mpintro.release();
        txtSinger.setText(music.getSinger());
        txtSong.setText(music.getSong());
        String url = (music.getFilePath());
        mpintro = new MediaPlayer();
        // your URL here
        mpintro.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mpintro.setDataSource(url);
            mpintro.prepare(); // might take long! (for buffering, etc)
            btnPlay.setBackgroundResource(R.drawable.ic_pause_music);
            flagPlay++;
            seekBar.setMax(mpintro.getDuration());
            txtStop.setText(milliSecondsToTimer(mpintro.getDuration()));
            mpintro.start();
            runSeekBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playOffline(Music music)
    {
        if(mpintro!=null) {
            mpintro.release();
        }
        mpintro = MediaPlayer.create(getActivity(), Uri.parse(music.getFilePath()));

        //Set Song and Singer
        txtSong.setText(music.getSong());
        txtSinger.setText(music.getSinger());

        //Change icon start
        btnPlay.setBackgroundResource(R.drawable.ic_pause_music);
        flagPlay++;
        seekBar.setMax(mpintro.getDuration());
        txtStop.setText(milliSecondsToTimer(mpintro.getDuration()));
        mpintro.start();
        saveLastMusic(music.getSong(),music.getSinger(),music.getFilePath(),milliSecondsToTimer(mpintro.getDuration()));
        runSeekBar();
    }

    //Thread update timer every second
    private void runSeekBar()
    {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                int currentDuration;
                if(mpintro != null){
                    if(seekBar.getProgress()!= mpintro.getDuration()) {
                        int mCurrentPosition = mpintro.getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition);
                        currentDuration = mpintro.getCurrentPosition();
                        updatePlayer(currentDuration);
                        mHandler.postDelayed(this, 1000);
                    }else
                    {
                        //Set bacground icon Play and reset seekbar
                        btnPlay.setBackgroundResource(R.drawable.ic_play_music);
                        seekBar.setProgress(0);
                        mpintro.seekTo(0);
                        flagPlay++;
                    }
                }
            }
        });

    }
    //Set time
    private void updatePlayer(int currentDuration){
        txtStart.setText("" + milliSecondsToTimer((long) currentDuration));
    }

   //Convert miliseconds to Minutes
    private String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }


    private void loadLastMusic()
    {
        SharedPreferences prefs = getActivity().getSharedPreferences(LAST_MUSIC, MODE_PRIVATE);
        txtSong.setText(prefs.getString("song","1 2 3 4"));
        txtSinger.setText(prefs.getString("singer","Chi Dân"));
        txtStop.setText(prefs.getString("time","0:0"));
        String filePath = prefs.getString("filePath","/storage/emulated/0/Download/1234-Chi-Dan.mp3");
        mpintro = MediaPlayer.create(getActivity(), Uri.parse(filePath));
    }

    private void saveLastMusic(String song,String singer,String filePath,String time)
    {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(LAST_MUSIC, MODE_PRIVATE).edit();
        editor.putString("song", song);
        editor.putString("singer", singer);
        editor.putString("filePath",filePath);
        editor.putString("time",time);
        editor.apply();

    }

}
