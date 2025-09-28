package server.commands;

import common.model.Movie;
import server.repository.MovieRepository;

import java.util.List;

/**
 * Удаляет все фильмы пользователя, которые меньше заданного (по compareTo).
 * Чтение кандидатов из памяти, на каждом ID выполняется удаление: БД → память через репозиторий.
 */
public class RemoveLowerCommand implements Command {
    private final MovieRepository movieRepo;

    public RemoveLowerCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (!(data instanceof Movie sample)) {
            throw new IllegalArgumentException("Ожидался объект типа Movie");
        }

        // Берём из памяти id всех фильмов ТЕКУЩЕГО пользователя, которые меньше sample
        List<Long> toRemove = movieRepo.idsLowerThan(sample, user);

        int removed = 0;
        for (Long id : toRemove) {
            if (movieRepo.remove(id, user)) { // БД → память
                removed++;
            }
        }
        return "Удалено " + removed + " элементов, меньших заданного.";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие чем заданный (только свои)";
    }
}
