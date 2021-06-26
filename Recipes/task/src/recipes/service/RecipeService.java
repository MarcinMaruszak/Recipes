package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.DTO.IdResponse;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public Recipe getRecipe(Long id) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isPresent()) {
            return recipeOpt.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    public IdResponse saveRecipe(Recipe recipe, Authentication auth) {
        User authUser = (User) auth.getPrincipal();
        if (authUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        recipe.setUserID(authUser.getId());
        recipe.setDate(LocalDateTime.now());
        recipe = recipeRepository.save(recipe);
        return new IdResponse(recipe.getId());
    }

    public void deleteRecipe(Long id, Authentication auth) {
        User authUser = (User) auth.getPrincipal();
        if (authUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            if (!authUser.getId().equals(recipeOptional.get().getUserID())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipeRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Deleted");
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
    }

    public void updateRecipe(Long id, Recipe recipeNew, Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();
        if (authUser == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            Recipe oldRecipe = recipeOptional.get();
            if (!authUser.getId().equals(oldRecipe.getUserID())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            recipeNew.setId(oldRecipe.getId());
            recipeNew.setUserID(oldRecipe.getUserID());
            recipeNew.setDate(LocalDateTime.now());
            recipeRepository.save(recipeNew);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }

    public List<Recipe> findRecipes(String category, String name) {
        if ((category == null && name == null) || (category != null && name != null)) {     // wrong parameters
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (category != null) {
            return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        } else {
            return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
        }
    }
}
