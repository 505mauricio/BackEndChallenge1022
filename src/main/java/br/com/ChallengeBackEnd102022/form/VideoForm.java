package br.com.ChallengeBackEnd102022.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.ChallengeBackEnd102022.model.Category;
import br.com.ChallengeBackEnd102022.model.Video;
import br.com.ChallengeBackEnd102022.repository.CategoryRepository;

public class VideoForm {
	
	@NotBlank @Length(min = 5)
	private String titulo;
	
	@NotBlank @Length(min = 10)
	private String descricao;
	
	@NotBlank @Length(min = 5)
	private String url;
	
	@NotNull
	private Long categoriaId;

	

	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public Long getCategoriaId() {
		return categoriaId;
	}



	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}



	public Video toVideo(CategoryRepository categoryRepository) {
		Optional<Category> category = categoryRepository.findById(this.categoriaId);
		return new Video(this.titulo,this.descricao,this.url,category.get());
	}
	

}
