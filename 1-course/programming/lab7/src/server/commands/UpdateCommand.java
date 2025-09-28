package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

/**
 * Команда обновления фильма по ID.
 * Логика: сначала UPDATE в БД (с проверкой владельца), при успехе — обновление в памяти.
 */
public class UpdateCommand implements Command {
    private final MovieRepository movieRepo;

    public UpdateCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Не указан ID.");
        if (!(data instanceof Movie movie)) throw new IllegalArgumentException("Ожидался объект Movie");

        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID должен быть целым числом.");
        }

        boolean success = movieRepo.update(id, movie, user); // БД → память
        return success
                ? "Фильм с ID " + id + " обновлён."
                : "Фильм с ID " + id + " не найден или не принадлежит вам.";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по id";
    }
}
