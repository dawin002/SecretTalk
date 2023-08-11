package com.secrettalk;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyChatListActivity extends AppCompatActivity implements OnItemClick{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MyChatReViewItem> arrayList;
    private ArrayList<String> arrayList1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,databaseReference1;
    private String uid, dept, sid;

    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList1 = new ArrayList<>();

        Intent gintent = getIntent(); /*데이터 수신*/
        uid = gintent.getExtras().getString("id"); /*String형*/
        dept = gintent.getExtras().getString("dept"); /*String형*/
        sid = gintent.getExtras().getString("sid"); /*String형*/

        database = FirebaseDatabase.getInstance(); //파이어베이스 데이터 연동

        ////////////////////////////////////////내 채팅방 목록 불러오기 arraylist1에
        databaseReference1 = database.getReference("User").child(uid);

        databaseReference1.child("chatList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 db에서 받아오는 곳
                arrayList1.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String user = snapshot.getValue().toString(); // 데이터 리스트 추출
                    arrayList1.add(user); // 배열에 추가
                    System.out.println(user);

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던중 에러 발생 시
                System.out.println("db1 에러발생");
            }

        });

        /////////////////////////////////// 전체 채팅 목록 불러와서 화면에 띄우기
        databaseReference = database.getReference("Chat");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 db에서 받아오는 곳
                arrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MyChatReViewItem chat = snapshot.getValue(MyChatReViewItem.class); // 데이터 리스트 추출
                    if(arrayList1.contains(chat.getChatName())){
                        arrayList.add(chat); // 배열에 추가
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던중 에러 발생 시
                System.out.println("db 에러발생");
            }

        });

        adapter = new MyChatAdapter(arrayList, this, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(String value,int a, int position) {
        if(a==0) {
            // arraylist1을 바꿔서 데이터베이스에 업데이트
            HashMap<String, Object> childUpdates = new HashMap<>();
            HashMap<String, String> chatValue = new HashMap<>(); //업데이트 된 데이터 가지고 있음
            Map<String, Object> useValue = null; //업데이트 된 데이터 가지고 있음

            arrayList1.remove(value);
            for(int i =0 ; i<arrayList1.size(); i++){
                chatValue.put(Integer.toString(i), arrayList1.get(i));
            }
            childUpdates.put("chatList/", chatValue);
            databaseReference1.updateChildren(childUpdates);

        }else if(a==1){
            //value에서 받아온 값으로 intent
            System.out.println(value);
            Intent intent = new Intent(MyChatListActivity.this, ChatActivity.class);
            intent.putExtra("chatName",value);
            intent.putExtra("id",uid);
            intent.putExtra("dept", dept);
            intent.putExtra("sid", sid);
            startActivity(intent);
        }

    }
}