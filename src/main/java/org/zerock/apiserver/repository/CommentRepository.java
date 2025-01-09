package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}