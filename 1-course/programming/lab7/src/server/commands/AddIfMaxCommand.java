package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

/**
 * Команда добавляет фильм, если он превышает текущий максимум (по compareTo).
 * Читает коллекцию из памяти и при необходимости пишет: БД → память через репозиторий.
 */
public class AddIfMaxCommand implements Command {
    private final MovieRepository movieRepo;

    public AddIfMaxCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie movie)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        List<Movie> all = movieRepo.all(); // чтение из памяти
        Optional<Movie> currentMax = all.stream().max(Movie::compareTo);

        if (currentMax.isEmpty() || movie.compareTo(currentMax.get()) > 0) {
            long id = movieRepo.add(movie, user); // БД → память
            return "Фильм добавлен (превышает максимальный элемент), ID: " + id;
        } else {
            return "Фильм не добавлен (не превышает текущий максимум).";
        }
    }

    @Override
    public String getDescription() {
        return "добавить элемент, если он превышает максимальный";
    }
}
