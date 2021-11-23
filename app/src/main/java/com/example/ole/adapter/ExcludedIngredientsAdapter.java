package com.example.ole.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ole.R;

import java.util.List;

public class ExcludedIngredientsAdapter extends RecyclerView.Adapter<ExcludedIngredientsAdapter.ViewHolder> {

    private List<String> excludedIngredients;
    private ItemClickListener clickListener;

    // Data is passed into the constructor
    public ExcludedIngredientsAdapter(List<String> data) {
        this.excludedIngredients = data;
    }

    // Attaches excluded_ingredient_card.xml layout to holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.excluded_ingredient_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String ingredient = excludedIngredients.get(position);
        holder.textView.setText(ingredient);
    }

    // Determines how many items to show on the item view
    @Override
    public int getItemCount() {
        return excludedIngredients.size();
    }


    // Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.excluded_ingredient_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // Convenience method for getting data at click position
    public String getItem(int position) {
        return excludedIngredients.get(position);
    }

    // Allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // Parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
