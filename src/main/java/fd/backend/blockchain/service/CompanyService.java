package fd.backend.blockchain.service;

import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.company.CompanyDto;
import fd.backend.blockchain.model.company.CompanyDtoCut;
import fd.backend.blockchain.model.user.User;
import fd.backend.blockchain.repo.CompanyRepository;
import fd.backend.blockchain.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Save company
     * @param companyDto
     */
    @Transactional
    public void saveCompany(CompanyDto companyDto) {
        companyRepository.save(
                convertCompanyDtoToCompany(companyDto)
        );
    }

    public Company getCompanyInfoByUser(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        Company company;
        if (user != null) {
            company = user.getCompany();
        } else {
            throw new Exception("Not found user in system");
        }
        return company;
    }

    public Collection<CompanyDtoCut> getAllCompanyInfoCut() {
        Collection<CompanyDtoCut> companyDtoCutCollections = companyRepository.findAll()
                .stream()
                .map(companyEntity -> convertCompanyToCompanyDtoCut(companyEntity))
                .collect(Collectors.toList());
        return companyDtoCutCollections;
    }

    private CompanyDtoCut convertCompanyToCompanyDtoCut(Company company) {
        return CompanyDtoCut.builder()
                .id(company.getId())
                .taxIdentifier(company.getTaxIdentifier())
                .title(company.getTitle())
                .build();
    }

    private Company convertCompanyDtoToCompany(CompanyDto companyDto) {
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
