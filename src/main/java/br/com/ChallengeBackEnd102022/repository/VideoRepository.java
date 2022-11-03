package br.com.ChallengeBackEnd102022.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ChallengeBackEnd102022.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

	List<Video> findByCategoriaId(Long id);

	List<Video> findByTituloContaining(String title);
}
