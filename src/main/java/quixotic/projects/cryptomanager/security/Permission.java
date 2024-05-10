package quixotic.projects.cryptomanager.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    COOK_READ("cook:read"),
    COOK_UPDATE("cook:update"),
    COOK_CREATE("cook:create"),
    COOK_DELETE("cook:delete"),
    VISITOR_READ("visitor:read"),
    VISITOR_UPDATE("visitor:update"),
    VISITOR_CREATE("visitor:create"),
    VISITOR_DELETE("visitor:delete")

    ;

    private final String permission;
}
