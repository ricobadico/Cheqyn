package edu.harvard.cs50.cheqyn;

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

            //ToDO: set onClickListener to open that thread
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

    }

    @Override
    public int getItemCount() {
        return checkInThreads.size();
    }
}
