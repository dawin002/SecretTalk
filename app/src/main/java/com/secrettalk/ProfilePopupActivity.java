
package com.secrettalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePopupActivity extends Activity {
    TextView txtUserid;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public String target_nick;
    public String user_nick;
    Button btnBlock;
    Button btnReport;
    Button btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profilepopup_activity);

        //UI 객체생성
        txtUserid = (TextView)findViewById(R.id.txtUserid);
        btnBlock = (Button)findViewById(R.id.btnBlock);
        btnReport = (Button)findViewById(R.id.btnReport);
        btnChat = (Button)findViewById(R.id.btnChat);
        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("target_nick");
        String data2 = intent.getStringExtra("my_nick");
        txtUserid.setText(data);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        target_nick = data;
        user_nick = data2;
    }

    //확인 버튼 클릭
    public void mOnBlock(View v){
        //데이터 전달하기
        //Intent intent = new Intent();
        //intent.putExtra("result", "Close Popup");
        //setResult(RESULT_OK, intent);
        //Toast.makeText(getApplicationContext(), "차단",Toast.LENGTH_SHORT).show();
        myRef.child("block").child(user_nick).push().setValue(target_nick);
        //액티비티(팝업) 닫기
        finish();
    }

    public void mOnReport(View v){
        //데이터 전달하기
        //Intent intent = new Intent();
        //intent.putExtra("result", "Close Popup");
        //setResult(RESULT_OK, intent);
        myRef.child("report").child(target_nick).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Integer.class) == null)
                    myRef.child("report").child(target_nick).setValue(1);
                else
                    myRef.child("report").child(target_nick).setValue((snapshot.getValue(Integer.class))+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //액티비티(팝업) 닫기
        finish();
    }

    public void mOnChat(View v){
        //데이터 전달하기
        //Intent intent = new Intent();
        //intent.putExtra("result", "Close Popup");
        //setResult(RESULT_OK, intent);
        Toast.makeText(getApplicationContext(), "1:1채팅",Toast.LENGTH_SHORT).show();
        //액티비티(팝업) 닫기
        finish();
    }




}
