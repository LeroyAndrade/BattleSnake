// Lokale userklasse.
// Implementeert Principal.
// Bevat username, password en role.

package nl.hu.bep.setup.SnakeGame.Security;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

public class SnakeUser implements Principal {
    private String username;
    private String password;
    private String role;

    // accounts/users bewaren in een Map met username als key.
    private static Map<String, SnakeUser> users = new HashMap<>();

    public SnakeUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // User lokaal aanmaken
    public static boolean addUser(SnakeUser newUser) {
        if (newUser.getName().isEmpty() || users.containsKey(newUser.getName())) {
            return false;
        }

        users.put(newUser.getName(), newUser);
        return true;
    }

    public static SnakeUser getUserByName(String username) {
        return users.get(username);
    }

    // Deze methode gebruikt AuthenticationResource straks bij login.
    // Als login klopt, geef ik de rol terug.
    // Als login fout is, geef ik null terug.
    public static String validateLogin(String username, String password) {
        SnakeUser user = getUserByName(username);

        if (user == null) {
            return null;
        }

        if (!user.password.equals(password)) {
            return null;
        }

        return user.role;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getRole() {
        return role;
    }
}