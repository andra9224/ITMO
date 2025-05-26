package model;

/**
 * Перечисление возможных жанров фильмов.
 */
public enum MovieGenre {
    ACTION,
    THRILLER,
    FANTASY;

    /**
     * Выводит список доступных жанров в консоль.
     */
    public static void printGenres() {
        System.out.println("Доступные жанры:");
        for (MovieGenre genre : values()) {
            System.out.println("- " + genre);
        }
    }
}
