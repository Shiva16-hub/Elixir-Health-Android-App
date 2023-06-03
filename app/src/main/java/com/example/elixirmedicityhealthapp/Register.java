package com.example.elixirmedicityhealthapp;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText editTextRegisterEmail, editTextRegisterPwd,editTextConfirmPwd, editTextRegisterName, editTextRegisterAge,editTextDOB,editTextBlood,editTextHeight,editTextWeight;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegistrationGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private FirebaseAuth auth;
    private static final String TAG ="Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Sign Up");
        Toast.makeText(Register.this, "You can register now!", Toast.LENGTH_SHORT).show();

        //finding views by id's
        editTextDOB=findViewById(R.id.editText_register_dob);
        editTextBlood=findViewById(R.id.editText_register_blood);

        editTextHeight=findViewById(R.id.editText_register_height);
        editTextWeight=findViewById(R.id.editText_register_weight);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterName = findViewById(R.id.editText_register_name);
        editTextRegisterAge = findViewById(R.id.editText_register_age);
        editTextConfirmPwd=findViewById(R.id.editText_register_confirm_password);

        radioGroupRegistrationGender=findViewById(R.id.radio_group_register_gender);
        radioGroupRegistrationGender.clearCheck();

        progressBar = findViewById(R.id.progressBar);
        showHidePwd();

        Button ButtonRegister = findViewById(R.id.button_register);
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId=radioGroupRegistrationGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected=findViewById(selectedGenderId);

                //Obtain the data from user
                String textEmail = editTextRegisterEmail.getText().toString();
                String textPassword = editTextRegisterPwd.getText().toString();
                String textName = editTextRegisterName.getText().toString();
                String textAge = editTextRegisterAge.getText().toString();
                String textDOB=editTextDOB.getText().toString();

                String textBlood=editTextBlood.getText().toString();
                String textHeight=editTextHeight.getText().toString();
                String textWeight=editTextWeight.getText().toString();
                String textConfirmPwd = editTextConfirmPwd.getText().toString();
                String textGender;

                // check if all the data are present before registering user
                if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(Register.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    editTextRegisterName.setError("Name is Required");
                    editTextRegisterName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(Register.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is Required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(Register.this, "Please Re-Enter Your Email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Valid Email is Required");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDOB)) {
                    Toast.makeText(Register.this, "Please Enter Your DOB", Toast.LENGTH_SHORT).show();
                    editTextDOB.setError("DOB is Required");
                    editTextDOB.requestFocus();
                } else if (radioGroupRegistrationGender.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(Register.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textBlood)) {
                    Toast.makeText(Register.this, "Please Enter Your Blood Type", Toast.LENGTH_SHORT).show();
                    editTextBlood.setError("Blood Type is Required");
                    editTextBlood.requestFocus();
                } else if (TextUtils.isEmpty(textAge)) {
                    Toast.makeText(Register.this, "Please Enter Your Age", Toast.LENGTH_SHORT).show();
                    editTextRegisterAge.setError("Address is Required");
                    editTextRegisterAge.requestFocus();
                } else if (TextUtils.isEmpty(textHeight)) {
                    Toast.makeText(Register.this, "Please Enter Your Height", Toast.LENGTH_SHORT).show();
                    editTextHeight.setError("Height is Required");
                    editTextHeight.requestFocus();
                } else if (TextUtils.isEmpty(textWeight)) {
                    Toast.makeText(Register.this, "Please Enter Your Weight", Toast.LENGTH_SHORT).show();
                    editTextWeight.setError("Weight is Required");
                    editTextWeight.requestFocus();
                }else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(Register.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password is Required");
                    editTextRegisterPwd.requestFocus();
                }else if(textPassword.length()<8) {
                    Toast.makeText(Register.this, "Password should be at least of 8 characters!", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password to weak!");
                    editTextRegisterPwd.requestFocus();
                }else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(Register.this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Confirm Password");
                    editTextRegisterPwd.requestFocus();
                }else if (!textPassword.equals(textConfirmPwd)) {
                    Toast.makeText(Register.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password not confirmed");
                    editTextRegisterPwd.requestFocus();
                    //Clear the entered password
                    editTextRegisterPwd.clearComposingText();
                    editTextConfirmPwd.clearComposingText();
                }else{
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textEmail,textName,textBlood,textDOB,textHeight,textWeight,textAge,textPassword,textGender);
                }
            }
        });
    }

    private void registerUser(String textEmail, String textName, String textBlood, String textDOB,String textHeight,String textWeight,String textAge,String textPassword,String textGender){
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    //      send verification email
                    firebaseUser.sendEmailVerification();

                    Intent homeActivity = new Intent(Register.this,MainActivity2.class);
                    homeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeActivity);
                    finish();

                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textAge,textDOB,textBlood,textHeight,textGender,textWeight);

                    //Extracting user reference from database for registerd user
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                firebaseUser.sendEmailVerification();
                                //      send verification email
                                Toast.makeText(Register.this, "User registered successfully, Please verify your email", Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(Register.this, "Registration failed! pleases try again.", Toast.LENGTH_SHORT).show();

                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                    //Send Verification Email

//                    if(firebaseUser!=null){
//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(textName).build();
//                        firebaseUser.updateProfile(profileUpdates);
////                        firebaseUser.sendEmailVerification();
//                        Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//                        Intent homeActivity = new Intent(Register.this,MainActivity2.class);
//
//                        homeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(homeActivity);
//                        finish();
//                    }
                }
                else{
                    try {
                        throw task.getException();

                    }catch (FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your Password is too weak, Kindly use mic=x of alphabets,numbers and special characters");
                        editTextRegisterPwd.requestFocus();
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Email is invalid or already in use, kindly re-enter");
                        editTextRegisterPwd.requestFocus();
                    }catch(FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("user is already registered with this email, use another email");
                        editTextRegisterPwd.requestFocus();
                    }catch (Exception e){

                        Log.e(TAG , e.getMessage());
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showHidePwd() {
        ImageView imageViewShowHidePwd = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);//Set Image for password

        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // if pwd visible then hide it
                if(editTextRegisterPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    editTextRegisterPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd); // change icon

                }else{
                    editTextRegisterPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                }
            }
        });
    }


}