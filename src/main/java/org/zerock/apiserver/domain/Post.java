package org.zerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "comments")
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mno", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category; // 물품의 특성

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = true)
    private Region region;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String photoUrl;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = true)
    private LocalDateTime updated;

    @Column(nullable = false)
    private String postType;  // "LOST", "FOUND", "FREE"

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true) // Post 삭제 시 관련 Comment 삭제
    private List<Comment> comments;

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    // 추가된 메서드
    public void changeTitle(String title) {
        this.title = title;
        this.updated = LocalDateTime.now(); // 수정 시각 업데이트
    }

    public void changeContent(String content) {
        this.content = content;
        this.updated = LocalDateTime.now(); // 수정 시각 업데이트
    }
}