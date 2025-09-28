package server.commands;

import server.collection.MovieCollection;
import server.storage.JsonFileManager;

import java.io.IOException;

/**
 * Команда, сохраняющая коллекцию в файл (только для сервера).
 */
public class ServerSaveCommand implements Command {
    private final MovieCollection collection;
    private final JsonFileManager fileManager;
    private final String fileName;

    public ServerSaveCommand(MovieCollection collection, JsonFileManager fileManager, String fileName) {
        this.collection = collection;
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    @Override
    public Object execute(String[] args, Object data) {
        try {
            fileManager.saveToFile(fileName, collection.getMovies());
            return "Коллекция сохранена вручную с сервера.";
        } catch (IOException e) {
            return "Ошибка при сохранении: " + e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return "[Только сервер] сохранить коллекцию в файл";
    }
}
