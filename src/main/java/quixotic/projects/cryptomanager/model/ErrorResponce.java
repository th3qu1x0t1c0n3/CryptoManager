package quixotic.projects.cryptomanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponce {
    private String timestamp;
    private int status;
    private String message;
    private String path;
}
