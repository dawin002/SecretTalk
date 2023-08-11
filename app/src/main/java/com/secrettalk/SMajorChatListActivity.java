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

public class SMajorChatListActivity extends AppCompatActivity {

    private ListView ListView_scMajor, ListView_scMajor2;
    private TextView majorName;

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
        setContentView(R.layout.activity_smajor_chat_list);

        ListView_scMajor = (ListView)findViewById(R.id.ListView_SMajor);
        ListView_scMajor2 = (ListView)findViewById(R.id.ListView_SMajor2);
        majorName = (TextView)findViewById(R.id.majorName);
        listlayout = (LinearLayout)findViewById(R.id.listlayout_major);

        array1 = new ArrayList<String>();
        array2 = new ArrayList<String>();

        array1.add("수학과");
        array1.add("통계학과");
        array1.add("물리학과");
        array1.add("화학생화학과");
        array1.add("생명과학과");

        Intent intent = getIntent(); /*데이터 수신*/
        uid = intent.getExtras().getString("id"); /*String형*/
        dept = intent.getExtras().getString("dept"); /*String형*/
        sid = intent.getExtras().getString("sid"); /*String형*/

        System.out.println(sid);
        System.out.println(uid);
        System.out.println(dept);



        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array1);
        ListView_scMajor.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_scMajor.setAdapter(adapter);
        ListView_scMajor.setOnItemClickListener(onItemClickListener);

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array2);
        ListView_scMajor2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ListView_scMajor2.setAdapter(adapter2);
        ListView_scMajor2.setOnItemClickListener(onItemClickListener2);


    }
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), array1.get(position), Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    majorName.setText("수학과");
                    majorlist = 0;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    majorName.setText("통계학과");
                    majorlist = 1;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    majorName.setText("물리학과");
                    majorlist = 2;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    majorName.setText("화학생화학과");
                    majorlist = 3;
                    listlayout.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    majorName.setText("생명과학과");
                    majorlist = 4;
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

            Intent cintent = new Intent(SMajorChatListActivity.this, ChatActivity.class);
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