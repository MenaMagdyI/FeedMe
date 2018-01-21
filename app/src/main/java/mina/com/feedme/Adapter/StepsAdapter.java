package mina.com.feedme.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import mina.com.feedme.Model.StepModel;
import mina.com.feedme.R;

/**
 * Created by Mena on 1/15/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {


    private List<StepModel> mSteps;
    private StepOnClickListener mOnClickListener;

    public interface StepOnClickListener {
        void onClick(int position);
    }

    public StepsAdapter(List<StepModel> mSteps, StepOnClickListener listener) {
        this.mSteps = mSteps;
        this.mOnClickListener = listener;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_step_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView stepShortDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);

            stepShortDescription = (TextView) itemView.findViewById(R.id.step_description);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){
            stepShortDescription.setText(String.valueOf(position+1) + ". " + mSteps.get(position).getmShortDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onClick(position);
        }
    }
}
