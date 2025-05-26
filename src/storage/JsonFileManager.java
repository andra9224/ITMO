package storage;

import model.Movie;
import model.Coordinates;
import model.Person;
import model.MovieGenre;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Класс для сохранения и загрузки коллекции фильмов в формате JSON.
 */
public class JsonFileManager {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Сохраняет коллекцию фильмов в файл в формате JSON.
     *
     * @param fileName имя файла
     * @param movies коллекция фильмов
     * @throws IOException если произошла ошибка записи
     */
    public void saveToFile(String fileName, Collection<Movie> movies) throws IOException {
        JSONArray jsonArray = new JSONArray();

        List<Movie> sorted = movies.stream()
                .sorted()
                .toList();

        for (Movie movie : sorted) {
            jsonArray.put(convertMovieToJson(movie));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonArray.toString(2));
        }
    }

    /**
     * Загружает коллекцию фильмов из файла в формате JSON.
     *
     * @param fileName имя файла
     * @return коллекция фильмов в виде TreeSet
     * @throws IOException если произошла ошибка чтения
     */
    public TreeSet<Movie> loadFromFile(String fileName) throws IOException {
        TreeSet<Movie> movies = new TreeSet<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return movies;
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder content = new StringBuilder();
            int ch;
            while ((ch = bis.read()) != -1) {
                content.append((char) ch);
            }

            if (content.length() > 0) {
                JSONArray jsonArray = new JSONArray(content.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    movies.add(parseMovieFromJson(jsonArray.getJSONObject(i)));
                }
            }
        } catch (Exception e) {
            throw new IOException("Ошибка при разборе JSON файла", e);
        }
        return movies;
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

    /**
     * Преобразует JSONObject в объект Movie при загрузке из файла.
     *
     * @param jsonMovie JSONObject, содержащий данные фильма
     * @return объект Movie
     * @throws Exception если произошла ошибка разбора данных
     */
    private Movie parseMovieFromJson(JSONObject jsonMovie) throws Exception {
        Movie movie = new Movie();
        movie.setName(jsonMovie.getString("name"));

        JSONObject jsonCoords = jsonMovie.getJSONObject("coordinates");
        Coordinates coords = new Coordinates();
        coords.setX(jsonCoords.getDouble("x"));
        coords.setY(jsonCoords.getInt("y"));
        movie.setCoordinates(coords);

        movie.setOscarsCount(jsonMovie.getInt("oscarsCount"));
        movie.setUsaBoxOffice(jsonMovie.getInt("usaBoxOffice"));
        movie.setLength(jsonMovie.getInt("length"));
        movie.setGenre(MovieGenre.valueOf(jsonMovie.getString("genre")));

        JSONObject jsonPerson = jsonMovie.getJSONObject("director");
        Person director = new Person();
        director.setName(jsonPerson.getString("name"));
        if (jsonPerson.has("birthday") && !jsonPerson.isNull("birthday")) {
            director.setBirthday(dateFormat.parse(jsonPerson.getString("birthday")));
        }
        director.setHeight((float) jsonPerson.getDouble("height"));
        if (jsonPerson.has("weight") && !jsonPerson.isNull("weight")) {
            director.setWeight(jsonPerson.getInt("weight"));
        }
        movie.setDirector(director);

        return movie;
    }
}

