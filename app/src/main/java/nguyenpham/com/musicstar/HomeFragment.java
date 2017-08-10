package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Quang Nguyen on 8/3/2017.
 */

public class HomeFragment extends Fragment {
    LinearLayout linear_home;
    TextView txtSong,txtSinger;
    ImageButton btnListMusic,btnFavorite,btnPlay,btnNext,btnPrevious,btnSearch,btnMenu;
    Integer REQUEST_LIST_MUSIC = 1;
    Integer REQUEST_LIST_MUSIC_ONLINE = 2;

    int flagPlay=0;
    MediaPlayer mpintro;
    int length;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Đọc file xml tạo ra đối tượng View.

        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        linear_home = view.findViewById(R.id.linear_home);
        linear_home.setBackgroundColor(Color.parseColor("#F1F1F1"));
        addControls(view);
        addEvents(view);
        return view;
    }

    private void getDataFromMainActivity() {
        String url = getArguments().getString("RESULT"); // your URL here
        mpintro.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mpintro.setDataSource(url);
            mpintro.prepare(); // might take long! (for buffering, etc)
            // mpintro.setLooping(true);

            //Set Song and Singer
            txtSong.setText(getArguments().getString("TITLE"));
            txtSinger.setText(getArguments().getString("ARTISH"));

            //Change icon start
            btnPlay.setBackgroundResource(R.drawable.ic_pause_music);
            flagPlay++;
            mpintro.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                mpintro = MediaPlayer.create(getActivity(), Uri.parse(result));
               // mpintro.setLooping(true);

                //Set Song and Singer
                txtSong.setText(title);
                txtSinger.setText(artish);

                //Change icon start
                btnPlay.setBackgroundResource(R.drawable.ic_pause_music);
                flagPlay++;
                mpintro.start();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else if (requestCode == REQUEST_LIST_MUSIC_ONLINE)
            if(resultCode == Activity.RESULT_OK){

                //Get data from ListMusicActivity
                String result=data.getStringExtra("filePath");
                String title = data.getStringExtra("title");
                String artish = data.getStringExtra("artish");


            }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
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
                playMusic();
            }
        });
    }

    private void addControls(View view) {
        txtSong = view.findViewById(R.id.txt_music_name);
        txtSinger = view.findViewById(R.id.txt_name_singer);
        btnFavorite = view.findViewById(R.id.btnFavorite);
        btnListMusic = view.findViewById(R.id.btnListMusic);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);

    }

    private void playMusic() {
        if(flagPlay%2==0)
        {
            //Change icon when resume
            btnPlay.setBackgroundResource(R.drawable.ic_pause_music);

            //Start at the last positon
            mpintro.seekTo(length);
            mpintro.start();
            flagPlay++;
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

}
