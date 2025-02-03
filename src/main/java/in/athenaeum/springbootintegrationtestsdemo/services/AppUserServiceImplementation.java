package in.athenaeum.springbootintegrationtestsdemo.services;

import in.athenaeum.springbootintegrationtestsdemo.exceptions.RecordNotFoundException;
import in.athenaeum.springbootintegrationtestsdemo.models.AppUser;
import in.athenaeum.springbootintegrationtestsdemo.repositories.AppRoleRepository;
import in.athenaeum.springbootintegrationtestsdemo.repositories.AppUserRepository;
import in.athenaeum.springbootintegrationtestsdemo.viewmodels.UserRegisterViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AppUserServiceImplementation implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImplementation(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser register(UserRegisterViewModel viewModel) {
        AppUser entity = new AppUser();
        BeanUtils.copyProperties(viewModel, entity, "password");

        entity.setAppRoles(
                viewModel
                        .getRoles()
                        .stream()
                        .map(r -> this.appRoleRepository.findByRole(r).orElseThrow(RecordNotFoundException::new))
                        .collect(Collectors.toSet())
        );

        entity.setPassword(this.passwordEncoder.encode(viewModel.getPassword()));

        return appUserRepository.saveAndFlush(entity);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findUserByUsername(username)
                .orElseThrow(RecordNotFoundException::new);
    }
}
