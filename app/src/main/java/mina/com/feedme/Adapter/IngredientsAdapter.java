package mina.com.feedme.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import mina.com.feedme.Model.Ingredient;
import mina.com.feedme.R;

/**
 * Created by Mena on 1/15/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private List<Ingredient> mIngredients;

    public IngredientsAdapter(List<Ingredient> mIngredients) {

        this.mIngredients = mIngredients;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_ingredient_item, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        TextView quantityTextView;
        TextView measureTextView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientTextView = (TextView) itemView.findViewById(R.id.ingr_title);
            quantityTextView = (TextView) itemView.findViewById(R.id.ing_amount);
            measureTextView = (TextView) itemView.findViewById(R.id.ON_ing);
        }

        public void bind(int position) {
            Ingredient currentIngredient = mIngredients.get(position);
            String ingredient = currentIngredient.getmIngredient();
            double quantity = currentIngredient.getmQuantity();
            String measure = currentIngredient.getmMeasure();

            ingredientTextView.setText(ingredient);
            quantityTextView.setText(String.valueOf(quantity));
            measureTextView.setText(measure);
        }
    }
}
