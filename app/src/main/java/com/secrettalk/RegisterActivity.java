package com.secrettalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private Button btn_register;
    private Button btn_college;
    private Button btn_dept;
    private Button btn_gender;

    public EditText et_name;
    public EditText et_id;
    public EditText et_password;
    public EditText et_password2;
    public EditText et_studentid;
    public EditText et_phonenumber;
    public EditText et_email;
    public EditText et_nickname;
    TextView tv_college;
    TextView tv_dept;
    TextView tv_gender;

    public static String id;
    String name;
    String password;
    String password2;
    String college = " ";
    String dept = " ";
    String studentid;
    String phonenumber;
    String gender = " ";
    String email;
    String nickname;
    Boolean isbanned;
    Boolean iscertified;
    int ban;
    long bandate;
    int deptIndex;

    int[] colnum = {0};
    int[] deptnum = {0};
    int[] gennum = {0};

    // String[] deptList = null;
    final String[] colList = new String[] { "문과대학", "자연과학대학", "공과대학", "기계IT대학", "정치행정대학",
            "상경대학", "경영대학", "의과대학", "약학대학", "생명응용과학대학", "생활과학대학", "사범대학", "디자인미술대학",
            "음악대학", "야간강좌개설부", "기초교육대학", "건축학부", "국제학부", "연합전공", "생명공학부" };
    final String[][] deptList = new String[][] {
            {"국어국문학과", "일어일문학과", "유럽언어문화학부", "역사학과", "심리학과", "언론정보학과",
                    "중어중문학과", "영어영문학과", "철학과", "문화인류학과", "사회학과"},
            {"수학과", "통계학과", "물리학과", "화학생화학과", "생명과학과"},
            {"건설시스템공학과", "도시공학과", "화학공학부", "환경공학과", "신소재공학부", "파이버시스템공학과"},
            {"기계공학부", "전기공학과", "전자공학과", "컴퓨터공학과", "정보통신공학과", "자동차기계공학과", "로봇공학과"},
            {"정치외교학과", "행정학과", "새마을국제개발학과", "경찰행정학과", "군사학과"},
            {"경제금융학부", "무역학부", "항공운송학과", "글로벌 차이나 연합전공"},
            {"경영학과", "회계세무학과", "글로벌 차이나 연합전공"},
            {"의예과", "의학과"},
            {"약학부"},
            {"식품경제외식학과", "원예생명과학과", "조경학과", "산림자원학과", "식품공학과", "생명공학과", "의생명공학과"},
            {"주거환경학과", "식품영양학과", "의류패션학과", "체육학부", "휴먼서비스학과"},
            {"교육학과", "국어교육과", "영어교육과", "한문교육과", "수학교육과", "유아교육과", "특수체육교육과"},
            {"회화과", "트랜스아트과", "시각디자인학과", "산업디자인학과", "생활제품디자인학과"},
            {"음악과", "성악과", "기악과(관현악)", "기악과(피아노)"},
            {"국어국문학과", "전기공학과", "정치외교학과", "행정학과", "새마을국제개발학과"},
            {"교양학부", "인문자율전공학부", "천마인재학부"},
            {"건축학부", "건축학(5년제)", "건축공학", "건축디자인"},
            {"국제학부"},
            {"글로벌 차이나", "그린에너지"},
            {"생명공학부"}
    };
    final String[] genList = new String[] {"남자", "여자"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("User");


        // 사용자가 입력한 값 받아오기
        et_name = findViewById(R.id.et_reg_name);
        et_id = findViewById(R.id.et_reg_id);
        et_password = findViewById(R.id.et_reg_password);
        et_password2 = findViewById(R.id.et_reg_password2);
        et_studentid = findViewById(R.id.et_reg_studentid);
        et_phonenumber = findViewById(R.id.et_reg_phonenumber);
        et_email = findViewById(R.id.et_reg_email);
        et_nickname = findViewById(R.id.et_reg_nickname);

        tv_college = (TextView) findViewById(R.id.tv_reg_college);
        tv_dept = (TextView) findViewById(R.id.tv_reg_dept);
        tv_gender = (TextView) findViewById(R.id.tv_reg_gender);

        btn_college = (Button) findViewById(R.id.btn_reg_collge);
        btn_dept = (Button) findViewById(R.id.btn_reg_dept);
        btn_gender = (Button) findViewById(R.id.btn_reg_gender);
        btn_register = findViewById(R.id.btn_reg_register);

        tv_college.setText("소속 단과대학을 선택하세요");
        tv_dept.setText("소속 학과를 선택하세요");
        tv_gender.setText("성별을 선택하세요");


        btn_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("선택").setSingleChoiceItems(colList, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colnum[0] = which;
                    }
                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterActivity.this, colList[colnum[0]], Toast.LENGTH_SHORT).show();
                                college = colList[colnum[0]];
                                tv_college.setText(college);
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });



        btn_dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("선택").setSingleChoiceItems(deptList[colnum[0]], 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deptnum[0] = which;
                    }
                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterActivity.this, deptList[colnum[0]][deptnum[0]], Toast.LENGTH_SHORT).show();
                                tv_dept.setText(deptList[colnum[0]][deptnum[0]]);
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });


        btn_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                dialog.setTitle("선택").setSingleChoiceItems(genList, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gennum[0] = which;
                    }
                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_gender.setText(genList[gennum[0]]);
                            }

                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                dialog.create();
                dialog.show();
            }
        });



        et_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userRef = database.getReference("User");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterator<DataSnapshot> child = snapshot.getChildren().iterator();

                        while (child.hasNext())
                        {
                            if (child.next().getKey().equals(et_id.getText().toString()))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("중복된 ID 입니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        et_password2.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!et_password2.getText().toString().equals(et_password.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Password가 일치하지 않습니다.");
                    et_password2.setText(null);
                    builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
            }
        });


        et_studentid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                userRef = database.getReference("User");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterator<DataSnapshot> child = snapshot.getChildren().iterator();

                        while (child.hasNext())
                        {
                            if (child.next().child("studentid").getValue().equals(et_studentid.getText().toString()))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("중복된 학번 입니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        et_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("-")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("하이픈(-) 없이 입력해주십시오.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

                userRef = database.getReference("User");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterator<DataSnapshot> child = snapshot.getChildren().iterator();

                        while (child.hasNext())
                        {
                            if (child.next().child("phonenumber").getValue().equals(et_phonenumber.getText().toString()))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("중복된 휴대폰 번호 입니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                userRef = database.getReference("User");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterator<DataSnapshot> child = snapshot.getChildren().iterator();

                        while (child.hasNext())
                        {
                            if (child.next().child("email").getValue().equals(et_email.getText().toString()))
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("중복된 이메일 입니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (    et_id.getText().toString().length() == 0
                        || et_password.getText().toString().length() == 0
                        || et_name.getText().toString().length() == 0
                        || et_phonenumber.getText().toString().length() == 0
                        || et_studentid.getText().toString().length() == 0
                        || et_email.getText().toString().length() == 0
                        || et_nickname.getText().toString().length() == 0
                        || et_email.getText().toString().length() == 0
                        || college.equals(" ")
                        || deptList[colnum[0]][deptnum[0]].equals(" ")
                        || genList[gennum[0]].equals(" ")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("작성되지 않은 정보가 있습니다.");
                    builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
                else if (!et_email.getText().toString().contains("@")
                        || !et_email.getText().toString().contains(".") ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("이메일 입력 양식이 올바르지 않습니다.");
                    builder.setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                    builder.show();
                }
                else {
                    // 회원가입 버튼 누르면 회원가입 입력 필드의 값을 get 해서 string 으로 바꾸고 저장
                    id = et_id.getText().toString();
                    name = et_name.getText().toString();
                    password = et_password.getText().toString();
                    password2 = et_password2.getText().toString();
                    dept = deptList[colnum[0]][deptnum[0]];
                    studentid = et_studentid.getText().toString();
                    phonenumber = et_phonenumber.getText().toString();
                    email = et_email.getText().toString();
                    gender = genList[gennum[0]];
                    nickname = et_nickname.getText().toString();

                    userRef = database.getReference("User");
                    userRef.child("" + id).child("id").setValue(id);


                    userRef = database.getReference("User");
                    userRef.child("" + id).child("id").setValue(id);
                    userRef = userRef.child("" + id);
                    userRef.child("password").setValue(password);
                    userRef.child("name").setValue(name);
                    userRef.child("dept").setValue(dept);
                    userRef.child("studentid").setValue(studentid);
                    userRef.child("phonenumber").setValue(phonenumber);
                    userRef.child("email").setValue(email);
                    userRef.child("gender").setValue(gender);
                    userRef.child("nickname").setValue(nickname);
                    userRef.child("isbanned").setValue(false);
                    userRef.child("iscertified").setValue(false);
                    userRef.child("ban").setValue(0);
                    userRef.child("bandate").setValue(0);

                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();

                    Intent saveIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    saveIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    saveIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    RegisterActivity.this.startActivity(saveIntent);
                }
            }
        });
    }
}