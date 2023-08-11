package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UniversityListActivity extends AppCompatActivity {

    private ListView ListView_university;


    private ArrayList<String> data_university;

    private ArrayAdapter<String> adapter;

    String uid;
    String dept, sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university_list);

        Intent intent = getIntent(); /*데이터 수신*/
        uid = intent.getExtras().getString("id"); /*String형*/
        dept = intent.getExtras().getString("dept"); /*String형*/
        sid = intent.getExtras().getString("sid");


        data_university = new ArrayList<String>();

        data_university.add("문과대학");
        data_university.add("자연과학대학");
        data_university.add("공과대학");
        data_university.add("기계IT대학");
        data_university.add("정치행정대학");
        data_university.add("상경대학");
        data_university.add("경영대학");
        data_university.add("의과대학");
        data_university.add("약학대학");
        data_university.add("생명응용과학대학");
        data_university.add("생활과학대학");
        data_university.add("사범대학");
        data_university.add("디자인미술대학");
        data_university.add("음악대학");
        data_university.add("야간강좌개설부");
        data_university.add("기초교육대학");
        data_university.add("건축학부");
        data_university.add("국제학부");
        data_university.add("연합전공");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data_university);

        ListView_university = (ListView) findViewById(R.id.ListView_university);
        ListView_university.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_university.setAdapter(adapter);

        ListView_university.setOnItemClickListener(onItemClickListener);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), data_university.get(position), Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    break;
                case 1:
                    Intent sintent = new Intent(UniversityListActivity.this, SMajorChatListActivity.class);
                    sintent.putExtra("id",uid);
                    sintent.putExtra("dept",dept);
                    sintent.putExtra("sid", sid);
                    startActivity(sintent);
                    break;
                case 2:
                    break;
                case 3:
                    Intent cintent = new Intent(UniversityListActivity.this, CMajorChatListActivity.class);
                    cintent.putExtra("id",uid);
                    cintent.putExtra("dept",dept);
                    cintent.putExtra("sid", sid);
                    startActivity(cintent);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        }
    };
}