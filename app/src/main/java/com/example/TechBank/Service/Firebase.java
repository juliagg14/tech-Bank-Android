package com.example.TechBank.Service;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.TechBank.R;
import com.google.firebase.FirebaseApp;
public class Firebase extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                FirebaseApp.initializeApp(this);
        }
}