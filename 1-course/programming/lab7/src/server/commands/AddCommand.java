package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

/**
 * Команда добавления нового фильма в коллекцию.
 * Логика: сначала вставка в БД (через репозиторий), затем — добавление в память.
 */
public class AddCommand implements Command {
    private final MovieRepository movieRepo;

    public AddCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }
        long id = movieRepo.add(movie, user); // БД → при успехе — память
        return "Фильм добавлен с ID: " + id;
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
