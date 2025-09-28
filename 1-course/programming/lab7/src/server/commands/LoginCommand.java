package server.commands;

import server.users.UserManager;

/**
 * Команда авторизации пользователя.
 */
public class LoginCommand implements Command {
    private final UserManager userManager;

    public LoginCommand(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("Нужно указать логин и md5-хеш пароля");

        String username = args[0];
        String hash = args[1];

        boolean success = userManager.authenticateUser(username, hash);

        if (success) {
            return "Аутентификация успешна.";
        } else {
            throw new IllegalArgumentException("Неверный логин или пароль.");
        }
    }


    @Override
    public String getDescription() {
        return "войти в систему: login <login> <md5-пароль>";
    }
}