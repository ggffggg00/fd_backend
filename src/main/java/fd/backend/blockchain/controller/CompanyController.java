package fd.backend.blockchain.controller;

import fd.backend.blockchain.model.company.CompanyDto;
import fd.backend.blockchain.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

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

}
