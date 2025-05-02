package commands;

import collection.MovieCollection;
import storage.JsonFileManager;

import java.io.IOException;

/**
 * Команда сохранения коллекции фильмов в файл.
 */
public class SaveCommand implements Command {
    private final MovieCollection collection;
    private final JsonFileManager fileManager;
    private final String fileName;

    /**
     * Конструктор.
     * @param collection коллекция фильмов
     * @param fileManager менеджер для сохранения в файл
     * @param fileName имя файла для сохранения
     */
    public SaveCommand(MovieCollection collection, JsonFileManager fileManager, String fileName) {
        this.collection = collection;
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    /**
     * Выполняет команду: сохраняет коллекцию в указанный файл.
     * @param args не используются
     */
    @Override
    public void execute(String[] args) {
        try {
            fileManager.saveToFile(fileName, collection.getMovies());
            System.out.println("Коллекция успешно сохранена в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    /**
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
