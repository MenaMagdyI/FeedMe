package mina.com.feedme.DataBase;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Mena on 1/16/2018.
 */

public class RecipesContract {
    public interface IngredientEntry{
        @DataType(DataType.Type.INTEGER)
        String RECIPE_ID = "recipe_id";
        @DataType(DataType.Type.TEXT)
        String RECIPE_NAME = "recipe_name";
        @DataType(DataType.Type.TEXT)
        String INGREDIENT_NAME = "ingredient";
        @DataType(DataType.Type.TEXT)
        String MEASURE = "measure";
        @DataType(DataType.Type.REAL)
        String QUANTITY = "qantity";
    }

    @Database(version = RecipesDatabase.VERSION)
    public final class RecipesDatabase {
        public static final int VERSION = 1;
        @Table(IngredientEntry.class)
        public static final String INGREDIENTS = "ingredients";
    }
}
