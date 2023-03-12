package fr.uga.l3miage.library.data.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name= "Borrow")
@NamedQueries({
        @NamedQuery(name = "find_in_progress_by_user", query = "SELECT b FROM Borrow b WHERE b.borrower.id=:userId"),
        @NamedQuery(name = "current-borrowed-books-by-user", query = "SELECT b FROM Borrow b WHERE b.borrower.id=:userId AND b.finished = false"),
        @NamedQuery(name = "found-All-borrow-that-will-late-within", query = "SELECT b FROM Borrow b WHERE b.requestedReturn < :dateLate ORDER BY b.requestedReturn ASC"),
        @NamedQuery(name = "found-all-late-borrow", query="SELECT b FROM Borrow b WHERE b.requestedReturn < CURRENT_DATE ORDER BY b.requestedReturn ASC"),
        @NamedQuery(name="top3-working-librarians", query="SELECT b.librarian, COUNT(b) as borrowNumber FROM Borrow b GROUP BY b.librarian ORDER BY borrowNumber DESC LIMIT 3")
})
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Book> books;
    @Temporal(TemporalType.DATE)
    private Date start;
    @Temporal(TemporalType.DATE)
    private Date requestedReturn;
    @OneToOne
    @JoinColumn(name = "`user_id`")
    private User borrower;
    @OneToOne
    @JoinColumn(name = "`librarian_id`")
    private Librarian librarian;
    @Column(name="finished")
    private boolean finished;

    public Long getId() {
        return id;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getRequestedReturn() {
        return requestedReturn;
    }

    public void setRequestedReturn(Date end) {
        this.requestedReturn = end;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrow = (Borrow) o;
        return finished == borrow.finished && Objects.equals(id, borrow.id) && Objects.equals(books, borrow.books) && Objects.equals(start, borrow.start) && Objects.equals(requestedReturn, borrow.requestedReturn) && Objects.equals(borrower, borrow.borrower) && Objects.equals(librarian, borrow.librarian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, books, start, requestedReturn, borrower, librarian, finished);
    }
}

