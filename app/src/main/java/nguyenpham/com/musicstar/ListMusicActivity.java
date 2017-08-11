package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import nguyenpham.com.adapter.ListMusicAdapter;
import nguyenpham.com.model.Music;

public class ListMusicActivity extends AppCompatActivity {
    ListView lv;
    String[] items;
    ArrayList<Music> listTitle;
    ListMusicAdapter arrayAdapter;
    ArrayList<HashMap<String,String>> names;

    RelativeLayout layoutListMusic;

    final String MEDIA_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/";
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private String mp3Pattern = ".mp3";
    // Constructor
    public ListMusicActivity() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);

        addControls();
        addEvents();
    }

    private void addEvents() {
        layoutListMusic.setBackgroundColor(Color.parseColor("#0AA8E9"));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filePath = ((Music)lv.getAdapter().getItem(i)).getFilePath();
                String title = ((Music)lv.getAdapter().getItem(i)).getSong();
                String artish = ((Music)lv.getAdapter().getItem(i)).getSinger();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("filePath",filePath);
                returnIntent.putExtra("title",title);
                returnIntent.putExtra("artish",artish);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void addControls() {
        lv = (ListView) findViewById(R.id.lvList);
        layoutListMusic = (RelativeLayout) findViewById(R.id.layoutListMusic);
        listTitle = new ArrayList<>();
        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
        this.songsList = getPlayList();

        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> song = songsList.get(i);

            // adding HashList to ArrayList
            songsListData.add(song);
            Music music = new Music();
            music.setSong(song.get("songTitle"));
            music.setSinger("Unknown");
            music.setFilePath(song.get("songPath"));

            listTitle.add(music);
        }

        arrayAdapter = new ListMusicAdapter(ListMusicActivity.this,R.layout.layout_list_music,listTitle);
        lv.setAdapter(arrayAdapter);
    }

    public ArrayList<HashMap<String, String>> getPlayList() {
        System.out.println(MEDIA_PATH);
        if (MEDIA_PATH != null) {
            File home = new File(MEDIA_PATH);
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    System.out.println(file.getAbsolutePath());
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }
                }
            }
        }
        // return songs list array
        return songsList;
    }
    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(mp3Pattern)) {
            HashMap<String, String> songMap = new HashMap<String, String>();
            songMap.put("songTitle",
                    song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());

            // Adding each song to SongList
            songsList.add(songMap);
        }
    }



}
