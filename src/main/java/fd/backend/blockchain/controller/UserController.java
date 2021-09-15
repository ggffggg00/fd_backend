package fd.backend.blockchain.controller;

import fd.backend.blockchain.model.user.UserDto;
import fd.backend.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private static final String REGISTER_URL = "/register";

    @Autowired
    private UserService userService;

    /**
     * {
     *     "password":"500",
     *     "companyName":"ООО НСК-ДОМ",
     *     "role":"CARRIER",
     *     "company": {
     *         "title":"ООО тест",
     *         "tax_id":"TAKSA",
     *         "ogrn":"ogrin",
     *         "legal_address":"legalAddress",
     *         "phone":"8903"
     *     }
     * }
     */
    @PostMapping(REGISTER_URL)
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }


}
