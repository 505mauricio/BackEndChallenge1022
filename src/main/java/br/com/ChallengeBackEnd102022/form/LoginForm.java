package br.com.ChallengeBackEnd102022.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.ChallengeBackEnd102022.model.User;

public class LoginForm {

	private String login;
	private String password;

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsernamePasswordAuthenticationToken convert() {
		return new UsernamePasswordAuthenticationToken(login, password);
	}

	public User toUser() {
		return new User(this.login, new BCryptPasswordEncoder().encode(this.password));
	}
	

}
