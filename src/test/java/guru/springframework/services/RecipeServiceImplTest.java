package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipes;

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipes1 = new HashSet<>();
        recipes1.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipes1);
        recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());

        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepository.findById(1L)).thenReturn(recipeOptional);

        Recipe recipe1 = recipeService.getRecipeById(1L);
        assertNotNull(recipe1);
        verify(recipeRepository, times(1)).findById(anyLong());

    }
}