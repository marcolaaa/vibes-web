package ca.marcos.vibesweb.repository;

import ca.marcos.vibesweb.repository.model.RejectedQuote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RejectedQuotesRepository extends JpaRepository<RejectedQuote, Long> {

    @Transactional
    void deleteAllByUserEmail(String userEmail);

    @Transactional(readOnly = true)
    List<RejectedQuote> findAllByUserEmail(String userEmail);
}