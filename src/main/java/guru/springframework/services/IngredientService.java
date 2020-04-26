package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdByIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand);
}
