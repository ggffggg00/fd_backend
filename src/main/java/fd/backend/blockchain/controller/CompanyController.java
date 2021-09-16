package fd.backend.blockchain.controller;

import fd.backend.blockchain.model.company.Company;
import fd.backend.blockchain.model.company.CompanyDto;
import fd.backend.blockchain.service.AuthenticationService;
import fd.backend.blockchain.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Не требуется! Сохранение компании происходит через сохранение юзера
     * пусть пока будет
     * @param companyDto
     */
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public void saveCompany(@RequestBody CompanyDto companyDto) {
        companyService.saveCompany(companyDto);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getCompanyInfoByUser() {
        Company company;
        try {
            company = companyService.getCompanyInfoByUser(
                    authenticationService.getAuthenticatedUserEmail()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(company);
    }

    /**
     * Получить информацию о всех компаниях
     * @return
     */
    @GetMapping("/info/all")
    public ResponseEntity<?> getAllCompanyInfoCut() {
        return ResponseEntity.ok(companyService.getAllCompanyInfoCut());
    }

}
