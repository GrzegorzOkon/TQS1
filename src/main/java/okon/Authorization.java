package okon;

public class Authorization {
    private String credentialsInterface;
    private String username;
    private String password;
    private String domain;

    public Authorization(String credentialsInterface, String username, String password, String domain) {
        this.credentialsInterface = credentialsInterface;
        this.username = username;
        this.password = password;
        this.domain = domain;
    }

    public String getCredentialsInterface() {
        return credentialsInterface;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDomain() {
        return domain;
    }
}