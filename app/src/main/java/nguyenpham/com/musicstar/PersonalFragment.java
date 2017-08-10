package nguyenpham.com.musicstar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nguyenpham.com.musicstar.R;

/**
 * Created by Quang Nguyen on 8/2/2017.
 */

public class PersonalFragment extends Fragment {

    ListView lvPersonal;
    ArrayList<String> arrListPersonal;
    ArrayAdapter<String> stringArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Đọc file xml tạo ra đối tượng View.

        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)

        View view= inflater.inflate(R.layout.fragment_personal, container, false);
        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {

    }

    private void addControls() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
