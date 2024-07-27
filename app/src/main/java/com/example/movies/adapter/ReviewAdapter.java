package com.example.movies.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {


    private List<Review> reviews = new ArrayList<>();

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());
        holder.textViewTitle.setText(review.getTitle());
        holder.textViewType.setText(review.getType());
        switch (review.getType()) {
            case "Негативный":
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_light));
                break;
            case "Позитивный":
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_light));
                break;
            default:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_orange_light));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ReviewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textViewAuthor;
        private TextView textViewTitle;
        private TextView textViewReview;
        private TextView textViewType;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewReview = itemView.findViewById(R.id.textViewReview);
            textViewType=itemView.findViewById(R.id.textViewType);
        }
    }

}
