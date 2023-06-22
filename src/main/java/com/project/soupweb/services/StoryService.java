package com.project.soupweb.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.soupweb.entities.Story;
import com.project.soupweb.entities.User;
import com.project.soupweb.repos.StoryRepository;
import com.project.soupweb.requests.StoryCreateRequest;
import com.project.soupweb.responses.StoryResponse;

@Service
public class StoryService {

    private StoryRepository storyRepository;
    private UserService userService;

    public StoryService(StoryRepository storyRepository, UserService userService) {
        this.storyRepository = storyRepository;
        this.userService = userService;
    }

    public List<StoryResponse> getAllStories() {
        List<Story> stories = storyRepository.findAll();
        return stories.stream().map(story -> new StoryResponse(story)).collect(Collectors.toList());
    }

    public StoryResponse createStory(StoryCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        if(user != null) {
            Story storyToSave = new Story();
            storyToSave.setUser(user);
            storyToSave.setPhotoUrl(request.getPhotoUrl());
            Story savedStory = storyRepository.save(storyToSave);
            return new StoryResponse(savedStory);
        } else
            return null;
    }

    public void deleteStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }
}
