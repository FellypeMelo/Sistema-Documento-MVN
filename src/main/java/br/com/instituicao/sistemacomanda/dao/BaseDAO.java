package br.com.instituicao.sistemacomanda.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Generic DAO interface defining common CRUD operations.
 * @param <T> Entity type
 * @param <ID> ID type
 */
public interface BaseDAO<T, ID> {
    /**
     * Saves a new entity.
     * @param entity Entity to save
     * @return Saved entity with generated ID
     * @throws SQLException if database error occurs
     */
    T save(T entity) throws SQLException;
    
    /**
     * Updates an existing entity.
     * @param entity Entity to update
     * @return Updated entity
     * @throws SQLException if database error occurs
     */
    T update(T entity) throws SQLException;
    
    /**
     * Finds entity by ID.
     * @param id ID to search for
     * @return Optional containing entity if found
     * @throws SQLException if database error occurs
     */
    Optional<T> findById(ID id) throws SQLException;
    
    /**
     * Lists all entities.
     * @return List of all entities
     * @throws SQLException if database error occurs
     */
    List<T> findAll() throws SQLException;
    
    /**
     * Deletes an entity by ID.
     * @param id ID of entity to delete
     * @throws SQLException if database error occurs
     */
    void delete(ID id) throws SQLException;
    
    /**
     * Checks if an entity exists by ID.
     * @param id ID to check
     * @return true if entity exists
     * @throws SQLException if database error occurs
     */
    boolean exists(ID id) throws SQLException;
}