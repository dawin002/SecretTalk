package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private DatabaseReference Ref;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    String correct_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();

        final String id2 = intent.getExtras().getString("id");

        final TextView TextView_name = (TextView)findViewById(R.id.TextView_name);
        final TextView TextView_id = (TextView)findViewById(R.id.TextView_id);
        final TextView TextView_dept = (TextView)findViewById(R.id.TextView_dept);
        final TextView TextView_studentid = (TextView)findViewById(R.id.TextView_studentid);
        final TextView TextView_phonenumber = (TextView)findViewById(R.id.TextView_phonenumber);
        final TextView TextView_email = (TextView)findViewById(R.id.TextView_email);
        final TextView TextView_gender= (TextView)findViewById(R.id.TextView_gender);
        final TextView TextView_iscertified = (TextView)findViewById(R.id.TextView_iscertified);
        final Button Btn_schoolcertification =(Button)findViewById(R.id.Btn_schoolcertifiation);
        final Button Btn_modify = (Button)findViewById(R.id.Btn_modify);
        final Button Btn_logout = (Button)findViewById(R.id.Btn_logout);
        final EditText EditText_nickname =(EditText) findViewById(R.id.EditText_nickname);

        //유저 정보 가져오기
        Ref = database.getReference("User");
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator<DataSnapshot> child = snapshot.getChildren().iterator();
                while (child.hasNext()) {
                    if (child.next().getKey().equals(id2)) {
                        DatabaseReference infoPw = database.getReference("User/" + id2);
                        infoPw.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                final User user = snapshot.getValue(User.class);
                                correct_id = user.id;
                                if (correct_id.equals(user.id)) {
                                    TextView_name.setText(user.name);
                                    TextView_id.setText(user.id);
                                    TextView_dept.setText(user.dept);
                                    TextView_studentid.setText(user.studentid);
                                    TextView_phonenumber.setText(user.phonenumber);
                                    TextView_email.setText(user.email);
                                    TextView_gender.setText(user.gender);
                                    EditText_nickname.setText(user.nickname);
                                    if (user.iscertified) {
                                        TextView_iscertified.setText("예");
                                    }
                                    else {
                                        TextView_iscertified.setText("아니오");
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        Btn_schoolcertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, SchoolCertification.class);
                intent.putExtra("id",id2);
                startActivity(intent);
            }
        });

        Btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, LoginActivity.class); //나중에 로그인 화면으로 바꾸기
                startActivity(intent);
            }
        });

        Btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ref = database.getReference("User");
                Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterator<DataSnapshot> child = snapshot.getChildren().iterator();
                        while (child.hasNext()) {
                            if (child.next().getKey().equals(id2)) {
                                DatabaseReference infoPw = database.getReference("User/" + id2);
                                infoPw.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        final User user = snapshot.getValue(User.class);
                                        correct_id = user.id;
                                        if (correct_id.equals(user.id)) {
                                            infoPw.child("nickname").setValue(EditText_nickname.getText().toString());
                                            Intent intent = new Intent(UserActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}