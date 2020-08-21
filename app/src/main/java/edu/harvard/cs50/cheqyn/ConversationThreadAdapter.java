package edu.harvard.cs50.cheqyn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConversationThreadAdapter extends RecyclerView.Adapter<ConversationThreadAdapter.ConversationsThreadViewHolder> {
    //TODO: change this in for database data
    private List<CheckInThread> checkInThreads = new ArrayList<>();



    // The viewholder represents the structure of each individual item in the recyclerview list
    public class ConversationsThreadViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView threadTitleTextView;
        public TextView threadDateTextView;

        public ConversationsThreadViewHolder(@NonNull View view) {
            super(view);
            containerView = view.findViewById(R.id.thread_row);
            threadTitleTextView = view.findViewById(R.id.thread_title);
            threadDateTextView = view.findViewById(R.id.thread_nextdate);


            // Listener that handles clicking to go to individual conversation threads
            containerView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    CheckInThread thread = (CheckInThread) containerView.getTag();
                    Intent intent = new Intent(view.getContext(), ThreadActivity.class);
                    Intent.putExtra("id", thread.id);
                    Intent.putExtra("title", thread.title);
                    Intent.putExtra("date", thread.soonestDate);

                    context.startActivity(intent);
                }
            });
        }
    }

    public ConversationThreadAdapter(List<CheckInThread> myDataset){
        checkInThreads = myDataset;
    }

    @Override
    public ConversationsThreadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thread_row, parent, false);
        return new ConversationsThreadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConversationsThreadViewHolder holder, int position) {
        CheckInThread current = checkInThreads.get(position);
        holder.threadTitleTextView.setText(current.title);
        holder.threadDateTextView.setText(current.soonestDate);
        holder.containerView.setTag(current);

    }

    @Override
    public int getItemCount() {
        return checkInThreads.size();
    }
}
