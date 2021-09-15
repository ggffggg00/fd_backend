package fd.backend.blockchain.service;

import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.company.CompanyDto;
import fd.backend.blockchain.repo.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Save company
     * @param companyDto
     */
    @Transactional
    public void saveCompany(CompanyDto companyDto) {
        companyRepository.save(
          convertCompanyDtotoCompany(companyDto)
        );
    }

    private Company convertCompanyDtotoCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setId(company.getId());
        company.setLegalAddress(companyDto.getLegalAddress());
        company.setOgrn(companyDto.getOgrn());
        company.setTaxIdentifier(companyDto.getTaxIdentifier());
        company.setTitle(companyDto.getTitle());
        company.setContactPhoneNumber(companyDto.getContactPhoneNumber());
        return company;
    }

}
