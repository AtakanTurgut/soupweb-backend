package com.project.soupweb.requests;

import lombok.Data;

@Data
public class StoryCreateRequest {
    Long userId;
    String photoUrl;
}