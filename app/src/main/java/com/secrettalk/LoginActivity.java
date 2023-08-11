package com.secrettalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    int count;
    String sid;

    EditText et_id;
    EditText et_password;

    Button btn_goToFindId;
    Button btn_goToFindPw;
    Button btn_register;
    Button btn_login;

    List<User> userList = new ArrayList<>();

    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_goToFindId = findViewById(R.id.btn_log_findid);
        btn_goToFindPw = findViewById(R.id.btn_log_findpw);
        btn_register = findViewById(R.id.btn_reg_register);
        btn_login = findViewById(R.id.btn_login);

        et_id = findViewById(R.id.et_log_id);
        et_password = findViewById(R.id.et_log_password);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("User");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                    count += 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // 아이디 찾기 화면 연결
        btn_goToFindId.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = (new Intent(getApplicationContext(), FindIdActivity.class));
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 화면 연결
        btn_goToFindPw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = (new Intent(getApplicationContext(), FindPwActivity.class));
                startActivity(intent);
            }
        });

        // 회원가입 화면 연결
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 메인 화면 연결
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                if (et_id.getText().toString().length() == 0 || et_password.getText().toString().length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("입력되지 않은 정보가 있습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    for (int i = 0; i < count; i++) {
                        String curId, curPw;
                        curId = userList.get(i).id;
                        curPw = userList.get(i).password;
                        if (curId.equals(et_id.getText().toString()) &&
                                curPw.equals(et_password.getText().toString())) {
                            Intent searchIntent = new Intent(LoginActivity.this, MainActivity.class);
                            searchIntent.putExtra("id", userList.get(i).id);
                            searchIntent.putExtra("dept",userList.get(i).dept);
                            sid = userList.get(i).studentid.substring(1, 3) + "학번";
                            searchIntent.putExtra("sid", sid);
                            searchIntent.putExtra("name", userList.get(i).name);

                            LoginActivity.this.startActivity(searchIntent);
                            break;
                        }
                        if (i == count - 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("ID 혹은 Password가 일치하지 않거나" + "\n" + "가입되지 않은 사용자입니다.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    }
                }

            }
        });
    }
}
