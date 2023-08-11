package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SearchListActivity extends AppCompatActivity {

    private ListView ListView_university;



//    ArrayAdapter<String> adapter;

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter1;      // 리스트뷰에 연결할 아답터
    private HashMap<String, String> mapData;
    private ArrayList<String> arrayData;
    private String uid, sid;
    private String dept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        editSearch = (EditText) findViewById(R.id.editSearch);
        listView = (ListView) findViewById(R.id.listView);

        mapData = new HashMap<String, String>();
        arrayData = new ArrayList<String>();

        Intent intent = getIntent(); /*데이터 수신*/
        uid = intent.getExtras().getString("id"); /*String형*/
        dept = intent.getExtras().getString("dept"); /*String형*/
        sid = intent.getExtras().getString("sid");

        list = new ArrayList<String>();

        settingMapData(dept);

        Set<String> keySet = mapData.keySet();

        for( String key : keySet )
        {
            list.add(mapData.get(key));
            arrayData.add(mapData.get(key));
        } //mapData.get("이름") 으로 가져올수 있음


        adapter1 = new SearchAdapter(list, this);
        listView.setAdapter(adapter1);


        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(onItemClickListener);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = editSearch.getText().toString();
                search(text);
            }
        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arrayData);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arrayData.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arrayData.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arrayData.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter1.notifyDataSetChanged();
    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Toast.makeText(getApplicationContext(), arrayData.get(position), Toast.LENGTH_SHORT).show();
            Intent cintent = new Intent(SearchListActivity.this, ChatActivity.class);
            cintent.putExtra("id", uid);

            if (list.get(position).contains(dept)) {
                if(list.get(position).contains(sid) || list.get(position).contains("질문")) {
                    cintent.putExtra("chatName", list.get(position));
                    cintent.putExtra("sid", sid);
                    cintent.putExtra("dept", dept);
                    startActivity(cintent);
                }else{
                    OnClickHandler(view);
                }
            }else{
                cintent.putExtra("chatName", list.get(position));
                startActivity(cintent);
            }

        }
    };

    private void settingMapData(String dept){
        if(Objects.equals(dept, "컴퓨터공학과")) {
            mapData.put("01", "컴퓨터공학과21학번");
            mapData.put("02", "컴퓨터공학과20학번");
            mapData.put("03", "컴퓨터공학과19학번");
            mapData.put("04", "컴퓨터공학과18학번");
            mapData.put("05", "컴퓨터공학과17학번");
            mapData.put("06", "컴퓨터공학과16학번");
            mapData.put("07", "컴퓨터공학과15학번");
        }else if(Objects.equals(dept, "수학과")) {
            mapData.put("11", "수학과21학번");
            mapData.put("12", "수학과20학번");
            mapData.put("13", "수학과19학번");
            mapData.put("14", "수학과18학번");
            mapData.put("15", "수학과17학번");
            mapData.put("16", "수학과16학번");
            mapData.put("17", "수학과15학번");
        }else if(Objects.equals(dept, "통계학과")) {
            mapData.put("21", "통계학과21학번");
            mapData.put("22", "통계학과20학번");
            mapData.put("23", "통계학과19학번");
            mapData.put("24", "통계학과18학번");
            mapData.put("25", "통계학과17학번");
            mapData.put("26", "통계학과16학번");
            mapData.put("27", "통계학과15학번");
        }else if(Objects.equals(dept, "화학과")) {
            mapData.put("31", "화학과21학번");
            mapData.put("32", "화학과20학번");
            mapData.put("33", "화학과19학번");
            mapData.put("34", "화학과18학번");
            mapData.put("35", "화학과17학번");
            mapData.put("36", "화학과16학번");
            mapData.put("37", "화학과15학번");
        }
        mapData.put("70", "기우회");
        mapData.put("71","석우회");
        mapData.put("72","스타일러스");
        mapData.put("73","애견클럽");
        mapData.put("74","자놀자");
        mapData.put("75","자유의지");
        mapData.put("76","위더스");
        mapData.put("77","아베스타");
        mapData.put("78","천마회");
        mapData.put("79","한울회");
        mapData.put("80","아트리");
        mapData.put("81","호우회");
        mapData.put("82","G.F.C");
        mapData.put("83","천마 LIFE-LINE");
        mapData.put("84","K.U.S.A");
        mapData.put("85","해오름");
        mapData.put("86","영지회");
        mapData.put("87","작은사랑큰기쁨");
        mapData.put("00", "컴퓨터공학과질문방");
        mapData.put("10", "수학과질문방");
        mapData.put("20", "통계학과질문방");
        mapData.put("30", "화학과질문방");
    }
    public void OnClickHandler(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("권한이 없습니다.");

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}
