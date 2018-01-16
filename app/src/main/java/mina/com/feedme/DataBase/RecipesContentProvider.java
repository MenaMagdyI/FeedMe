package mina.com.feedme.DataBase;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Mena on 1/16/2018.
 */

@ContentProvider(authority = RecipesContentProvider.AUTHORITY, database = RecipesContract.RecipesDatabase.class)
public class RecipesContentProvider {
    public static final String AUTHORITY = "mina.com.feedme";

    @TableEndpoint(table = RecipesContract.RecipesDatabase.INGREDIENTS)
    public static class Ingredients {
        @ContentUri(path = RecipesContract.RecipesDatabase.INGREDIENTS,
                type = "vnd.android.cursor.dir/list")
        public static final Uri INGREDIENTS = Uri.parse("content://" + AUTHORITY)
                .buildUpon()
                .appendPath(RecipesContract.RecipesDatabase.INGREDIENTS)
                .build();
    }

}
