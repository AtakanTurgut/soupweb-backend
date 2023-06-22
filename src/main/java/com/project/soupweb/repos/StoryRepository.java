package com.project.soupweb.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.soupweb.entities.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

}
