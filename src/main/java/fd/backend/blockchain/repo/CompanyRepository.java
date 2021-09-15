package fd.backend.blockchain.repo;

import fd.backend.blockchain.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByTitle(String title);

}
