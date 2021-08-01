package com.svarks.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svarks.myapplication.data.model.Review
import com.svarks.myapplication.databinding.ItemReviewBinding

/**
 * @param reviews
 * To display the review given by customers
 */
class ReviewAdapter(
    private val reviews: List<Review>
) : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    class ReviewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                tvComments.text = review.comments
                tvName.text = review.name
                tvDate.text = review.date
                rating.apply {
                    numStars = 5
                    stepSize = 0.5.toFloat()
                    rating = review.rating.toFloat()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return if (reviews.isNotEmpty()) reviews.size else 0
    }
}
