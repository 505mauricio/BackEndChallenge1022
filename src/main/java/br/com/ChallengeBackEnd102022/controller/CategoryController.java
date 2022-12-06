package br.com.ChallengeBackEnd102022.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import br.com.ChallengeBackEnd102022.form.CategoryForm;
import br.com.ChallengeBackEnd102022.model.Category;
import br.com.ChallengeBackEnd102022.model.Video;
import br.com.ChallengeBackEnd102022.repository.CategoryRepository;
import br.com.ChallengeBackEnd102022.repository.VideoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	VideoRepository videoRepository;
	
	@GetMapping
	public Page<Category> get(@PageableDefault(sort = "titulo", direction = Direction.DESC, page = 0, size = 5) Pageable page){
		
		return categoryRepository.findAll(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> getById(@PathVariable Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(category.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/{id}/videos")
	public ResponseEntity<List<Video>> getVideosById(@PathVariable Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			List<Video> videos = videoRepository.findByCategoriaId(id);
			return  ResponseEntity.ok(videos);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Category> save(@RequestBody @Validated CategoryForm form){
		Category category = form.toCategory();
		categoryRepository.save(category);
		return ResponseEntity.ok(category);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Category> modify(@PathVariable Long id, @RequestBody @Validated CategoryForm form){
		Optional<Category> optional = categoryRepository.findById(id);
		
		if (optional.isPresent()) {
			Category category = optional.get();
			category.setTitulo(form.getTitulo());
			category.setCor(form.getCor());
			return ResponseEntity.ok(category);
		}
		
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id){
		Optional<Category> optional = categoryRepository.findById(id);
		
		if (optional.isPresent()) {
			categoryRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		
		return ResponseEntity.notFound().build();
	}
	
}
