package org.zerock.apiserver.service;

import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.dto.CommentDTO;

public interface CommentService {
    CommentDTO get(Long id);

    Long register(CommentDTO commentDTO);

    void modify(CommentDTO commentDTO);

    void remove(Long id);

    Comment dtoToEntity(CommentDTO commentDTO);

    CommentDTO entityToDTO(Comment comment);
}