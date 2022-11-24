package br.com.ChallengeBackEnd102022.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ChallengeBackEnd102022.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByLogin(String username);

}
