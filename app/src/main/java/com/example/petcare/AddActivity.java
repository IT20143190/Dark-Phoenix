package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class AddActivity extends AppCompatActivity {

    EditText title,age,breed,gender,phone,price,purl;
    Button btnAdd,btnBack;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        awesomeValidation = new AwesomeValidation(BASIC);

        title = (EditText)findViewById(R.id.txtTitle);
        age = (EditText)findViewById(R.id.txtAge);
        breed = (EditText)findViewById(R.id.txtBreed);
        gender = (EditText)findViewById(R.id.txtGender);
        phone = (EditText)findViewById(R.id.txtPhone);
        price = (EditText)findViewById(R.id.txtPrice);
        purl = (EditText)findViewById(R.id.txtImageUrl);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        awesomeValidation.addValidation(AddActivity.this, R.id.txtPhone, "^[0-9]{10}$", R.string.err_tel);
        awesomeValidation.addValidation(AddActivity.this, R.id.txtBreed, "[a-zA-Z\\s]+", R.string.err_bre);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void insertData()
    {

        if (awesomeValidation.validate()) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", title.getText().toString());
            map.put("age", age.getText().toString());
            map.put("breed", breed.getText().toString());
            map.put("gender", gender.getText().toString());
            map.put("phone", phone.getText().toString());
            map.put("price", price.getText().toString());
            map.put("purl", purl.getText().toString());


            FirebaseDatabase.getInstance().getReference().child("pet").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddActivity.this, "Error While Inserting Data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void clearAll()
    {
        title.setText("");
        age.setText("");
        breed.setText("");
        gender.setText("");
        phone.setText("");
        price.setText("");
        purl.setText("");
    }
}