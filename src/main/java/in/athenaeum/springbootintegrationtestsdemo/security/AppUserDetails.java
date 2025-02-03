package in.athenaeum.springbootintegrationtestsdemo.security;

import in.athenaeum.springbootintegrationtestsdemo.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {
    private final AppUser user;

    public AppUserDetails(AppUser user) {
        this.user = user;
    }

    private boolean isUserAllowed() {
        //  custom logic
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAppRoles().stream()
                .map(c -> (GrantedAuthority) () -> c.getRole())
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isUserAllowed();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isUserAllowed();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isUserAllowed();
    }

    @Override
    public boolean isEnabled() {
        return this.isUserAllowed();
    }
}
