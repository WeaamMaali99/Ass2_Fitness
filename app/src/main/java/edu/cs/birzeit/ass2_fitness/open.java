package edu.cs.birzeit.ass2_fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class open extends AppCompatActivity {
    private Button btnStart;
    private Button btnCalBMI;
    private Button btnBack;
    private Button btnSave;
    private EditText txtHeight ;
    private EditText editCalculatBMI ;
    private EditText txtUserName;
    private EditText txtWeight;
    private Spinner spinner;
    public static final String NAME="NAME";
    public static final String FLAG="FLAG";
    public static final String HEIGHT="HEIGHT";
    public static final String WEIGHT="WEIGHT";
    public static final String GENDER="GENDER";
    public static final String BMI="BMI";


    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
        setupViews();
        populateSpinner();
        setupSharedPrefs();
        CkeckStore();

    }
    private void setupViews(){
        btnStart= findViewById(R.id.btnStart);
        btnCalBMI=findViewById(R.id.btnCalBMI);
        txtHeight=findViewById(R.id.txtHeight);
        txtWeight=findViewById(R.id.txtWeight);
        editCalculatBMI=findViewById(R.id.editCalculatBMI);
        txtUserName=findViewById(R.id.txtUserName);
        btnSave=findViewById(R.id.btnSave);
        spinner=findViewById(R.id.spinner);
        btnBack=findViewById(R.id.btnBack);

    }
    private void populateSpinner(){
        String[] item =new String[]{"Male","Female"};
        ArrayAdapter<String> adapter =new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,item);
        spinner.setAdapter(adapter);

    }
    public void btnOnClickCalBMI(View view) {
        String strWeight=txtWeight.getText().toString();
        String  strHeight=txtHeight.getText().toString();
        if (strWeight.equals("")){
            txtWeight.setError("Please Enter your Weight");
            txtWeight.requestFocus();
            return;
        }
        if(strHeight.equals("")){
            txtHeight.setError("Please Enter your Height");
            txtHeight.requestFocus();
            return;
        }
        float weight =Float.parseFloat(strWeight);
        float height = Float.parseFloat(strHeight)/100;
        float Val = CalBMI(weight,height);

        editCalculatBMI.setText(""+Val);

    }
    private void CkeckStore(){
        boolean flag =prefs.getBoolean(FLAG,false);

        if(flag) {
            String name = prefs.getString(NAME,"");
            Integer gender =prefs.getInt(GENDER,0);
             Integer weight = prefs.getInt(WEIGHT,0);
             Integer height=prefs.getInt(HEIGHT,0);
         ///   Integer bmi = prefs.getInt(BMI,0);
            txtUserName.setText(""+name);
            txtWeight.setText(""+weight);
            txtHeight.setText("" +height);
           spinner.setSelection(gender);
         //   editCalculatBMI.setText(""+bmi);


        }
    }
    private void setupSharedPrefs(){
   //   SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
      prefs = PreferenceManager.getDefaultSharedPreferences (this);
         editor = prefs.edit();




    }

    public void btnonClickSaveData(View view) {
        String name =txtUserName.getText().toString();
        int we= Integer.parseInt(txtWeight.getText().toString());
        int height= Integer.parseInt(txtHeight.getText().toString());
       // int bmi= Integer.parseInt(editCalculatBMI.getText().toString());

      int  spn=spinner.getSelectedItemPosition();

            editor.putString(NAME, name);
            editor.putInt(WEIGHT, we);
            editor.putInt(HEIGHT, height);
            editor.putInt(GENDER,spn);
      //    editor.putInt(BMI, bmi);
            editor.putBoolean(FLAG, true);
            editor.commit();



    }

   

    public void btnOnClickStart(View view) {
        Intent intent =new Intent(this,Timer.class);
        startActivity(intent);
    }
    public float CalBMI(float Weight,float Height ){

        return Weight/(Height*Height);
    }


    public void btnBack(View view) {
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}