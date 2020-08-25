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
    private int rootCheckinId;

    public static class CheckinFilloutViewholder extends RecyclerView.ViewHolder {

        public LinearLayout containerView;
        public TextView fieldTitle;
        public EditText fieldEditText;

        public CheckinFilloutViewholder(@NonNull View itemView, final int checkinId) {
            super(itemView);
            containerView = itemView.findViewById(R.id.fillout_row);
            fieldTitle = itemView.findViewById(R.id.fillout_field_title);
            fieldEditText = itemView.findViewById(R.id.fillout_field_input);

            fieldEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String fieldContents = String.valueOf(fieldEditText.getText());
                        String title = String.valueOf(fieldTitle.getText());
                        MainActivity.database.threadDao().saveFieldData(fieldContents, checkinId, title);
                    }
                }
            });

        }
    }

    public CheckinFilloutAdapter(List<CheckinFields> currentDataset, int checkinId){
        fields = currentDataset;
        rootCheckinId = checkinId;
    }

    @NonNull
    @Override
    public CheckinFilloutViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fillout_row, parent, false);
        return new CheckinFilloutViewholder(view, rootCheckinId);
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
        fields = MainActivity.database.threadDao().getFields(rootCheckinId); //todo change to gettFields with rootThreadId
        notifyDataSetChanged();
    }
}


// todo ***
// todo: create an onpause method that saves the edittext fields as a column (named with the checkinId,which is accessible from current.id, maybe getTag needed )
// todo  in their corresponding textfields row (either inserting a new column if there isn't one, or updating otherwise.
// todo ***