package fr.uga.l3miage.library.data.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name= "book")
@NamedQueries({
        @NamedQuery(name = "all-books", query = "SELECT b FROM Book b ORDER BY title"),
        @NamedQuery(name = "find-books-by-title", query = "SELECT b FROM Book b WHERE LOWER(b.title) LIKE CONCAT('%', LOWER(:titlePart), '%')"),
        @NamedQuery(name = "find-books-by-author-and-title", query = "SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId and LOWER(b.title) LIKE CONCAT('%', LOWER(:titlePart), '%')"),
        @NamedQuery(name="find-books-by-authors-name", query="SELECT b FROM Book b JOIN b.authors a WHERE LOWER(a.fullName) LIKE CONCAT('%', LOWER(:namePart), '%')"),
        @NamedQuery(name="find-books-by-several-authors", query="SELECT b FROM Book b WHERE SIZE(b.authors) > :count")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(name="isbn", nullable = false, unique = true)
    private long isbn;
    @Column(name="publisher", nullable = false)
    private String publisher;
    @Column(name="`year`", nullable = false)
    private short year;
    @Enumerated(EnumType.STRING)
    private Language language;
    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        if (this.authors == null) {
            this.authors = new HashSet<>();
        }
        this.authors.add(author);
    }

    public enum Language {
        FRENCH,
        ENGLISH
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn && year == book.year && Objects.equals(title, book.title) && Objects.equals(publisher, book.publisher) && language == book.language && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, publisher, year, language, authors);
    }
}
