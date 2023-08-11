package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewChatSearch extends AppCompatActivity {
    private Button Button_ClubChatList, Button_UniversityChatList, Button_SearchChatList;

    private String uid;
    private String dept, sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat_search);

        Button_ClubChatList = (Button) findViewById(R.id.Button_ClubChatList);
        Button_UniversityChatList = (Button) findViewById(R.id.Button_UniversityChatList);
        Button_SearchChatList = (Button) findViewById(R.id.Button_SearchChatList);

        Intent intent = getIntent(); /*데이터 수신*/
        uid = intent.getExtras().getString("id"); /*String형*/
        dept = intent.getExtras().getString("dept"); /*String형*/
        sid = intent.getExtras().getString("sid");

        Button_ClubChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cintent = new Intent(NewChatSearch.this,ClubListActivity.class);
                cintent.putExtra("id",uid);
                cintent.putExtra("dept",dept);
                cintent.putExtra("sid",sid);
                startActivity(cintent);

            }
        });
        Button_UniversityChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aintent = new Intent(NewChatSearch.this,UniversityListActivity.class);
                aintent.putExtra("id",uid);
                aintent.putExtra("dept",dept);
                aintent.putExtra("sid",sid);
                startActivity(aintent);

            }
        });
        Button_SearchChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(NewChatSearch.this,SearchListActivity.class);
                bintent.putExtra("id",uid);
                bintent.putExtra("dept",dept);
                bintent.putExtra("sid",sid);
                startActivity(bintent);

            }
        });
    }
}