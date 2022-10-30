package br.com.ChallengeBackEnd102022.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.com.ChallengeBackEnd102022.model.Video;

public class VideoForm {
	
	@NotBlank @Length(min = 5)
	private String title;
	
	@NotBlank @Length(min = 10)
	private String description;
	
	@NotBlank @Length(min = 5)
	private String url;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public Video toVideo() {
		return new Video(this.title,this.description,this.url);
	}
	

}
