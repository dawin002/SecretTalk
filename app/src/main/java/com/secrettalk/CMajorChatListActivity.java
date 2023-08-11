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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class CMajorChatListActivity extends AppCompatActivity {

    private ListView ListView_itMajor, ListView_itMajor2;
    private TextView clubName;
    private int majorlist;
    private ArrayList<String> array1, array2;
    private LinearLayout listlayout;
    private View view;


    private String uid;
    private String dept, sid;

    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmajor_chat_list);

        ListView_itMajor = (ListView)findViewById(R.id.ListView_CMajor);
        ListView_itMajor2 = (ListView)findViewById(R.id.ListView_CMajor2);
        clubName = (TextView)findViewById(R.id.majorName);
        listlayout = (LinearLayout)findViewById(R.id.listlayout_major);

        array1 = new ArrayList<String>();
        array2 = new ArrayList<String>();

        array1.add("기계공학부");
        array1.add("전자공학과");
        array1.add("정보통신공학과");
        array1.add("로봇공학과");
        array1.add("전기공학과");
        array1.add("컴퓨터공학과");
        array1.add("자동차기계공학과");

        Intent gintent = getIntent(); /*데이터 수신*/
        uid = gintent.getExtras().getString("id"); /*String형*/
        dept = gintent.getExtras().getString("dept"); /*String형*/
        sid = gintent.getExtras().getString("sid"); /*String형*/


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array1);
        ListView_itMajor.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_itMajor.setAdapter(adapter);
        ListView_itMajor.setOnItemClickListener(onItemClickListener);

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array2);
        ListView_itMajor2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_itMajor2.setAdapter(adapter2);
        ListView_itMajor2.setOnItemClickListener(onItemClickListener2);


    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), array1.get(position), Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    clubName.setText("기계공학부");
                    majorlist = 0;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    clubName.setText("전자공학과");
                    majorlist = 1;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    clubName.setText("정보통신공학과");
                    majorlist = 2;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    clubName.setText("로봇공학과");
                    majorlist = 3;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    clubName.setText("전기공학과");
                    majorlist = 4;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    clubName.setText("컴퓨터공학과");
                    majorlist = 5;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    clubName.setText("자동차기계공학과");
                    majorlist = 6;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
            } // 학과 별 번호를 파이어베이스에 넘겨줘서 학과 구별
            array2.clear();
            array2.add("질문방");
            array2.add("21학번");
            array2.add("20학번");
            array2.add("19학번");
            array2.add("18학번");
            array2.add("17학번");
            array2.add("16학번");
            array2.add("15학번");
            array2.add("졸업자방");

            adapter2.notifyDataSetChanged();
        }
    };

    public void OnClickHandler(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("권한이 없습니다.");

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    AdapterView.OnItemClickListener onItemClickListener2 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), array2.get(position), Toast.LENGTH_SHORT).show();
            String chatName = array1.get(majorlist)+array2.get(position);

            Intent cintent = new Intent(CMajorChatListActivity.this, ChatActivity.class);
            cintent.putExtra("id",uid);
            cintent.putExtra("sid",sid);
            cintent.putExtra("dept",dept);
            if(position == 0 || position == 8){
                cintent.putExtra("chatName",chatName);
                startActivity(cintent);
            }else{

                if((Objects.equals(dept, array1.get(majorlist))) && (chatName.contains(sid))) {
                    cintent.putExtra("chatName",chatName);
                    startActivity(cintent);
                }else
                    OnClickHandler(view);
            }
        }
    };
}