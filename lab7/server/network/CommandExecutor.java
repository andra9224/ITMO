package server.network;

import common.exchange.CommandRequest;
import common.exchange.CommandResponse;
import server.commands.Command;
import server.commands.CommandFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Выполняет команды на основе входящего запроса.
 */
public class CommandExecutor {
    private final CommandFactory commandFactory;

    public CommandExecutor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public CommandResponse execute(CommandRequest request) {
        String name = request.getCommandName();
        String[] args = request.getArguments();
        Object data = request.getData();
        String user = request.getUsername();
        String passwordHash = request.getPasswordHash();

        Command command = commandFactory.getCommand(name);
        if (command == null) {
            return new CommandResponse("Команда '" + name + "' не найдена", false, null);
        }


        boolean isPublicCommand = name.equals("login") || name.equals("register");
        if (!isPublicCommand) {
            try {
                if (!commandFactory.getUserManager().authenticateUser(user, passwordHash)) {
                    return new CommandResponse("Ошибка авторизации: неверный логин или пароль.", false, null);
                }
            } catch (Exception e) {
                return new CommandResponse("Ошибка при проверке пользователя: " + e.getMessage(), false, null);
            }
        }

        try {
            Object result = command.execute(args, data, user, passwordHash);
            return new CommandResponse("Команда выполнена успешно", true, result);
        } catch (Exception e) {
            return new CommandResponse("Ошибка: " + e.getMessage(), false, null);
        }
    }
}