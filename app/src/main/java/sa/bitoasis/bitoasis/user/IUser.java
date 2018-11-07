package sa.bitoasis.bitoasis.user;

/**
 * Created by Mohamed.Shaaban on 3/5/2018.
 */

public interface IUser {
    void setUserId(Long id);

    Long getUserId();

    String getEmail();

    void setEmail(String emailAddress);

    void setPassword(String password);

    String getPassword();

}
