package guru.springframework;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RecipeBootStrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private CategoryRepository categoryRepository;

    public RecipeBootStrap(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
        log.debug("loading bootstrap data...");
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(15);
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon\" +\n" +
                "                \"\\n\" +\n" +
                "                \"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\" +\n" +
                "                \"\\n\" +\n" +
                "                \"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\\n\" +\n" +
                "                \"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\\n\" +\n" +
                "                \"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\\n\" +\n" +
                "                \"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\\n\" +\n" +
                "                \"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamole.setServings(4);
        guacamole.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Optional<UnitOfMeasure> each = unitOfMeasureRepository.findByUom("Each");
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByUom("Teaspoon");
        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByUom("Tablespoon");
        Optional<UnitOfMeasure> cup = unitOfMeasureRepository.findByUom("Cup");
        Optional<UnitOfMeasure> dash = unitOfMeasureRepository.findByUom("Dash");
        Optional<UnitOfMeasure> pint = unitOfMeasureRepository.findByUom("Pint");

        UnitOfMeasure eachUom = each.get();
        UnitOfMeasure teaspoonUom = teaspoon.get();
        UnitOfMeasure tablespoonUom = tablespoon.get();
        UnitOfMeasure cuoUom = cup.get();
        UnitOfMeasure dashUom = dash.get();
        UnitOfMeasure pintUom = pint.get();

        Ingredient ing1 = new Ingredient();
        ing1.setAmount(BigDecimal.valueOf(2.0));
        ing1.setDescription("Ripe Avocados");
        ing1.setUom(eachUom);
        ing1.setRecipe(guacamole);

        Ingredient tomato = new Ingredient();
        tomato.setAmount(BigDecimal.valueOf(0.5));
        tomato.setDescription("ripe tomato, seeds and pulp removed, chopped");
        tomato.setUom(eachUom);
        tomato.setRecipe(guacamole);

        Ingredient radish = new Ingredient();
        radish.setAmount(BigDecimal.valueOf(1.0));
        radish.setDescription("Red radishes or jicama");
        radish.setUom(eachUom);
        radish.setRecipe(guacamole);

        Ingredient tortilla = new Ingredient();
        tortilla.setAmount(BigDecimal.valueOf(10.0));
        tortilla.setDescription("Tortilla chips");
        tortilla.setUom(eachUom);
        tortilla.setRecipe(guacamole);

        Ingredient salt = new Ingredient();
        salt.setAmount(BigDecimal.valueOf(0.25));
        salt.setDescription("Salt");
        salt.setUom(teaspoonUom);
        salt.setRecipe(guacamole);

        Ingredient lemonjuice = new Ingredient();
        lemonjuice.setAmount(BigDecimal.valueOf(1.0));
        lemonjuice.setDescription("Fresh lime juice or lemon juice");
        lemonjuice.setUom(tablespoonUom);
        lemonjuice.setRecipe(guacamole);

        Ingredient onion = new Ingredient();
        onion.setAmount(BigDecimal.valueOf(0.25));
        onion.setDescription("minced red onion or thinly sliced green onion");
        onion.setUom(cuoUom);
        onion.setRecipe(guacamole);

        Ingredient chile = new Ingredient();
        chile.setAmount(BigDecimal.valueOf(2));
        chile.setDescription("serrano chiles, stems and seeds");
        chile.setUom(eachUom);
        chile.setRecipe(guacamole);

        Ingredient cilantro = new Ingredient();
        cilantro.setAmount(BigDecimal.valueOf(2.0));
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");
        cilantro.setUom(tablespoonUom);
        cilantro.setRecipe(guacamole);

        Ingredient pepper = new Ingredient();
        pepper.setAmount(BigDecimal.valueOf(1.0));
        pepper.setDescription("freshly grated black pepper");
        pepper.setUom(dashUom);
        pepper.setRecipe(guacamole);

        guacamole.addIngredient(ing1);
        guacamole.addIngredient(salt);
        guacamole.addIngredient(lemonjuice);
        guacamole.addIngredient(onion);
        guacamole.addIngredient(chile);
        guacamole.addIngredient(cilantro);
        guacamole.addIngredient(pepper);
        guacamole.addIngredient(tomato);
        guacamole.addIngredient(tortilla);
        guacamole.addIngredient(radish);

        Optional<Category> american = categoryRepository.findByDescription("American");
        Optional<Category> mexican = categoryRepository.findByDescription("Mexican");

        Category americanCategory = american.get();
        Category mexicanCategory = mexican.get();

        guacamole.getCategories().add(americanCategory);
        guacamole.getCategories().add(mexicanCategory);
        guacamole.setDifficulty(Difficulty.EASY);


        Notes guacNotes = new Notes();
        guacNotes.setDescription("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        //guacNotes.setRecipe(guacamole);
        guacamole.setNotes(guacNotes);

        recipes.add(guacamole);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setDescription("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n");
        tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacoNotes);
        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setServings(6);
        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUom));

        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUom));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoonUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cuoUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cuoUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(tacosRecipe);

        return recipes;
    }
}
