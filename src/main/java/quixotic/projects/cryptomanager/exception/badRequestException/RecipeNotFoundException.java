package quixotic.projects.cryptomanager.exception.badRequestException;

public class RecipeNotFoundException extends BadRequestException {
    public RecipeNotFoundException() {
        super("RecipeNotFound");
    }
}
