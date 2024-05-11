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
    USER(
            Set.of(
                    USER_READ,
                    USER_UPDATE,
                    USER_DELETE,
                    USER_CREATE
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
