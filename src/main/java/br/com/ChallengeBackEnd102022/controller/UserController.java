package br.com.ChallengeBackEnd102022.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ChallengeBackEnd102022.form.LoginForm;
import br.com.ChallengeBackEnd102022.model.User;
import br.com.ChallengeBackEnd102022.repository.UserRepository;

@RestController
@RequestMapping("/signup")
@Profile(value = {"prod", "test"})
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<UserDto> save(@RequestBody @Validated LoginForm form){
		User user = form.toUser();
		userRepository.save(user);
		return ResponseEntity.ok(new UserDto(user.getLogin()));
	}
	
	public record UserDto(String login){}
	
}
