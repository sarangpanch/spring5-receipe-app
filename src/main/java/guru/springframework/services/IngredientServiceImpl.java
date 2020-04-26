package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }


    @Override
    public IngredientCommand findByRecipeIdByIngredientId(Long recipeId, Long ingredientId) {
        log.info("getting ingredient for recipe:" + recipeId.toString() + " and ingredient id:" + ingredientId.toString());
        //Ingredient ingredient = ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId);

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        Set<Ingredient> ingredients = new HashSet<>();
        if (recipe.isPresent()) {
            ingredients = recipe.get().getIngredients();
        } else {
            throw new RuntimeException("Recipe not found...");
        }

        if (ingredients.isEmpty() || ingredients.size() == 0) {
            log.error("No ingredients...");
            throw new RuntimeException("No ingredients");
        }
        Optional<Ingredient> ingredientOptional = ingredients.stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

        return ingredientToIngredientCommand.convert(ingredientOptional.get());
    }

    @Override
    @Transactional
    public IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        Optional<Ingredient> recipeIngredient = recipe.get().getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();
        if (recipe.isPresent()) {
            ingredientCommand.setAmount(recipeIngredient.get().getAmount());
            ingredientCommand.setDescription(recipeIngredient.get().getDescription());
            ingredientCommand.setUom(unitOfMeasureToUnitOfMeasureCommand.convert(recipeIngredient.get().getUom()));
        } else {
            recipe.get().addIngredient(recipeIngredient.get());
        }
        Recipe savedRecipe = recipeRepository.save(recipe.get());
        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst().get());
    }
}
