package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}