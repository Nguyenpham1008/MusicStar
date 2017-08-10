package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import nguyenpham.com.adapter.ListMusicAdapter;
import nguyenpham.com.model.Music;

public class SearchActivity extends FragmentActivity {
    SearchView srvMusic;
    DatabaseReference mData;
    ArrayList<Music> listMusicOnline;
    ListView lvList;
    ListMusicAdapter listMusicAdapter;

    Music music = new Music("Until you","Sakira","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mData = FirebaseDatabase.getInstance().getReference();
        addControls();
        addEvents();

    }

    private void addEvents() {
        srvMusic.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                LoadMusic(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String filePath = ((Music)lvList.getAdapter().getItem(i)).getFilePath();
                String title = ((Music)lvList.getAdapter().getItem(i)).getSong();
                String artish = ((Music)lvList.getAdapter().getItem(i)).getSinger();
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
        srvMusic = (SearchView) findViewById(R.id.srvMusic);
        lvList = (ListView) findViewById(R.id.lvList);
        listMusicOnline = new ArrayList<>();
        listMusicAdapter = new ListMusicAdapter(SearchActivity.this,R.layout.layout_list_music,listMusicOnline);
    }

    private void LoadMusic(String query)
    {
            mData.child("ListMusic").child(query).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Music music = dataSnapshot.getValue(Music.class);
                    listMusicOnline.clear();
                    try {
                        listMusicOnline.add(new Music(music.getSong(), music.getSinger(), music.getFilePath()));
                        lvList.setAdapter(listMusicAdapter);
                        listMusicAdapter.notifyDataSetChanged();
                    }catch (Exception ex)
                    {
                        Toast.makeText(SearchActivity.this,"Don't have this music!!!",Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public Music sendData()
    {
        return music;
    }
}
