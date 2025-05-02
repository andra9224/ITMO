package commands;

import collection.MovieCollection;

/**
 * Команда, считающая количество фильмов с длиной меньше заданной.
 */
public class CountLessThanLengthCommand implements Command {
    private final MovieCollection collection;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     */
    public CountLessThanLengthCommand(MovieCollection collection) {
        this.collection = collection;
    }

    /**
     * Выполняет команду: считает количество фильмов, длина которых меньше указанной.
     * @param args значение длины фильма
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Не указана длина для сравнения");
            return;
        }

        try {
            int length = Integer.parseInt(args[0]);
            if (length < 0) {
                System.out.println("Длина фильма должна быть неотрицательной");
                return;
            }
            long count = collection.getMovies().stream()
                    .filter(m -> m.getLength() < length)
                    .count();
            System.out.println("Количество фильмов с длиной меньше " + length + ": " + count);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат длины.");
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "вывести количество элементов с length меньше заданного";
    }
}
