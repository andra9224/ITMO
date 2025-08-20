package server.commands;

import server.users.UserManager;

/**
 * Команда регистрации нового пользователя.
 */
public class RegisterCommand implements Command {
    private final UserManager userManager;

    public RegisterCommand(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 2) throw new IllegalArgumentException("Необходимо указать логин и md5-хеш пароля.");

        String username = args[0];
        String hash = args[1];

        boolean success = userManager.registerUser(username, hash);
        return success
                ? "Регистрация прошла успешно."
                : "Пользователь с таким логином уже существует.";
    }

    @Override
    public String getDescription() {
        return "зарегистрировать нового пользователя: register <login> <md5-пароль>";
    }
}