package website.service;

import com.bsu.server.dto.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * User: Dmitry Leontyev
 * Date: 10.01.11
 * Time: 17:26
 */
@Transactional
public class JpaUserDetailsService implements UserDetailsService, IChangePassword {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;

        try {
            userDetails = em.createQuery("from UserAccount where username = :username", UserAccount.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            throw new UsernameNotFoundException(null);
        }

        return userDetails;
    }

    @Override
    public void changePassword(String username, String newPassword) {
        UserAccount userDetails = em.createQuery("from UserAccount where username = :username", UserAccount.class)
                .setParameter("username", username)
                .getSingleResult();

        userDetails.setPassword(passwordEncoder.encodePassword(newPassword, null));

        em.merge(userDetails);

    }


}