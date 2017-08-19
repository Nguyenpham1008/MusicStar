package nguyenpham.com.musicstar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Quang Nguyen on 8/2/2017.
 */

public class PersonalFragment extends Fragment {

    ListView lvPersonal;
    ArrayList<String> arrListPersonal;
    ArrayAdapter<String> stringArrayAdapter;

    private ImageView imgImage;
    private Button btnUpload,btnDownload;
    private TextView txtEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Đọc file xml tạo ra đối tượng View.

        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)

        View view= inflater.inflate(R.layout.fragment_personal, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
               txtEmail.setText(data.getStringExtra("EMAIL"));
                Uri myUri = Uri.parse(data.getStringExtra("IMAGE"));
                Picasso.with(getActivity()).load(myUri).into(imgImage);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void addEvents(View view) {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(i, 1);
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
            }
        });
    }

    private void addControls(View view) {
        imgImage = view.findViewById(R.id.imgImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        btnDownload = view.findViewById(R.id.btnDownload);
        txtEmail = view.findViewById(R.id.txtMail);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
