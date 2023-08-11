package com.secrettalk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



@SuppressWarnings("ALL")
public class ChatActivity extends AppCompatActivity {

    private ArrayList<ChatData> dataList;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText editText_chat;

    //유저 정보 담기
    private ArrayList<String> arrayList1;


    // 정보
    private String msg;
    private String uid = "uid2";
    private String chatName;
    private String sid, dept;

    // 이미지
    private ImageButton image_btn;
    private ImageView image_view;
    private Button sendButton;
    private final int SELECT_IMAGE = 100;

    //파이어베이스
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference, databaseReference1, databaseReference2;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        dataList = new ArrayList<>();
        arrayList1 = new ArrayList<>();
        dataList.clear();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent gintent = getIntent(); //데이터 수신
        uid = gintent.getExtras().getString("id"); //*String형
        chatName = gintent.getExtras().getString("chatName"); //*String형
        sid = gintent.getExtras().getString("sid");
        dept = gintent.getExtras().getString("dept");


        // xml 매칭
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        EditText editText_chat = findViewById(R.id.EditText_chat);
        TextView chatNameText = findViewById(R.id.textView4);

        chatNameText.setText(chatName);

        // 이미지
        image_btn = findViewById(R.id.image_btn);
        sendButton = findViewById(R.id.Button_send);
        image_view = findViewById(R.id.image_view);
        storage = FirebaseStorage.getInstance();

        databaseReference = firebaseDatabase.getReference();

        ////////////////////////////////////////내 채팅방 목록 불러오기 arraylist1에
        databaseReference2 = firebaseDatabase.getReference("User").child(uid);

        databaseReference2.child("chatList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 db에서 받아오는 곳
                arrayList1.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String user = snapshot.getValue().toString(); // 데이터 리스트 추출
                    arrayList1.add(user); // 배열에 추가
                }
                if(!arrayList1.contains(chatName)) {
                    HashMap<String, Object> childUpdates = new HashMap<>();
                    HashMap<String, String> chatValue = new HashMap<>(); //업데이트 된 데이터 가지고 있음
                    Map<String, Object> useValue = null; //업데이트 된 데이터 가지고 있음
                    arrayList1.add(chatName);
                    for (int i = 0; i < arrayList1.size(); i++) {
                        chatValue.put(Integer.toString(i), arrayList1.get(i));
                    }
                    childUpdates.put("chatList/", chatValue);
                    databaseReference2.updateChildren(childUpdates);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //디비를 가져오던중 에러 발생 시
                System.out.println("db1 에러발생");
            }

        });

        // 전송 버튼
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = editText_chat.getText().toString();
                if(msg != null) {
                    ChatData chat = new ChatData(checkBadword(msg), uid, toDate(System.currentTimeMillis()), 0, sid, dept);
                    databaseReference.child("Chat").child(chatName).child("message").push().setValue(chat);
                }
                editText_chat.setText("");
            }

        });

        // 이미지 전송 버튼
        image_btn.setOnClickListener(v -> {
            Intent imageIntent = new Intent(Intent.ACTION_PICK);
            imageIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(imageIntent, SELECT_IMAGE);
        });

        //채팅 내용 읽어들임
        databaseReference1 = firebaseDatabase.getReference("Chat").child(chatName).child("message");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    ChatData chat = dataSnapshot.getValue(ChatData.class);
                    dataList.add(chat);
                }
                dataList = re_viewtype(dataList, uid);
                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(dataList.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DB fail");
            }
        });

        mAdapter = new ChatAdapter(dataList, uid);

        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(mAdapter);  // Adapter 등록

        mAdapter.setOnLongItemClickListener(new ChatAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(View v, int position) {
                ChatData myDataList = dataList.get(position);
                String nickname = myDataList.getDept()+"/"+myDataList.getSid()+"/"+myDataList.getName();
                if (!(uid.equals(nickname))) {
                    //Toast.makeText(getApplicationContext(), "프로필", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), ProfilePopupActivity.class);
                    intent.putExtra("target_nick",nickname);
                    intent.putExtra("my_nick",uid);
                    startActivityForResult(intent,1);
                }
            }
        });

    }
    public String[] badword = {"fuck","shit","bitch"};
    String str1 = new String();
    public String checkBadword(String str){
        str1 = str;
        for (int i = 0; i < 3; i++){
            if (str1.toLowerCase().contains(badword[i])) {
                str1 = str1.toLowerCase().replace(badword[i], "**");
            }
        }
        return str1;
    }
    // System.currentTimeMillis를 몇시:몇분 am/pm 형태의 문자열로 반환
    private String toDate(long currentMiliis) {
        return new SimpleDateFormat("hh:mm a").format(new Date(currentMiliis));
    }

    // 이미지를 선택했을 때의 이벤트
    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_IMAGE) {
            Uri file = data.getData();
            StorageReference storageRef = storage.getReference();
            StorageReference riverRef = storageRef.child("photo/1.png");
            UploadTask uploadTask = riverRef.putFile(file);
            databaseReference = firebaseDatabase.getReference();

            try {
                InputStream in =
                        getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                ChatData chat = new ChatData(null, uid, BitmapToString(img), toDate(System.currentTimeMillis()), 2, sid, dept);
                databaseReference.child("Chat").child(chatName).child("message").push().setValue(chat);
//                image_view.setImageBitmap(img);
            } catch(Exception e){
                e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ChatActivity.this, "사진이 정상적으로 업로드 되지 않았습니다." , Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ChatActivity.this, "사진이 정상적으로 업로드 되었습니다." ,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 뷰타입 구분 ( 왼 텍스트, 오 텍스트, 왼 이미지, 오 이미지 ) // 센터는 구현 중단함.
    public ArrayList<ChatData> re_viewtype(ArrayList<ChatData> myDataList, String uid){
        for(int i = 0; i < myDataList.size(); i++){
            if(Objects.equals(uid, myDataList.get(i).getName())){
                if(Objects.equals(0,myDataList.get(i).getViewType())){
                    myDataList.get(i).setViewType(1);
                }else{
                    myDataList.get(i).setViewType(3);
                }
            }
        }
        return myDataList;
    }
    
    // 이미지 비트맵 -> 스트링 변환 함수
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //바이트 배열을 차례대로 읽어 들이기위한 ByteArrayOutputStream클래스 선언
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);//bitmap을 압축 (숫자 70은 70%로 압축한다는 뜻)
        byte[] bytes = baos.toByteArray();//해당 bitmap을 byte배열로 바꿔준다.
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);//Base 64 방식으로byte 배열을 String으로 변환
        return temp;//String을 retrurn
    }

}