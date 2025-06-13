package com.example.updatedsmartlearnapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.updatedsmartlearnapp.R;
import com.example.updatedsmartlearnapp.models.Topic;
import java.util.ArrayList;
import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.TopicViewHolder> {

    private final Context context;
    private final List<Topic> topicList;
    private final List<Topic> selectedTopics = new ArrayList<>();
    private final int MAX_SELECTION = 10;

    public InterestAdapter(Context context, List<Topic> topics) {
        this.context = context;
        this.topicList = topics;
    }

    public List<String> getSelectedTopics() {
        List<String> selectedNames = new ArrayList<>();
        for (Topic t : selectedTopics) {
            selectedNames.add(t.getName());
        }
        return selectedNames;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        holder.name.setText(topic.getName());

        boolean isSelected = selectedTopics.contains(topic);
        holder.card.setCardBackgroundColor(isSelected ?
                context.getResources().getColor(android.R.color.holo_green_light)
                : context.getResources().getColor(android.R.color.white));

        holder.card.setOnClickListener(v -> {
            if (selectedTopics.contains(topic)) {
                selectedTopics.remove(topic);
            } else {
                if (selectedTopics.size() < MAX_SELECTION) {
                    selectedTopics.add(topic);
                }
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CardView card;

        public TopicViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_topic_name);
            card = (CardView) itemView;
        }
    }
}
