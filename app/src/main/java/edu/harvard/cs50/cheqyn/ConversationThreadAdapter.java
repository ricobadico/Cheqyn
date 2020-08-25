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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        holder.threadDateTextView.setText(current.soonestDate.toString());
        holder.containerView.setTag(current);

    }

    @Override
    public int getItemCount() {
        return checkInThreads.size();
    }

    public void reload() {

        soonestDateUpdater(MainActivity.database.threadDao().getAllThreads());
        checkInThreads = MainActivity.database.threadDao().getAllFutureThreads(Calendar.getInstance().getTime());
        notifyDataSetChanged();

    }

    public void reload2() {

        checkInThreads = MainActivity.database.threadDao().getAllPastThreads(Calendar.getInstance().getTime());
        notifyDataSetChanged();

    }

    // Update database to ensure we have the soonest date among upcoming threads
    public void soonestDateUpdater(List<CheckInThread> threads){
        for(int i = 0; i < threads.size(); i++) {
            CheckInThread currentThread = threads.get(i);
            //Get soonest future date among checkins in currently iterated thread
            Date trueSoonestDate = MainActivity.database.threadDao().getSoonestDate(currentThread.id, Calendar.getInstance().getTime());
            // if no future dates, we don't want to update this value
            if(trueSoonestDate != null){
                MainActivity.database.threadDao().updateThreadSoonestDate(trueSoonestDate, currentThread.id);
            }
        }
    }

}
