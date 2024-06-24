package quixotic.projects.cryptomanager.model.old;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import quixotic.projects.cryptomanager.model.Wallet;
import quixotic.projects.cryptomanager.security.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Wallet> wallets = new ArrayList<>();
    private double portfolioSize;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Allocation> allocations = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private KellyCriterion kellyCriterion;

    public void addWallet(Wallet wallet) {
        wallet.setUser(this);
        wallets.add(wallet);
    }
    public void removeWallet(Wallet wallet) {
        wallet.setUser(null);
        wallets.remove(wallet);
    }

    public void addAllocation(Allocation allocation) {
        allocation.setUser(this);
        allocations.add(allocation);
    }
    public void removeAllocation(Allocation allocation) {
        allocation.setUser(null);
        allocations.remove(allocation);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //	Non implémenté car non utilisé (Pour le moment) 2024-05-10
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
