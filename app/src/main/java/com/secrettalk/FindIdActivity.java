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

public class FindIdActivity extends AppCompatActivity {

    String id = "";

    int count;

    Button searchBtn;
    EditText et_name;
    EditText et_studentid;
    EditText et_email;

    List<User> userList = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        searchBtn = findViewById(R.id.btn_fid_search);
        et_name = findViewById(R.id.et_fid_name);
        et_studentid = findViewById(R.id.et_fid_studentid);
        et_email = findViewById(R.id.et_fid_email);


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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)


            {

                if (et_name.getText().toString().length() == 0 ||
                        et_studentid.getText().toString().length() == 0 ||
                        et_email.getText().toString().length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                    builder.setMessage("입력되지 않은 정보가 있습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
                else{
                    String curname;
                    String curstudentid;
                    String curemail;
                    for (int i = 0; i < count; i++) {
                        curname = userList.get(i).name;
                        curstudentid = userList.get(i).studentid;
                        curemail = userList.get(i).email;
                        if (curname.equals(et_name.getText().toString()) &&
                                curstudentid.equals(et_studentid.getText().toString()) &&
                                curemail.equals(et_email.getText().toString())) {
                            id = userList.get(i).id;
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                            builder.setMessage("회원님의 ID는 " + id + " 입니다.");
                            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent searchIntent = new Intent(FindIdActivity.this, LoginActivity.class);
                                    searchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    FindIdActivity.this.startActivity(searchIntent);
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                            break;
                        }
                        if (i == count - 1) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(FindIdActivity.this);
                            builder.setMessage("일치하는 회원정보가 없습니다.");
                            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
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