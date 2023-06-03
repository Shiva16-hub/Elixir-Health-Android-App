package com.example.elixirmedicityhealthapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class
HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        TextView name=(TextView) view.findViewById(R.id.textView_show_welcome);
        ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView_profile_dp);
        FirebaseAuth authProfile= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();

        
        if(firebaseUser==null){
            Toast.makeText(view.getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
        return view;
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        FirebaseUserMetadata metadata=firebaseUser.getMetadata();
        long registerTimeStamp = metadata.getCreationTimestamp() ;
        String datePattern="E, dd MMMM yyyy hh:mm a z"; //Day, dd MMMM yyyy hh:mm: AM/PM Time Zone
        SimpleDateFormat sdf=new SimpleDateFormat(datePattern);
        sdf.setTimeZone(TimeZone.getDefault());
        String register = sdf.format(new Date(registerTimeStamp));

        String registerDate = getResources().getString(R.string.user_since,register);
//        textView.setText(registerDate);
        String userID=firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}