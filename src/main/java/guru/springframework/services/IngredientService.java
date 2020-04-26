package guru.springframework.services;

import guru.springframework.domain.Ingredient;

import java.util.Set;

public interface IngredientService {

    Set<Ingredient> findByRecipeId(Long recipeId);
}
