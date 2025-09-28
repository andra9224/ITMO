package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

/**
 * Команда добавляет фильм, если он меньше текущего минимума (по compareTo).
 * Читает коллекцию из памяти и при необходимости пишет: БД → память через репозиторий.
 */
public class AddIfMinCommand implements Command {
    private final MovieRepository movieRepo;

    public AddIfMinCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        List<Movie> all = movieRepo.all(); // чтение из памяти
        Optional<Movie> currentMin = all.stream().min(Movie::compareTo);

        if (currentMin.isEmpty() || movie.compareTo(currentMin.get()) < 0) {
            long id = movieRepo.add(movie, user); // БД → память
            return "Фильм добавлен (меньше минимального элемента), ID: " + id;
        } else {
            return "Фильм не добавлен (не меньше текущего минимума).";
        }
    }

    @Override
    public String getDescription() {
        return "добавить элемент, если он меньше минимального";
    }
}
