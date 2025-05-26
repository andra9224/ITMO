package server.network;

import common.exchange.CommandRequest;
import common.exchange.CommandResponse;
import server.commands.Command;
import server.commands.CommandFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Обрабатывает запросы клиента и выполняет команды.
 */
public class CommandExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    private final CommandFactory commandFactory;

    public CommandExecutor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    /**
     * Выполняет команду на основе запроса клиента.
     *
     * @param request запрос клиента
     * @return ответ с результатом выполнения
     */
    public CommandResponse execute(CommandRequest request) {
        String name = request.getCommandName();
        String[] args = request.getArguments();
        Object data = request.getData();

        Command command = commandFactory.getCommand(name);

        if (command == null) {
            return new CommandResponse("Команда '" + name + "' не найдена", false, null);
        }

        logger.info("Выполняется команда: {}", name);

        try {
            Object result = command.execute(args, data);
            logger.info("Команда '{}' выполнена успешно", name);
            return new CommandResponse("Команда выполнена успешно", true, result);
        } catch (Exception e) {
            logger.error("Ошибка при выполнении команды '{}': {}", name, e.getMessage());
            return new CommandResponse("Ошибка при выполнении команды: " + e.getMessage(), false, null);
        }
    }
}
