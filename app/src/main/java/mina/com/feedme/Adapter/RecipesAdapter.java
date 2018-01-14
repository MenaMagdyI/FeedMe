package mina.com.feedme.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mina.com.feedme.Data.Recipe;
import mina.com.feedme.R;

/**
 * Created by Mena on 1/14/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.MyRecipesViewHolder>{


    private RecipeOnClickListener mOnClickListener;
    private List<Recipe> RecipesList;


    public interface RecipeOnClickListener {
        void onClick(int position);
    }

    public RecipesAdapter(List<Recipe> recipes, RecipeOnClickListener clickListener) {
        this.RecipesList = recipes;
        this.mOnClickListener = clickListener;
    }

    @Override
    public MyRecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_main_item, parent, false);
        return new MyRecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecipesViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return RecipesList.size();
    }





    class MyRecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.R_title_id)
        TextView recipeNameTextView;
        @BindView(R.id.R_image_id)
        ImageView recipeImageView;





        public MyRecipesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            Recipe currentRecipe = RecipesList.get(position);
            recipeNameTextView.setText(currentRecipe.getmName());

            Picasso.with(recipeImageView.getContext())
                    .load(Uri.parse(currentRecipe.getmImageUrl()))
                    .placeholder(R.drawable.content)
                    .error(R.drawable.content)
                    .into(recipeImageView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onClick(position);
        }


    }
}
