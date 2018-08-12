package jp.techacademy.toshiaki.asakura.firebasetest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    EditText mCateg;
    EditText mQues;
    EditText mAns;
    FirebaseAuth mAuth;
    OnCompleteListener<AuthResult> mCreateAccountListener;
    OnCompleteListener<AuthResult> mLoginListener;
    DatabaseReference mDataBaseReference;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button putButton = (Button) findViewById(R.id.putButton);
        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mCateg = (EditText) findViewById(R.id.editText_Catg);
                String str_Categ = mCateg.getText().toString();
                mQues = (EditText) findViewById(R.id.editText_Ques);
                String str_Ques = mQues.getText().toString();
                mAns = (EditText) findViewById(R.id.editText_Answ);
                String str_Ans = mAns.getText().toString();
                Log.d("asat","■Category1■："+str_Categ);
                Log.d("asat","■Question1■："+str_Ques);
                Log.d("asat","■Answer1■："+str_Ans);
                // ログインしていなければログイン画面に遷移させる
                if (user == null) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    Log.d("asat","■ログインがないのでIntentします！■");
                }
                mDatabase = FirebaseDatabase.getInstance().getReference();
                Map<String, String> data = new HashMap<String, String>();
                data.put("Category", str_Categ);
                data.put("Question", str_Ques);
                data.put("Answer", str_Ans);
                 DatabaseReference dataRef = mDatabase.child(Const.ContentsPATH).child(Const.GenrePATH);
                dataRef.push().setValue(data);
                }
        });

        Button deleteButton = (Button) findViewById(R.id.deletelButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                Map<String, String> data = new HashMap<String, String>();
                DatabaseReference dataRef = mDatabase.child(Const.ContentsPATH).child(Const.GenrePATH);
                dataRef.removeValue();
            }
        });

    }
}
