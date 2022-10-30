package br.com.ChallengeBackEnd102022.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ChallengeBackEnd102022.form.VideoForm;
import br.com.ChallengeBackEnd102022.model.Video;
import br.com.ChallengeBackEnd102022.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {

	@Autowired
	VideoRepository videoRepository;
	
	@GetMapping
	public List<Video> get(){
		
		return videoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Video> getById(@PathVariable Long id) {
		Optional<Video> video = videoRepository.findById(id);
		if (video.isPresent()) {
			return ResponseEntity.ok(video.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Video> save(@RequestBody @Validated VideoForm form){
		Video video = form.toVideo();
		videoRepository.save(video);
		return ResponseEntity.ok(video);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Video> modify(@PathVariable Long id, @RequestBody @Validated VideoForm form){
		Optional<Video> optional = videoRepository.findById(id);
		
		if (optional.isPresent()) {
			Video video = optional.get();
			video.setTitle(form.getTitle());
			video.setDescription(form.getDescription());
			video.setUrl(form.getUrl());
			return ResponseEntity.ok(video);
		}
		
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Video> optional = videoRepository.findById(id);
		
		if (optional.isPresent()) {
			videoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		
		return ResponseEntity.notFound().build();
	}
	
}
