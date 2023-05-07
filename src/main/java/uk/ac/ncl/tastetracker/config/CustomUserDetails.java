package uk.ac.ncl.tastetracker.config;

import uk.ac.ncl.tastetracker.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;



/**
 * This class implements Spring Security's UserDetails interface that represents a user's details
 * and provides methods for account validity. It also contains a Collection of GrantedAuthorities which is not
 * implemented and used.
 *
 * @author Surendhar Chandran Jayapal
 * @version 1.5 (Date - 06-05-2023)
 * @since 1.0 (Date - 17-04-2023)
 */
public final class CustomUserDetails implements UserDetails {


    /**
     * Username of the user
     */
    private final String username;

    /**
     * Password of the user
     */
    private final String password;



    /**
     * Constructs a new CustomUserDetails with the given User object's username and password.
     *
     * @param user User object
     */
    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }



    /**
     * Returns the authorities granted to the user. Not currently implemented.
     *
     * @return always null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    /**
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }



    /**
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }



    /**
     * @return true if the account is not expired else false
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    /**
     * @return true if the user account is not locked else false
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



    /**
     * @return true if the user credentials(password) is not expired else false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    /**
     * @return true if the user is enabled else returns false
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


}
