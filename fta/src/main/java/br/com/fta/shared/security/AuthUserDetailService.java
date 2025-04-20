package br.com.fta.shared.security;

import br.com.fta.user.domain.User;
import br.com.fta.user.infra.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AuthUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

	public AuthUserDetailService(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
		this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(username);
		if (userOptional.isEmpty()) {
			// If not in memory, throws UsernameNotFoundException
			return inMemoryUserDetailsManager.loadUserByUsername(username);
		}
		User user = userOptional.get();
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        user.getAuthorities()
          .forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority())));
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
