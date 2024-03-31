package com.example.giuaky;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText id, mt,ms;
    private Button push,logout;
    private RecyclerView rcvUser;
    private  UserAdapter mUserAdapter;
    private List<User> mlistuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdduser();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                GoogleSignIn.getClient(MainActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
       getUser();


    }

    private void initUi() {
        id=findViewById(R.id.tvID);
        mt=findViewById(R.id.tvMattrc);
        ms=findViewById(R.id.tvMatsau);
        push=findViewById(R.id.pushdata);
        logout=findViewById(R.id.logout);
        rcvUser=findViewById(R.id.rckview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(dividerItemDecoration);

         mlistuser=new ArrayList<>();
         mUserAdapter=new UserAdapter(this);
         mUserAdapter.setdata(mlistuser);
         rcvUser.setAdapter(mUserAdapter);
    }

    private void onClickAdduser() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("DataFlast");
        int Id = Integer.parseInt(id.getText().toString().trim());
        String mtrc = mt.getText().toString().trim();
        String msau = ms.getText().toString().trim();
        User user = new User(Id,mtrc,msau);
        reference.child(String.valueOf(user.getId())).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(MainActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("DataFlast");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot sdn : snapshot.getChildren()){
//                    User user = sdn.getValue(User.class);
//                    mlistuser.add(user);
//
//                }
//                mUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity.this,"Lỗi",Toast.LENGTH_SHORT).show();
//            }
//        });
        mlistuser.add(new User(3,"A","B"));
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(mlistuser!=null){
                    mlistuser.add(user);
                    mUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}