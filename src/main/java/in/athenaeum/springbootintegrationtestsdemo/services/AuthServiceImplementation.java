package in.athenaeum.springbootintegrationtestsdemo.services;

import in.athenaeum.springbootintegrationtestsdemo.exceptions.LoginFailedException;
import in.athenaeum.springbootintegrationtestsdemo.models.AppUser;
import in.athenaeum.springbootintegrationtestsdemo.repositories.AppUserRepository;
import in.athenaeum.springbootintegrationtestsdemo.security.AppUserDetails;
import in.athenaeum.springbootintegrationtestsdemo.util.JwtUtils;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.LoginViewModel;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.TokenResponseViewModel;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.UserRegisterViewModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AppUserService appUserService;

    public AuthServiceImplementation(
            AppUserRepository appUserRepository, 
            PasswordEncoder passwordEncoder, 
            JwtUtils jwtUtils, 
            AppUserService appUserService
    ) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.appUserService = appUserService;
    }

    @Override
    public TokenResponseViewModel getToken(LoginViewModel viewModel) {
        AppUser user = appUserRepository.findUserByUsername(viewModel.getUsername())
                .orElseThrow(LoginFailedException::new);

        if (!passwordEncoder.matches(viewModel.getPassword(), user.getPassword())) {
            throw new LoginFailedException();
        }

        String token = jwtUtils.generateToken(new AppUserDetails(user));

        return new TokenResponseViewModel(token);
    }

    @Override
    public void register(UserRegisterViewModel viewModel) {
        appUserService.register(viewModel);
    }
}
