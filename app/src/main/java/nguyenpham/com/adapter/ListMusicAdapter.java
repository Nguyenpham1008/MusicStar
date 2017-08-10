package nguyenpham.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import nguyenpham.com.model.Music;
import nguyenpham.com.musicstar.R;

/**
 * Created by Quang Nguyen on 8/5/2017.
 */

public class ListMusicAdapter extends ArrayAdapter<Music> {
     Activity context;
     int resource;
     List<Music> objects;

    public ListMusicAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource, null);

        TextView txtSong = row.findViewById(R.id.txtSong);
        TextView txtSingerName = row.findViewById(R.id.txtSingerName);

        final Music music=this.objects.get(position);
        txtSong.setText(music.getSong());
        txtSingerName.setText(music.getSinger());


        return row;
    }


}
