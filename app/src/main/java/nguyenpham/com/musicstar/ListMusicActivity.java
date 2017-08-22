package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

    String DATABASE_NAME="ListMusic.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

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
                returnIntent.putExtra("position",i);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private void addControls() {
        lv = (ListView) findViewById(R.id.lvList);
        layoutListMusic = (RelativeLayout) findViewById(R.id.layoutListMusic);
        listTitle = new ArrayList<>();

        showListMusic();
        arrayAdapter = new ListMusicAdapter(ListMusicActivity.this,R.layout.layout_list_music,listTitle);
        lv.setAdapter(arrayAdapter);
    }

    private void showListMusic() {
        //Open database
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor=database.query("Music",null,null,null,null,null,null);
        listTitle.clear();
        while (cursor.moveToNext())
        {
            String song=cursor.getString(1);
            String singer=cursor.getString(2);
            String filePath=cursor.getString(3);

            listTitle.add(new Music(song,singer,filePath));
        }
        cursor.close();//close connection
    }
}
