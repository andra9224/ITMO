package server.commands;

import common.model.Movie;
import server.db.MovieDAO;

/**
 * Команда обновления фильма по ID.
 */
public class UpdateCommand implements Command {
    private final MovieDAO movieDAO;

    public UpdateCommand(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Не указан ID.");
        if (!(data instanceof Movie movie)) throw new IllegalArgumentException("Ожидался объект Movie");

        long id = Long.parseLong(args[0]);
        boolean success = movieDAO.updateMovie(id, movie, user);
        return success
                ? "Фильм с ID " + id + " обновлён."
                : "Фильм с ID " + id + " не найден или не принадлежит вам.";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по id";
    }
}