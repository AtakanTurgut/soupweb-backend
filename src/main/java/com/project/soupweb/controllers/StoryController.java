package com.project.soupweb.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.project.soupweb.requests.StoryCreateRequest;
import com.project.soupweb.responses.StoryResponse;
import com.project.soupweb.services.StoryService;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public List<StoryResponse> getAllStories() {
        return storyService.getAllStories();
    }

    @PostMapping
    public StoryResponse createStory(@RequestBody StoryCreateRequest request) {
        return storyService.createStory(request);
    }

    @DeleteMapping("/{storyId}")
    public void deleteStory(@PathVariable Long storyId) {
        storyService.deleteStory(storyId);
    }
}
