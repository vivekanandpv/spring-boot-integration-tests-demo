package in.athenaeum.springbootintegrationtestsdemo.services;

import in.athenaeum.springbootintegrationtestsdemo.models.AppUser;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.UserRegisterViewModel;

public interface AppUserService {
    AppUser register(UserRegisterViewModel viewModel);
    AppUser findUserByUsername(String username);
}
