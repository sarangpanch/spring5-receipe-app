package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(Model model, @PathVariable("recipeId") String recipeId) {
        log.info("Getting the ingredient list for recipe:" + recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";

    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(Model model, @PathVariable("recipeId") String recipeId, @PathVariable("id") String ingredientId) {

        log.info("From controller: recipe:" + recipeId.toString() + " and ingredient id:" + ingredientId.toString());
        IngredientCommand ingredientCommand =
                ingredientService.findByRecipeIdByIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(Model model,
                                   @PathVariable("recipeId") String recipeId,
                                   @PathVariable("id") String ingredientId) {
        log.info("Get ingredient: " + ingredientId + " of recipe: " + recipeId);

        IngredientCommand ingredient = ingredientService.findByRecipeIdByIngredientId(Long.valueOf(recipeId),
                Long.valueOf(ingredientId));
        model.addAttribute("ingredient", ingredient);

        Set<UnitOfMeasureCommand> uoms = unitOfMeasureService.findAll();
        model.addAttribute("uomList", uoms);
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        log.info("Save or update ingredient in recipe with id:" + ingredientCommand.getRecipeId());

        IngredientCommand savedIngredientCommand = ingredientService.saveOrUpdateIngredient(ingredientCommand);
        log.info("saved Ing Command:" + savedIngredientCommand.getUom().getDescription());
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }
}
