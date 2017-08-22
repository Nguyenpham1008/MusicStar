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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Quang Nguyen on 8/2/2017.
 */

public class PersonalFragment extends Fragment {

    ListView lvPersonal;
    ArrayList<String> arrListPersonal;
    ArrayAdapter<String> stringArrayAdapter;

    private CircularImageView imgImage;
    private ImageButton btnUpload,btnDownload,btnFavorites;
    private TextView txtEmail;
    private Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Đọc file xml tạo ra đối tượng View.

        // inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)

        View view = inflater.inflate(R.layout.fragment_personal, container, false);
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

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (FirebaseAuth.getInstance().getCurrentUser().getProviderId()!= null) {
                btnLogout.setVisibility(View.VISIBLE);
            }
        }catch (NullPointerException ex){
            btnLogout.setVisibility(View.INVISIBLE);
        }
    }

    private void addEvents(View view) {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callToLoginActivity()==false){
                    Toast.makeText(getActivity(),"user signed",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callToLoginActivity()==false){
                    Toast.makeText(getActivity(),"user signed",Toast.LENGTH_LONG).show();
                }
            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                btnLogout.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addControls(View view) {
        imgImage = view.findViewById(R.id.imgImage);
        btnUpload = view.findViewById(R.id.btnUpload);
        btnDownload = view.findViewById(R.id.btnDownload);
        btnFavorites = view.findViewById(R.id.btnFavorites);
        txtEmail = view.findViewById(R.id.txtMail);
        btnLogout = view.findViewById(R.id.btnLogout);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private boolean callToLoginActivity()
    {
        try {
            if (FirebaseAuth.getInstance().getCurrentUser().getProviderId()!= null) {
               // Toast.makeText(getActivity(), "User signed", Toast.LENGTH_LONG).show();
                return false;
            }
        }catch (NullPointerException ex){
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(i, 1);
        }
        return true;
    }
}
