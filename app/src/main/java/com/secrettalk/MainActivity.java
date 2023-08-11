package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference Ref;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    Button Btn_mychat, Btn_searchchat, Btn_mypage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String id2 = intent.getExtras().getString("id");
        String dept2 = intent.getExtras().getString("dept");
        String sid2 = intent.getExtras().getString("sid");

        Btn_mychat = findViewById(R.id.Btn_mychat);
        Btn_searchchat = findViewById(R.id.Btn_searchchat);
        Btn_mypage = findViewById(R.id.Btn_mypage);

        Btn_mychat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long curTime = System.currentTimeMillis();

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
                                        if (user.isbanned) {
                                            if(user.bandate > curTime){
                                                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                String realbandate = timeFormat.format(new Date(user.bandate));
                                                Toast.makeText(MainActivity.this, "사용 제재 중인 유저입니다.\n" + realbandate + "까지 제재입니다.", Toast.LENGTH_SHORT).show();

                                            }

                                            else{
                                                DatabaseReference infoPw = database.getReference("User/" + id2);
                                                infoPw.child("isbanned").setValue(false);
                                                infoPw.child("ban").setValue(0);
                                            }

                                        }
                                        else if(user.iscertified == false){
                                            Toast.makeText(MainActivity.this, "학교인증을 우선 하십시오", Toast.LENGTH_SHORT).show();
                                        }

                                        else{
                                            Intent intent = new Intent(MainActivity.this, MyChatListActivity.class); //나중에 내 채팅방으로 바꾸기
                                            intent.putExtra("id",id2);
                                            intent.putExtra("dept",dept2);
                                            intent.putExtra("sid",sid2);
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

        Btn_searchchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long curTime = System.currentTimeMillis();
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
                                        if (user.isbanned) {
                                            if(user.bandate > curTime){ //제재가 아직 안풀렸을 때
                                                SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                String realbandate = timeFormat.format(new Date(user.bandate));
                                                Toast.makeText(MainActivity.this, "사용 제재 중인 유저입니다.\n" + realbandate + "까지 제재입니다.", Toast.LENGTH_SHORT).show();

                                            }

                                            else{ // 제재 날짜보다 지나서 제재를 풀게 하는 경우
                                                DatabaseReference infoPw = database.getReference("User/" + id2);
                                                infoPw.child("isbanned").setValue(false);
                                                infoPw.child("ban").setValue(0);
                                            }


                                        }

                                        else{
                                            Intent intent = new Intent(MainActivity.this, NewChatSearch.class); //나중에 채팅방 찾기로 바꾸기
                                            intent.putExtra("id",id2);
                                            intent.putExtra("dept",dept2);
                                            intent.putExtra("sid",sid2);
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
        Btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("id",id2);
                startActivity(intent);
            }
        });

    }
}