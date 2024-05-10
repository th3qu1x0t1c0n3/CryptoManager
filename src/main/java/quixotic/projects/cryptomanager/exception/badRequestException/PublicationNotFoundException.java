package quixotic.projects.cryptomanager.exception.badRequestException;

public class PublicationNotFoundException extends BadRequestException {
    public PublicationNotFoundException() {
        super("message.publicationNotFound");
    }
}
