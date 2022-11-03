package br.com.ChallengeBackEnd102022.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.ChallengeBackEnd102022.model.Category;

public class CategoryForm {
	
	@NotBlank 
	private String titulo;
	
	@NotBlank @Pattern(regexp="^[(a-f|0-9)]{6}$")
	private String cor;
	
	public Category toCategory() {
		
		return new Category(this.titulo,this.cor);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}



	
	
}
