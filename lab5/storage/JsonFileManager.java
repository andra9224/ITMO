package storage;

import model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

/**
 * Класс для сохранения и загрузки коллекции фильмов в формате JSON.
 */
public class JsonFileManager {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Сохраняет коллекцию фильмов в файл в формате JSON.
     *
     * @param fileName имя файла
     * @param movies   коллекция фильмов
     * @throws IOException если произошла ошибка записи
     */
    public void saveToFile(String fileName, Collection<Movie> movies) throws IOException {
        JSONArray jsonArray = new JSONArray();

        List<Movie> sorted = movies.stream().sorted().toList();

        for (Movie movie : sorted) {
            jsonArray.put(convertMovieToJson(movie));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonArray.toString(2));
        }
    }

    /**
     * Преобразует объект Movie в формат JSONObject для записи в файл.
     *
     * @param movie объект Movie
     * @return соответствующий JSONObject
     */
    private JSONObject convertMovieToJson(Movie movie) {
        JSONObject jsonMovie = new JSONObject();
        jsonMovie.put("id", movie.getId());
        jsonMovie.put("name", movie.getName());

        JSONObject jsonCoords = new JSONObject();
        jsonCoords.put("x", movie.getCoordinates().getX());
        jsonCoords.put("y", movie.getCoordinates().getY());
        jsonMovie.put("coordinates", jsonCoords);

        jsonMovie.put("creationDate", movie.getCreationDate().toString());
        jsonMovie.put("oscarsCount", movie.getOscarsCount());
        jsonMovie.put("usaBoxOffice", movie.getUsaBoxOffice());
        jsonMovie.put("length", movie.getLength());
        jsonMovie.put("genre", movie.getGenre().toString());

        JSONObject jsonPerson = new JSONObject();
        jsonPerson.put("name", movie.getDirector().getName());
        if (movie.getDirector().getBirthday() != null) {
            jsonPerson.put("birthday", dateFormat.format(movie.getDirector().getBirthday()));
        }
        jsonPerson.put("height", movie.getDirector().getHeight());
        jsonPerson.put("weight", movie.getDirector().getWeight());
        jsonMovie.put("director", jsonPerson);

        return jsonMovie;
    }
}


