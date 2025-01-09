package org.zerock.apiserver.service;

import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO get(Long pno);

    Long register(PostDTO postDTO);

    void modify(PostDTO postDTO);

    void remove(Long pno);

    List<PostDTO> getPostsByType(String postType);

    Post dtoToEntity(PostDTO postDTO);

    PostDTO entityToDTO(Post post);
}