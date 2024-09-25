package com.urgenciasYa.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private UserEntity user; // Instance of UserEntity that this class wraps

    public UserPrincipal(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This method returns the authorities granted to the user.
        // Assuming 'role' is an object of RoleEntity and 'code' is the name of the role.
        return List.of(new SimpleGrantedAuthority(user.getRole().getCode())); // Returns a list with the user's role as an authority
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    } // Returns the password of the user

    @Override
    public String getUsername() {
        return user.getName();
    } // Returns the username of the user

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // Indicates whether the user's account is expired; true means it is not expired

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // Indicates whether the user's account is locked; true means it is not locked

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // Indicates whether the user's credentials (password) are expired; true means they are not expired

    @Override
    public boolean isEnabled() {
        return true;
    } // Indicates whether the user is enabled; true means the user is enabled

}
