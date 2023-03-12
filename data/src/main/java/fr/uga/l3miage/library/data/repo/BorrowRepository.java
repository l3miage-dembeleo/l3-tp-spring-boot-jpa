package fr.uga.l3miage.library.data.repo;

import fr.uga.l3miage.library.data.domain.Book;
import fr.uga.l3miage.library.data.domain.Borrow;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class BorrowRepository implements CRUDRepository<String, Borrow> {

    private final EntityManager entityManager;

    @Autowired
    public BorrowRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Borrow save(Borrow entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Borrow get(String id) {
        return entityManager.find(Borrow.class, id);
    }

    @Override
    public void delete(Borrow entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<Borrow> all() {
        return entityManager.createQuery("from Borrow", Borrow.class).getResultList();
    }
    /**
     * Trouver des emprunts en cours pour un emprunteur donné
     *
     * @param userId l'id de l'emprunteur
     * @return la liste des emprunts en cours
     */
    public List<Borrow> findInProgressByUser(String userId) {
        List<Borrow> l = entityManager.createNamedQuery("current-borrowed-books-by-user", Borrow.class)
                .setParameter("userId", userId)
                .getResultList();
        return l;
    }

    /**
     * Compte le nombre total de livres emprunté par un utilisateur.
     *
     * @param userId l'id de l'emprunteur
     * @return le nombre de livre
     */
    public int countBorrowedBooksByUser(String userId) {
        List<Book> borrowedBooks = new ArrayList<>();
        List<Borrow> borrows = entityManager.createNamedQuery("find_in_progress_by_user", Borrow.class)
                .setParameter("userId", userId)
                .getResultList();

        for (Borrow borrow : borrows) {
            for (Book b : borrow.getBooks()){
                borrowedBooks.add(b);
            }
        }
        return borrowedBooks.size();
    }

    /**
     * Compte le nombre total de livres non rendu par un utilisateur.
     *
     * @param userId l'id de l'emprunteur
     * @return le nombre de livre
     */
    public int countCurrentBorrowedBooksByUser(String userId) {
        List<Book> borrowedBooks = new ArrayList<>();
        List<Borrow> borrows = entityManager.createNamedQuery("current-borrowed-books-by-user", Borrow.class)
                .setParameter("userId", userId)
                .getResultList();

        for (Borrow borrow : borrows) {
            for (Book b : borrow.getBooks()){
                borrowedBooks.add(b);
            }
        }
        return borrowedBooks.size();
    }

    /**
     * Recherche tous les emprunt en retard trié
     *
     * @return la liste des emprunt en retard
     */
    public List<Borrow> foundAllLateBorrow() {
        List<Borrow> borrows = entityManager.createNamedQuery("found-all-late-borrow", Borrow.class)
                .getResultList();
        return borrows;
    }

    /**
     * Calcul les emprunts qui seront en retard entre maintenant et x jours.
     *
     * @param days le nombre de jour avant que l'emprunt soit en retard
     * @return les emprunt qui sont bientôt en retard
     */
    public List<Borrow> findAllBorrowThatWillLateWithin(int days) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, days);
        Date dateLate = cal.getTime();
        List<Borrow> borrows = entityManager.createNamedQuery("found-All-borrow-that-will-late-within", Borrow.class)
                .setParameter("dateLate", dateLate)
                .getResultList();
        return borrows;
    }

}

