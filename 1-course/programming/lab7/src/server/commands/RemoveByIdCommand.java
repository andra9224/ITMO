package server.commands;

import server.repository.MovieRepository;

/**
 * Команда удаления фильма по ID (если пользователь владелец).
 * Логика: сначала DELETE в БД, затем — удаление из памяти.
 */
public class RemoveByIdCommand implements Command {
    private final MovieRepository movieRepo;

    public RemoveByIdCommand(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Object execute(String[] args, Object data, String user, String passwordHash) throws Exception {
        if (args.length < 1) throw new IllegalArgumentException("Не указан ID.");

        long id;
        try {
            id = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID должен быть целым числом.");
        }

        boolean success = movieRepo.remove(id, user); // БД → память
        return success
                ? "Фильм с ID " + id + " удалён."
                : "Фильм не найден или не принадлежит вам.";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по id";
    }
}
