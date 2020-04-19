package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        log.debug("In the get recipes...");
        return "recipeList";
    }

    @RequestMapping({"/recipeList"})
    public String getRecipes(Model model){
        model.addAttribute("recipes", recipeService.getRecipes());
        log.debug("In the get recipes...");
        return "recipeList";
    }
}
