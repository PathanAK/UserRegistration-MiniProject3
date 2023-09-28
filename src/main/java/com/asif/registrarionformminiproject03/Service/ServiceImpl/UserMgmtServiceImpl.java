package com.asif.registrarionformminiproject03.Service.ServiceImpl;

import com.asif.registrarionformminiproject03.Binding.LoginForm;
import com.asif.registrarionformminiproject03.Binding.UnlockAccountForm;
import com.asif.registrarionformminiproject03.Binding.UserForm;
import com.asif.registrarionformminiproject03.Entity.CityMaster;
import com.asif.registrarionformminiproject03.Entity.CountryMaster;
import com.asif.registrarionformminiproject03.Entity.StateMaster;
import com.asif.registrarionformminiproject03.Entity.UserDetails;
import com.asif.registrarionformminiproject03.Repository.CityRepository;
import com.asif.registrarionformminiproject03.Repository.CountryRepository;
import com.asif.registrarionformminiproject03.Repository.StateRepository;
import com.asif.registrarionformminiproject03.Repository.UserRepository;
import com.asif.registrarionformminiproject03.Service.UserMgmtService;
import com.asif.registrarionformminiproject03.Utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private StateRepository stateRepo;
    @Autowired
    private CityRepository cityRepo;
    @Autowired
    private EmailUtils emailUtils;

    @Override
    public String checkEmail(String email) {
        UserDetails byEmail = userRepo.findByEmail(email);
        if (byEmail == null) {
            return "Unique";
        }
        return "AlreadyExit";
    }

    @Override
    public Map<Integer, String> getCountries() {

        List<CountryMaster> countries = countryRepo.findAll();
        Map<Integer, String> countryMap = new HashMap<>();
        countries.stream().forEach(country -> {
            countryMap.put(country.getId(), country.getCName());
        });
        return countryMap;
    }

    @Override
    public Map<Integer, String> getState(Integer countryId) {

        List<StateMaster> states = stateRepo.findByCountryId(countryId);
        Map<Integer, String> stateMap = new HashMap<>();
        states.stream().forEach(state -> {
            stateMap.put(state.getSId(), state.getSName());
        });
        return stateMap;
    }

    @Override
    public Map<Integer, String> getCity(Integer stateId) {

        List<CityMaster> cities = cityRepo.findByStateId(stateId);
        Map<Integer, String> cityMap = new HashMap<>();
        cities.stream().forEach(city -> {
            cityMap.put(city.getCityId(), city.getCityName());
        });
        return cityMap;
    }

    @Override
    public String registerUser(UserForm user) {
        UserDetails userEntity = new UserDetails();
        BeanUtils.copyProperties(user, userEntity);
        //Generate & Set Random pwd
        String randomPassword = generatePassword();
        userEntity.setPassword(randomPassword);
        //Set account status as locked
        userEntity.setAccountStatus("Locked");
        userRepo.save(userEntity);
        //Send Email to unlock the account
        String to = user.getEmail();
        String subject = "Registration Email";
        String body = readEmailBody("RES_EMAIL_BODY.txt", userEntity);
        emailUtils.sendEmail(to, subject, body);
        return "User create Successfully";
    }

    @Override
    public String unlockAccount(UnlockAccountForm unlockAccountForm) {
        String email = unlockAccountForm.getEmail();
        UserDetails user = userRepo.findByEmail(email);
        if (user.getPassword().equals(unlockAccountForm.getTempPassword())) {
            user.setPassword(unlockAccountForm.getNewPassword());
            user.setAccountStatus("Unlocked");
            userRepo.save(user);
            return "Account Unlocked.!";
        }
        return "Invalid Temporary Password.!";
    }

    @Override
    public String login(LoginForm login) {

        UserDetails user = userRepo.findByEmailAndPassword(login.getEmail(), login.getPassword());
        if (user == null) {
            return "Invalid Credentials";
        }
        if (user.getAccountStatus().equals("Locked")) {
            return "Account is locked";
        }
        return "Login Successfully";
    }

    @Override
    public String forgetPassword(String email) {
        UserDetails user = userRepo.findByEmail(email);
        if (user == null) {
            return "User Not Found";
        }
        //Send the email with password
        String subject = "Recover Password";
        String body = readEmailBody("FORGET_PWD_BODY.txt", user);
        emailUtils.sendEmail(email, subject, body);
        return "Email was sent";
    }

    public String generatePassword() {
        String text = "ABCDEFGHIJLLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int pwdLength = 6;
        for (int i = 1; i <= pwdLength; i++) {
            int index = random.nextInt(text.length());
            sb.append(text.charAt(index));
        }
        return sb.toString();
    }

    private String readEmailBody(String fileName, UserDetails user) {

        StringBuffer sb = new StringBuffer();

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.forEach(line -> {
                line = line.replace("$(FNAME}", user.getUserName());
                line = line.replace("${LNAME}", user.getAccountStatus());
                line = line.replace("{TEMP_PWD}", user.getPassword());
                line = line.replace("${EMAIL}", user.getEmail());
                line = line.replace("${PWD}", user.getPassword());
                sb.append(line);
            });
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
