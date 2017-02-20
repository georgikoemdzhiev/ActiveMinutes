package georgikoemdzhiev.activeminutes.data_layer;

/**
 * Created by Georgi Koemdzhiev on 19/02/2017.
 */

public interface IUserManager {

    int getLoggedInUserId();

    void setLoggedInUser(int userId);

    boolean isLoggedIn();

    void setLoggedIn(boolean keepLoggedIn);
}
