package server.commands;

import server.db.MovieDAO;

/**
 * Только серверная команда: сохранить состояние коллекции вручную (не нужна при использовании БД).
 */
public class ServerSaveCommand implements Command {
    private final MovieDAO movieDAO;

    public ServerSaveCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) {
        return "Сохранение вручную больше не требуется — данные хранятся в базе данных.";
    }

    @Override
    public String getDescription() {
        return "[только сервер] фиктивное сохранение (БД обновляется автоматически)";
    }
}