package com.project.soupweb.responses;

import com.project.soupweb.entities.Story;

import lombok.Data;

@Data
public class StoryResponse {
    Long id;
    Long userId;
    String photoUrl;

    public StoryResponse(Story entity) {
        this.id = entity.getId();
        this.userId = entity.getUser().getId();
        this.photoUrl = entity.getPhotoUrl();
    }
}