package org.zerock.apiserver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long pno;
    private Long mno;
    private String title;
    private String content;
    private Long categoryId;
    private Long regionId;
    private String location;
    private String photoUrl;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String postType;  // "LOST", "FOUND", "FREE"
}