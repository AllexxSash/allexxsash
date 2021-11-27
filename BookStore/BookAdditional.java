package ru.allexxsash.BookStore;

public class BookAdditional {
    private BookGenre genre;
    private int count;


    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BookAdditional(BookGenre genre, int count) {
        this.genre = genre;
        this.count = count;
    }
}
