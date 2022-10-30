package br.com.ChallengeBackEnd102022.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ChallengeBackEnd102022.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
