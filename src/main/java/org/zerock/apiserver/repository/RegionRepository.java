package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}