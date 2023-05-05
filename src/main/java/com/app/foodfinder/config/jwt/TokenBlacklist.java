package com.app.foodfinder.config.jwt;

import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;


/**
 * This class is used to store and check the blacklisted tokens.
 *
 * @author CSC8019_Team15
 * @since 01-05-2023
 */
@Component
public class TokenBlacklist {

    private final Set<String> blacklistedTokens = new HashSet<>();




    /**
     * This method add a token to the blacklistedTokens Set.
     *
     * @param token token to be blacklisted
     */
    public void addTokenToBlacklist(String token) {
        blacklistedTokens.add(token);
    }




    /**
     * This method validates if the token is blacklisted or not
     *
     * @param token token to be validated
     *
     * @return true if the token is blacklisted, else false
     */
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
