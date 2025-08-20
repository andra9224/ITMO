package server.commands;

import server.db.MovieDAO;

/**
 * Команда очистки всех фильмов пользователя.
 */
public class ClearCommand implements Command {
    private final MovieDAO movieDAO;

    public ClearCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        int removed = movieDAO.clearUserMovies(user);
        return "Удалено " + removed + " ваших фильмов.";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию (только свои элементы)";
    }
}