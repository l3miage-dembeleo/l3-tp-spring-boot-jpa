package fr.uga.l3miage.library.data.domain;



import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name= "author")
@NamedQueries({
        @NamedQuery(name = "all-authors", query = "SELECT fullName FROM Author ORDER BY fullName ASC"),
        @NamedQuery(name = "findByName", query = "SELECT a FROM Author a WHERE a.fullName LIKE :namePart"),
        @NamedQuery(name = "isAuthorByIdHavingCoAuthoredBooks", query = "SELECT DISTINCT a FROM Author a JOIN a.books boo JOIN boo.authors aux WHERE a.id = :authorId AND aux.id != a.id")
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="fullName", nullable = false)
    private String fullName;

    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new HashSet<>();
        }
        this.books.add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(fullName, author.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }
}

