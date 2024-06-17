package quixotic.projects.cryptomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.Allocation;

import java.util.List;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    List<Allocation> findAllByUserId(Long userId);
    List<Allocation> findAllocationsByUser_Email(String email);


}
