package quixotic.projects.cryptomanager.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static quixotic.projects.cryptomanager.security.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
    ),
    COOK(
            Set.of(
                    COOK_READ,
                    COOK_UPDATE,
                    COOK_DELETE,
                    COOK_CREATE
            )
    ),
    VISITOR(
            Set.of(
                    VISITOR_READ,
                    VISITOR_UPDATE,
                    VISITOR_DELETE,
                    VISITOR_CREATE
            )
    );
    private final Set<Permission> permissions;
}
