package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeService recipeService;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeService recipeService, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeService = recipeService;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdByIngredientId(Long recipeId, Long ingredientId) {
        log.info("getting ingredient for recipe:" + recipeId.toString() + " and ingredient id:" + ingredientId.toString());
        //Ingredient ingredient = ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId);

        Recipe recipe = recipeService.findById(recipeId);
        Set<Ingredient> ingredients = recipe.getIngredients();

        if (ingredients.isEmpty() || ingredients.size() == 0) {
            log.error("No ingredients...");
            throw new RuntimeException("No ingredients");
        }
        Optional<Ingredient> ingredientOptional = ingredients.stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

        return ingredientToIngredientCommand.convert(ingredientOptional.get());
    }
}
