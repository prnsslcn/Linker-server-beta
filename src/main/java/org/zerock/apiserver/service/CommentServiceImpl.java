package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.repository.CommentRepository;
import org.zerock.apiserver.util.CustomServiceException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public CommentDTO get(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CustomServiceException("NOT_EXIST_COMMENT"));
        return entityToDTO(comment);
    }

    @Override
    public Long register(CommentDTO commentDTO) {
        Comment comment = commentRepository.save(dtoToEntity(commentDTO));
        return comment.getId();
    }

    @Override
    public void modify(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> new CustomServiceException("NOT_EXIST_COMMENT"));
        comment.changeContent(commentDTO.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void remove(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CustomServiceException("NOT_EXIST_COMMENT");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public Comment dtoToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .id(commentDTO.getId())
                .content(commentDTO.getContent())
                .build();
    }

    @Override
    public CommentDTO entityToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }
}