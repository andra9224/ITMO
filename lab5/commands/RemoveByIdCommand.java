package commands;

import collection.MovieCollection;

/**
 * Команда удаления фильма из коллекции по его ID.
 */
public class RemoveByIdCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public RemoveByIdCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Выполняет команду: удаляет фильм по указанному ID.
     * @param args ID
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Не указан ID для удаления");
            return;
        }

        try {
            long id = Long.parseLong(args[0]);
            if (id <= 0) {
                System.out.println("ID должен быть положительным числом.");
                return;
            }

            if (collection.removeById(id)) {
                System.out.println("Фильм с ID " + id + " успешно удален.");
            } else {
                System.out.println("Фильм с ID " + id + " не найден.");
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
        return "удалить элемент из коллекции по id";
    }
}
