package com.cipher.sharesmilesandroid.cardslider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderCard> {

    private final int count;
    private final int[] content;

    public SliderAdapter(int[] content, int count) {
        this.content = content;
        this.count = count;

    }

    @Override
    public SliderCard onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.slider_card, parent, false);



        return new SliderCard(view);
    }

    @Override
    public void onBindViewHolder(SliderCard holder, int position) {
        holder.setContent(content[position % content.length]);
    }

    @Override
    public void onViewRecycled(SliderCard holder) {
        holder.clearContent();
    }

    @Override
    public int getItemCount() {
        return count;
    }

}
