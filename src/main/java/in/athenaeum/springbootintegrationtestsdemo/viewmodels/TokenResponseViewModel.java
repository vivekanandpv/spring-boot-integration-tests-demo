package in.athenaeum.springbootintegrationtestsdemo.viewmodels;

public class TokenResponseViewModel {
    private final String token;

    public TokenResponseViewModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
