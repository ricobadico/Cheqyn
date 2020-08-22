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

public class CheckinsAdapter extends RecyclerView.Adapter<CheckinsAdapter.CheckinsViewHolder> {

    //TODO: change this in for database data
    private List<CheckIn> checkIns = new ArrayList<>();

    // The viewholder represents the structure of each individual item in the recyclerview list
    public static class CheckinsViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout containerView;
        public TextView checkinTitleTextView;
        public TextView checkinDetailsTextView;

        public CheckinsViewHolder(@NonNull View view) {
            super(view);
            containerView = view.findViewById(R.id.checkins_row);
            checkinTitleTextView = view.findViewById(R.id.checkins_text1);
            checkinDetailsTextView = view.findViewById(R.id.checkins_text1);

                //todo add click to go to info/ data entry
//            // Listener that handles clicking to go to individual conversation threads
//            containerView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    Context context = view.getContext();
//                    CheckInThread thread = (CheckInThread) containerView.getTag();
//                    Intent intent = new Intent(view.getContext(), CheckinsActivity.class);
//                    Intent.putExtra("id", thread.id);
//                    Intent.putExtra("title", thread.title);
//                    Intent.putExtra("date", thread.soonestDate);
//
//                    context.startActivity(intent);
//                }
//            });
        }
    }

    public CheckinsAdapter(List<CheckIn> currentDataset){
        checkIns = currentDataset;
    }

    @Override
    public CheckinsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkins_row, parent, false);
        return new CheckinsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckinsAdapter.CheckinsViewHolder holder, int position) {
        CheckIn current = checkIns.get(position);
        holder.checkinTitleTextView.setText(current.date);
        holder.checkinDetailsTextView.setText("Placeholder");
        holder.containerView.setTag(current);

    }

    @Override
    public int getItemCount() {
        return checkIns.size();
    }
}
