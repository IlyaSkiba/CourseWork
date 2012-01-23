package org.jgeek.website.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * User: Dmitry Leontyev
 * Date: 26.12.10
 * Time: 22:49
 */
public interface IChangePassword extends UserDetailsService {

    void changePassword(String username, String password);

}
