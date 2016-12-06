package com.example.guest.discussionforum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopicActivity extends AppCompatActivity {
    private DatabaseReference mTopicReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private TopicListAdapter mAdapter;

    public ArrayList<Topic> mTopics = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String topic = intent.getStringExtra("topic");

        mTopicReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_TOPIC);
        setUpFirebaseAdapter();

        getTopics(topic);
    }

    public void getTopics(String topic) {

    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Topic, FirebaseTopicViewHolder> (Topic.class, R.layout.topic_list_item, FirebaseTopicViewHolder.class, mTopicReference) {
            @Override
            protected void populateViewHolder(FirebaseTopicViewHolder viewHolder, Topic model, int position) {
                viewHolder.bindTopic(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
