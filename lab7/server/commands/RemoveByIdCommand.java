package server.commands;

import server.db.MovieDAO;

/**
 * Команда удаления фильма по ID (если пользователь владелец).
 */
public class RemoveByIdCommand implements Command {
    private final MovieDAO movieDAO;

    public RemoveByIdCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Не указан ID.");

        long id = Long.parseLong(args[0]);
        boolean success = movieDAO.removeById(id, user);
        return success
                ? "Фильм с ID " + id + " удалён."
                : "Фильм не найден или не принадлежит вам.";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по id";
    }
}