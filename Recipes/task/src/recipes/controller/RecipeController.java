package recipes.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recipes.DTO.IdResponse;
import recipes.view.RecipeView;
import recipes.model.Recipe;
import recipes.service.RecipeService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @JsonView(RecipeView.WholeRecipe.class)
    @GetMapping(path= "/api/recipe/{id}")
    public @ResponseBody
    Recipe getRecipe(@PathVariable Long id){
        return recipeService.getRecipe(id);
    }

    @PostMapping(path = "/api/recipe/new")
    public @ResponseBody
    IdResponse saveRecipe(@Valid @RequestBody Recipe recipe, Authentication authentication){
        return recipeService.saveRecipe(recipe, authentication);
    }

    @DeleteMapping(path = "/api/recipe/{id}")
    public void deleteRecipe(@PathVariable Long id, Authentication authentication){
        recipeService.deleteRecipe(id, authentication);
    }

    @JsonView(RecipeView.NoDateTime.class)
    @PutMapping(path = "/api/recipe/{id}")
    public void updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe,
                             Authentication authentication){
        recipeService.updateRecipe(id, recipe, authentication);
    }

    @GetMapping(path = "/api/recipe/search")
    public @ResponseBody
    List<Recipe> findRecipes(@RequestParam(required = false) String category,
                             @RequestParam(required = false) String name){
        return recipeService.findRecipes(category, name);
    }
}
