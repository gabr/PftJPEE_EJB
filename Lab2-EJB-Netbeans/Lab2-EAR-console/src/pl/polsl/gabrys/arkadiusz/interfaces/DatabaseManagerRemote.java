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
 * DatabaseManager bean remote interfacee
 * @author arkad_000
 * @version 1.0
 */
@Remote
public interface DatabaseManagerRemote {
    
    /**
     * Finds Author entity by its id
     * @param id the entity id
     * @return the entity with given id
     * @throws IllegalArgumentException if the id is null
     */
    Author findAuthorById(Long id) throws IllegalArgumentException;
        
    /**
     * Returns all authors
     * @return the list with all authors
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    List<Author> findAllAuthors() throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    
    /**
     * Returns all authors with given name
     * @param name the author name
     * @return the list of authors with given name
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    List<Author> findAuthorsByName(String name) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    
     /**
     * Finds Book entity by its id
     * @param id the entity id
     * @return the entity with given id
     * @throws IllegalArgumentException if the id is null
     */   
    Book findBookById(Long id) throws IllegalArgumentException;
    
    /**
     * Returns all books
     * @return the list with all books
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    List<Book> findAllBooks() throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    
    /**
     * Returns al books with given title
     * @param title the book title
     * @return the list of books with given title
     * @throws IllegalArgumentException if given class is not valid entity class for this operation
     * @throws QueryTimeoutException if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    List<Book> findBooksByTitle(String title) throws IllegalArgumentException, QueryTimeoutException, TransactionRequiredException, PessimisticLockException, LockTimeoutException, PersistenceException;
    
    /**
     * Adds new Author entity
     * @param name the author name
     * @param lastName the author last name
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    void persistAuthor(String name, String lastName) throws TransactionRequiredException;
    
    /**
     * Adds new Book entity
     * @param title the book title
     * @param pages the number of book pages
     * @param releaseDate the book release date
     * @param authorId the book author id
     * @throws IllegalArgumentException if the author id is null or if the author doesn't exists
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    void persistBook(String title, Long pages, Date releaseDate, Long authorId) throws IllegalArgumentException, TransactionRequiredException;
    
    /**
     * Updates given Author entity
     * @param id the author id
     * @param name the new author name
     * @param lastName the new author last name
     * @throws IllegalArgumentException if the author is a removed entity or if the id is null or the given author doesn't exists
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transactio
     */
    void mergeAuthor(Long id, String name, String lastName) throws IllegalArgumentException, TransactionRequiredException;
    
    /**
     * Updates given Book entity
     * @param id the book id
     * @param title the new book title
     * @param pages the new number of pages
     * @param releaseDate the new release date
     * @param authorId the id of the new author
     * @throws IllegalArgumentException if the book is a removed entity or if the id is null
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transactio
     */
    void mergeBook(Long id, String title, Long pages, Date releaseDate, Long authorId) throws IllegalArgumentException, TransactionRequiredException;
    
    /**
     * Removes Author entity
     * @param id the author id
     * @throws IllegalArgumentException if the instance is a detached entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    void removeAuthor(Long id) throws IllegalArgumentException, TransactionRequiredException;
    
    /**
     * Removes Book entity
     * @param id the book id
     * @throws IllegalArgumentException if the instance is a detached entity
     * @throws TransactionRequiredException if invoked on a container-managed entity manager of type PersistenceContextType.TRANSACTION and there is no transaction
     */
    void removeBook(Long id) throws IllegalArgumentException, TransactionRequiredException;

}
