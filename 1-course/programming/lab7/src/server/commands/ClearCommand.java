package server.commands;

import server.repository.MovieRepository;

/**
 * Команда очистки всех фильмов пользователя.
 * Логика: для каждого фильма — DELETE в БД, при успехе — удаление из памяти.
 */
public class ClearCommand implements Command {
    private final MovieRepository movieRepo;

    public ClearCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        int removed = movieRepo.clearOwned(user); // БД → память (по каждому ID)
        return "Удалено " + removed + " ваших фильмов.";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию (только свои элементы)";
    }
}
