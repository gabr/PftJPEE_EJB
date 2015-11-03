package pl.polsl.gabrys.arkadiusz.interfaces;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import pl.polsl.gabrys.arkadiusz.model.Author;
import pl.polsl.gabrys.arkadiusz.model.Book;

/**
 *
 * @author arkad_000
 * @version 1.0
 */
@Remote
public interface DatabaseManagerRemote {

    Author findAuthorById(Long id) throws IllegalArgumentException;
    List<Author> findAllAuthors() throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    List<Author> findAuthorsByName(String name) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    Book findBookById(Long id) throws IllegalArgumentException;
    List<Book> findAllBooks() throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    List<Book> findBooksByTitle(String title) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    void persistAuthor(String name, String lastName) throws TransactionRequiredException;
    void persistBook(String title, Long pages, Date releaseDate, Long authorId) throws IllegalArgumentException, TransactionRequiredException;
    void mergeAuthor(Long id, String name, String lastName) throws IllegalArgumentException, TransactionRequiredException;
    void mergeBook(Long id, String title, Long pages, Date releaseDate, Long authorId) throws IllegalArgumentException, TransactionRequiredException;
    void removeAuthor(Long id) throws IllegalArgumentException, TransactionRequiredException;
    void removeBook(Long id) throws IllegalArgumentException, TransactionRequiredException;

}
