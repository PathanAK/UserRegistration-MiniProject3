package com.asif.registrarionformminiproject03.Service;

import com.asif.registrarionformminiproject03.Binding.LoginForm;
import com.asif.registrarionformminiproject03.Binding.UnlockAccountForm;
import com.asif.registrarionformminiproject03.Binding.UserForm;

import java.util.Map;

public interface UserMgmtService {

    public String checkEmail(String email);

    public Map<Integer, String> getCountries();

    public Map<Integer, String> getState(Integer countryId);

    public Map<Integer, String> getCity(Integer stateId);

    public String registerUser(UserForm user);

    public String unlockAccount(UnlockAccountForm unlockAccountForm);

    public String login(LoginForm login);

    public String forgetPassword(String email);
}
