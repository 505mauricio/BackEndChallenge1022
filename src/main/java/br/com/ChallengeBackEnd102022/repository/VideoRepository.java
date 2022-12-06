package br.com.ChallengeBackEnd102022.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ChallengeBackEnd102022.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	List<Video> findByCategoriaId(Long id);

	Page<Video> findByTituloContaining(String title, Pageable page);

}
