package edu.harvard.cs50.cheqyn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class CheckinFilloutAdapter extends RecyclerView.Adapter<CheckinFilloutAdapter.CheckinFilloutViewholder> {
    private List<CheckinFields> fields;
    private int rootThreadId;

    public static class CheckinFilloutViewholder extends RecyclerView.ViewHolder {

        public LinearLayout containerView;
        public TextView fieldTitle;
        public EditText fieldEditText;

        public CheckinFilloutViewholder(@NonNull View itemView) {
            super(itemView);
            containerView = itemView.findViewById(R.id.fillout_row);
            fieldTitle = itemView.findViewById(R.id.fillout_field_title);
            fieldEditText = itemView.findViewById(R.id.fillout_field_input);
        }
    }

    public CheckinFilloutAdapter(List<CheckinFields> currentDataset, int threadId){
        fields = currentDataset;
        rootThreadId = threadId;
    }

    @NonNull
    @Override
    public CheckinFilloutViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fillout_row, parent, false);
        return new CheckinFilloutViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckinFilloutViewholder holder, int position) {
        CheckinFields current = fields.get(position);
        holder.fieldTitle.setText(current.fieldTitle);
        holder.fieldEditText.setText(current.fieldData);
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    public void reload() {
        fields = MainActivity.database.threadDao().getFields(rootThreadId); //todo change to gettFields with rootThreadId
        notifyDataSetChanged();
    }
}


// todo ***
// todo: create an onpause method that saves the edittext fields as a column (named with the checkinId,which is accessible from current.id, maybe getTag needed )
// todo  in their corresponding textfields row (either inserting a new column if there isn't one, or updating otherwise.
// todo ***