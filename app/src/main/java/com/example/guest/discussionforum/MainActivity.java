package com.example.guest.discussionforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mTopicReference;
    private ValueEventListener mTopicReferenceListener;
    @Bind(R.id.addTopicButton) Button mAddTopicButton;
    @Bind(R.id.topicCreation) EditText mTopicCreation;
    @Bind(R.id.topicActivityButton) Button mTopicActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mTopicReference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_TOPIC);

        mTopicReferenceListener = mTopicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot topicSnapshot : dataSnapshot.getChildren()) {
                    String topic = topicSnapshot.getValue().toString();
                    Log.d("Topics updated", "topic: " + topic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTopicActivityButton.setOnClickListener(this);
        mAddTopicButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mTopicActivityButton) {
            Intent intent = new Intent(MainActivity.this, TopicActivity.class);
            startActivity(intent);
        }
        if(v == mAddTopicButton) {
            String topic = mTopicCreation.getText().toString();
            saveTopicToFirebase(topic);
            Intent intent = new Intent(MainActivity.this, TopicActivity.class);
            intent.putExtra("topic", topic);
            startActivity(intent);
        }
    }
    public void saveTopicToFirebase(String topic) {
        mTopicReference.push().setValue(topic);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTopicReference.removeEventListener(mTopicReferenceListener);
    }
}
