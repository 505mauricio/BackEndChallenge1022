package br.com.ChallengeBackEnd102022.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ChallengeBackEnd102022.form.VideoForm;
import br.com.ChallengeBackEnd102022.model.Video;
import br.com.ChallengeBackEnd102022.repository.CategoryRepository;
import br.com.ChallengeBackEnd102022.repository.VideoRepository;

@RestController
@RequestMapping("/videos")
public class VideoController {

	public record videoDto(Long id, Long categoriaId, String titulo, String descricao, String url) {
		
		 videoDto(Video video) {
			this(video.getId(),video.getCategoria().getId(),video.getTitulo(),video.getDescricao(),video.getUrl());

		}
	}
	
	@Autowired
	VideoRepository videoRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping
	public Page<videoDto> get(@RequestParam(required = false) String search, @PageableDefault(sort = "titulo", direction = Direction.DESC, page = 0, size = 5) Pageable page){
		if (search==null){
			Page<Video> videos = videoRepository.findAll(page);
			 Page<videoDto> videosDto = videos.map(videoDto::new);
			 return videosDto;
			 

		}
		else {
			Page<Video> videos = videoRepository.findByTituloContaining(search,page);
			Page<videoDto> videosDto = videos.map(videoDto::new);
			return videosDto;
		}      
	}
	
	@GetMapping("/free")
	public List<videoDto> getFree(){
		Page<Video> videos = videoRepository.findAll(PageRequest.of(0, 3, Sort.by(Sort.Order.asc("id"))));
		return videos.stream()
                .map(v -> new videoDto(v.getId(),v.getCategoria().getId(),v.getTitulo(),v.getDescricao(),v.getUrl()))
                .collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<videoDto> getById(@PathVariable Long id) {
		Optional<Video> optvideo = videoRepository.findById(id);
		if (optvideo.isPresent()) {
			Video video = optvideo.get();
			return ResponseEntity.ok(new videoDto(video.getId(),video.getCategoria().getId(),video.getTitulo(),video.getDescricao(),video.getUrl()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<videoDto> save(@RequestBody @Validated VideoForm form){
		Video video = form.toVideo(categoryRepository);
		videoRepository.save(video);
		return ResponseEntity.ok(
				new videoDto(video.getId(),video.getCategoria().getId(),video.getTitulo(),video.getDescricao(),video.getUrl()));
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<videoDto> modify(@PathVariable Long id, @RequestBody @Validated VideoForm form){
		Optional<Video> optional = videoRepository.findById(id);
		
		if (optional.isPresent()) {
			Video video = optional.get();
			video.setTitulo(form.getTitulo());
			video.setDescricao(form.getDescricao());
			video.setUrl(form.getUrl());
			video.setCategoria(categoryRepository.findById(form.getCategoriaId()).get());
			return ResponseEntity.ok(new videoDto(video.getId(),video.getCategoria().getId(),video.getTitulo(),video.getDescricao(),video.getUrl()));
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
