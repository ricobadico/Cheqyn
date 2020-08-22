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
import java.util.List;

public class ConversationThreadAdapter extends RecyclerView.Adapter<ConversationThreadAdapter.ConversationsThreadViewHolder> {
    //TODO: change this in for database data
    private List<CheckInThread> checkInThreads = new ArrayList<>();



    // The viewholder represents the structure of each individual item in the recyclerview list
    public class ConversationsThreadViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView threadTitleTextView;
        public TextView threadDateTextView;

        ConversationsThreadViewHolder(@NonNull View view) {
            super(view);
            this.containerView = view.findViewById(R.id.thread_row);
            this.threadTitleTextView = view.findViewById(R.id.thread_title);
            this.threadDateTextView = view.findViewById(R.id.thread_nextdate);


            // Listener that handles clicking to go to individual conversation threads
            this.containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    CheckInThread thread = (CheckInThread) containerView.getTag();
                    Intent intent = new Intent(view.getContext(), CheckinsActivity.class);
                    intent.putExtra("id", thread.id);
                    intent.putExtra("title", thread.title);
                    intent.putExtra("date", thread.soonestDate);

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
