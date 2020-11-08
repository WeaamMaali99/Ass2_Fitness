package edu.cs.birzeit.ass2_fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn=findViewById(R.id.btnSingIn);
    }

    public void btnSign(View view) {
        Intent intent =new Intent(this,open.class);
        startActivity(intent);
    }
}