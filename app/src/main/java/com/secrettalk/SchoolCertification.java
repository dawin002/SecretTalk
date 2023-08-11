package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class SchoolCertification extends AppCompatActivity {

    private GMailSender m;
    private DatabaseReference Ref;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolcertification);

        Intent intent = getIntent();
        String id2 = intent.getExtras().getString("id");

        Button Btn_send = (Button) this.findViewById(R.id.Btn_send);
        EditText EditText_address = (EditText)this.findViewById(R.id.EditText_address);
        Button Btn_verify = (Button) this.findViewById(R.id.Btn_verify);
        EditText EditText_verifycode = (EditText)this.findViewById(R.id.EditText_verifycode);


        //code 생성기
        int code = (int) (Math.random() * 1000000) + 1;
        int second = 0;





        Btn_send.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                    GMailSender sender = new GMailSender("hyo040441@gmail.com", "lhuguszmgxnwcbva"); // 보내는 사람 구글 계정





                if (android.os.Build.VERSION.SDK_INT > 9)
                {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();

                    StrictMode.setThreadPolicy(policy);

                }
                // HERE
                try
                {
                    sender.sendMail("Secret Talk 인증 코드 메일입니다.", // 메일 제목
                            "인증 코드 번호 : " + code, // 메일 내용
                            "SecretTalk", // 보내는 사람
                            EditText_address.getText().toString() + "@ynu.ac.kr" // to.getText().toString()
                    );

                    toast();
                } catch (Exception e)
                {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });




        Btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code2 = Integer.toString(code);
                Ref = database.getReference("User");
                if(code2.equals(EditText_verifycode.getText().toString())){

                    Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterator<DataSnapshot> child = snapshot.getChildren().iterator();
                            while(child.hasNext()){
                                if(child.next().getKey().equals(id2)){
                                    DatabaseReference infoPw = database.getReference("User/" + id2);
                                    infoPw.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            final User user = snapshot.getValue(User.class);
                                            infoPw.child("iscertified").setValue(true);
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



                    Toast.makeText(SchoolCertification.this,"인증되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(SchoolCertification.this,"실패했습니다.",Toast.LENGTH_SHORT).show();

                }





            }
        });
    }



    public void toast()
    {
        Toast.makeText(this, "전송되었습니다.", Toast.LENGTH_SHORT).show();
    }



    }
