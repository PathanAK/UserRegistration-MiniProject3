package com.asif.registrarionformminiproject03.Controller;

import com.asif.registrarionformminiproject03.Binding.LoginForm;
import com.asif.registrarionformminiproject03.Binding.UnlockAccountForm;
import com.asif.registrarionformminiproject03.Binding.UserForm;
import com.asif.registrarionformminiproject03.Service.UserMgmtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserRegisterController {

    private UserMgmtService userMgmtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        String login = userMgmtService.login(loginForm);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @GetMapping("/countries")
    public Map<Integer, String> getCountries() {
        return userMgmtService.getCountries();
    }

    @GetMapping("/states/{countryId}")
    public Map<Integer, String> getState(@PathVariable Integer countryId) {
        return userMgmtService.getState(countryId);
    }

    @GetMapping("/cities/{stateId}")
    public Map<Integer, String> getCities(@PathVariable Integer stateId) {
        return userMgmtService.getCity(stateId);
    }

    @GetMapping("/email/{email}")
    public String emailCheck(@PathVariable String email) {
        return userMgmtService.checkEmail(email);
    }

    @PostMapping("/user")
    public ResponseEntity<String> userRegistration(@RequestBody UserForm userForm) {
        String status = userMgmtService.registerUser(userForm);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccountForm unlockAccountForm) {
        String lockStatus = userMgmtService.unlockAccount(unlockAccountForm);
        return new ResponseEntity<>(lockStatus, HttpStatus.OK);
    }

    @GetMapping("/forgetpwd/{email}")
    public ResponseEntity<String> forgetPassword(@PathVariable String email) {
        String status = userMgmtService.forgetPassword(email);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
