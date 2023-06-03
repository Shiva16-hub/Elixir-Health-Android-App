package com.example.elixirmedicityhealthapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView age = view.findViewById(R.id.age);
        TextView gender=view.findViewById(R.id.gender);
        TextView blood = view.findViewById(R.id.blood);
        TextView height = view.findViewById(R.id.height);
        TextView weight = view.findViewById(R.id.weight);
        TextView dob= view.findViewById(R.id.dob);
        ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        TextView Name=(TextView) view.findViewById(R.id.user_name);
        TextView textViewRegisterDate= view.findViewById(R.id.textView_show_register_date);

        FirebaseAuth authProfile= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();

              //Function Call

        if(firebaseUser==null){
            Toast.makeText(view.getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }else{
            progressBar.setVisibility(View.VISIBLE);//function call
            showUserProfile(firebaseUser,textViewRegisterDate,progressBar);
//            showProfile(firebaseUser,progressBar,Name,age,dob,gender,height,weight,blood);

        }
        Button signOut=(Button) view.findViewById(R.id.SignOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authProfile.signOut();
                Toast.makeText(view.getContext(), "Signed Out!", Toast.LENGTH_SHORT).show();
                Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
                mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainActivity);

            }
        });

        return view;
    }

//    private void showProfile(FirebaseUser firebaseUser,ProgressBar progressBar,TextView textViewName, TextView textViewAge, TextView textViewDob, TextView textViewGender, TextView textViewHeight, TextView textViewWeight, TextView textViewBlood) {
//        String userID=firebaseUser.getUid();
//
//            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
//            progressBar.setVisibility(View.VISIBLE);
//            referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
//                String textName,textDOB,textAge,textGender,textBlood,textHeight,textWeight;
//                if(readUserDetails!=null){
//                    textName = firebaseUser.getDisplayName();
//                    textAge = readUserDetails.age;
//                    textDOB = readUserDetails.dob;
//                    textGender = readUserDetails.gender;
//                    textBlood = readUserDetails.blood;
//                    textHeight = readUserDetails.height;
//                    textWeight = readUserDetails.weight;
//                    textViewName.setText("Welcome, "+textName+".");
//                    textViewAge.setText(textAge);
//                    textViewDob.setText(textDOB);
//                    textViewGender.setText(textGender);
//                    textViewBlood.setText(textBlood);
//                    textViewHeight.setText(textHeight);
//                    textViewWeight.setText(textWeight);
//
//
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }


    private void showUserProfile(FirebaseUser firebaseUser,TextView textViewRegisterDate,ProgressBar progressBar) {
//        String userID= firebaseUser.getUid();

        FirebaseUserMetadata metadata=firebaseUser.getMetadata();
        long registerTimeStamp = metadata.getCreationTimestamp() ;
        String datePattern="E, dd MMMM yyyy hh:mm a z"; //Day, dd MMMM yyyy hh:mm: AM/PM Time Zone
        SimpleDateFormat sdf=new SimpleDateFormat(datePattern);
        sdf.setTimeZone(TimeZone.getDefault());
        String register = sdf.format(new Date(registerTimeStamp));

        String registerDate = getResources().getString(R.string.user_since,register);

        textViewRegisterDate.setText(registerDate);
        progressBar.setVisibility(View.GONE);
    }

}