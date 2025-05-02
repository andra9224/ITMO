package commands;

import collection.MovieCollection;
import input.MovieInputParser;
import model.Movie;

/**
 * Команда обновления существующего фильма по ID.
 */
public class UpdateCommand implements Command {
    private final MovieCollection collection;
    private final MovieInputParser parser;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     * @param parser парсер для ввода обновлённого фильма
     */
    public UpdateCommand(MovieCollection collection, MovieInputParser parser) {
        this.collection = collection;
        this.parser = parser;
    }

    /**
     * Выполняет команду: обновляет фильм с заданным ID.
     * @param args ID фильма
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Не указан ID для обновления");
            return;
        }

        try {
            long id = Long.parseLong(args[0]);
            if (id <= 0) {
                System.out.println("ID должен быть положительным числом.");
                return;
            }

            Movie existing = collection.getById(id);
            if (existing == null) {
                System.out.println("Фильм с ID " + id + " не найден.");
                return;
            }

            System.out.println("Обновление фильма с ID " + id);
            Movie updated = parser.parseMovie();

            if (collection.update(id, updated)) {
                System.out.println("Фильм с ID " + id + " успешно обновлён.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ID.");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции по id";
    }
}
