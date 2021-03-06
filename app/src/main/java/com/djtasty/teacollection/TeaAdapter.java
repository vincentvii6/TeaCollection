package com.djtasty.teacollection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeaAdapter extends RecyclerView.Adapter<TeaAdapter.TeaHolder> {
    private List<Tea> teas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TeaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tea_item, parent, false);
        return new TeaHolder(itemView);
    }

    // Set information to fields before sending to ViewHolder

    @Override
    public void onBindViewHolder(@NonNull TeaHolder holder, int position) {
        Tea currentTea = teas.get(position);
        holder.textViewName.setText(currentTea.getName());
        holder.textViewType.setText(currentTea.getType());
        holder.textViewQuantity.setText(String.valueOf(currentTea.getQuantity()) + "oz");

    }

    @Override
    public int getItemCount() {
        return teas.size();
    }

    public void setTeas(List<Tea> teas) {
        this.teas = teas;
        notifyDataSetChanged();
    }

    public Tea getTeaAt(int position) {
        return teas.get(position);
    }

    class TeaHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewType;
        private TextView textViewQuantity;

        // Set information in ViewHolder

        public TeaHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewQuantity = itemView.findViewById(R.id.text_view_quantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(teas.get(position));
                    }
                }
            });
        }
    }

    // Edit Tea by clicking list item

    public interface OnItemClickListener {
        void onItemClick(Tea tea);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
