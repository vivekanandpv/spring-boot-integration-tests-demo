package in.athenaeum.springbootintegrationtestsdemo.services;

import in.athenaeum.springbootintegrationtestsdemo.viewmodels.LoginViewModel;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.TokenResponseViewModel;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.UserRegisterViewModel;

public interface AuthService {
    TokenResponseViewModel getToken(LoginViewModel viewModel);
    void register(UserRegisterViewModel viewModel);
}
