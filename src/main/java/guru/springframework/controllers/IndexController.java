package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import guru.springframework.services.RecipeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        return "index";
    }

    @RequestMapping({"/recipeList"})
    public String getRecipes(Model model){
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipeList";
    }
}
