package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClubListActivity extends AppCompatActivity {

    private ListView ListView_club, ListView_club2;
    private TextView clubName;
    private int clublist;
    private ArrayList<String> array1, array2;
    private LinearLayout listlayout;
    private View view;

    private String uid, dept, sid;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        ListView_club = (ListView)findViewById(R.id.ListView_club);
        ListView_club2 = (ListView)findViewById(R.id.ListView_club2);
        clubName = (TextView)findViewById(R.id.clubName);
        listlayout = (LinearLayout)findViewById(R.id.listlayout);

        array1 = new ArrayList<String>();
        array2 = new ArrayList<String>();

        Intent intent = getIntent(); /*데이터 수신*/
        uid = intent.getExtras().getString("id"); /*String형*/
        dept = intent.getExtras().getString("dept"); /*String형*/
        sid = intent.getExtras().getString("sid"); /*String형*/


        array1.add("교양분과");
        array1.add("봉사분과");
        array1.add("체육분과");
        array1.add("예술분과");
        array1.add("어학분과");
        array1.add("학술분과");
        array1.add("응용학술분과");
        array1.add("종교분과");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array1);
        ListView_club.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_club.setAdapter(adapter);
        ListView_club.setOnItemClickListener(onItemClickListener);

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array2);
        ListView_club2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_club2.setAdapter(adapter2);
        ListView_club2.setOnItemClickListener(onItemClickListener2);

    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(),array1.get(position), Toast.LENGTH_SHORT).show();
            switch (position){
                case 0:
                    clubName.setText("교양분과");
                    clublist = 0;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    clubName.setText("봉사분과");
                    clublist = 1;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    clubName.setText("체육분과");
                    clublist = 2;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    clubName.setText("예술분과");
                    clublist = 3;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    clubName.setText("어학분과");
                    clublist = 4;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    clubName.setText("학술분과");
                    clublist = 5;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    clubName.setText("응용학술분과");
                    clublist = 6;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    clubName.setText("종교분과");
                    clublist = 7;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
            }
            array2.clear();
            switch (clublist){
                case 0:
                    array2.add("기우회");
                    array2.add("석우회");
                    array2.add("스타일러스");
                    array2.add("애견클럽");
                    array2.add("자놀자");
                    array2.add("자유의지");
                    break;
                case 1:
                    array2.add("위더스");
                    array2.add("아베스타");
                    array2.add("천마회");
                    array2.add("한울회");
                    array2.add("아트리");
                    array2.add("호우회");
                    array2.add("G.F.C");
                    array2.add("천마 LIFE-LINE");
                    array2.add("K.U.S.A");
                    array2.add("해오름");
                    array2.add("영지회");
                    array2.add("작은사랑큰기쁨");
                    array2.add("스카우트");
                    array2.add("그루터기");
                    break;
                case 2:
                    array2.add("검도부");
                    array2.add("다이나마이트");
                    array2.add("문무반");
                    array2.add("아카데믹스");
                    array2.add("영남대농구반");
                    array2.add("한마음");
                    array2.add("S.F.A");
                    array2.add("탐험대");
                    array2.add("페가수스");
                    array2.add("YUTA");
                    array2.add("탁우회");
                    array2.add("YUBC");
                    array2.add("Hi-Clear");
                    array2.add("유도사랑");
                    array2.add("산악회");
                    array2.add("E.V.E");
                    break;
                case 3:
                    array2.add("HO-SA");
                    array2.add("영화공간");
                    array2.add("천마극단");
                    array2.add("육현");
                    array2.add("예사가락");
                    array2.add("Y.E.P");
                    array2.add("MAX&ZENITH");
                    array2.add("The WE");
                    array2.add("Echoes");
                    array2.add("BLUE WAVE");
                    array2.add("천마응원단");
                    array2.add("COSMOS");
                    array2.add("c(ARTOON)");
                    array2.add("힙합컴퍼니");
                    array2.add("사우회");
                    array2.add("신명마당");
                    array2.add("영남대 합창단");
                    array2.add("영남대풍물패연합");
                    break;
                case 4:
                    array2.add("TIME");
                    array2.add("KO-JACOS");
                    array2.add("DALA");
                    array2.add("PTC");
                    array2.add("KABS");
                    array2.add("IN SPANISH");
                    break;
                case 5:
                    array2.add("해시태그");
                    array2.add("청탑");
                    array2.add("상문");
                    array2.add("필드워크");
                    array2.add("SELV");
                    array2.add("4-H");
                    break;
                case 6:
                    array2.add("LIKE BIKE");
                    array2.add("그리니치");
                    array2.add("대액학회");
                    array2.add("천마OM");
                    array2.add("사고뭉치");
                    break;
                case 7:
                    array2.add("중산도");
                    array2.add("돌샘");
                    array2.add("S.F.C");
                    array2.add("I.V.F");
                    array2.add("아뉴스");
                    array2.add("GLBP");
                    array2.add("대각회");
                    array2.add("영남대CCC");
                    break;
            }
            adapter2.notifyDataSetChanged();
        }
    };
    AdapterView.OnItemClickListener onItemClickListener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), array2.get(position), Toast.LENGTH_SHORT).show();
            Intent cintent = new Intent(ClubListActivity.this, ChatActivity.class);
            cintent.putExtra("id",uid);
            cintent.putExtra("chatName",array2.get(position));
            cintent.putExtra("sid",sid);
            cintent.putExtra("dept",dept);
            System.out.println(array2.get(position));
            startActivity(cintent);
        }
    };
}