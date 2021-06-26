package recipes.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.view.RecipeView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@Entity
public class Recipe {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @JsonView(RecipeView.NoDateTime.class)
    private String name;

    @NotBlank
    @JsonView(RecipeView.NoDateTime.class)
    private String description;

    @NotBlank
    @JsonView(RecipeView.NoDateTime.class)
    private String category;

    @JsonView(RecipeView.WholeRecipe.class)
    private LocalDateTime date;

    @NotEmpty
    @JsonView(RecipeView.NoDateTime.class)
    private ArrayList<String> ingredients;

    @NotEmpty
    @JsonView(RecipeView.NoDateTime.class)
    private ArrayList<String> directions;

    @JsonIgnore
    private Long UserID;
}
