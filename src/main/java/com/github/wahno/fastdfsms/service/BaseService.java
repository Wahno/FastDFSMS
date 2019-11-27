package com.github.wahno.fastdfsms.service;

import com.github.wahno.fastdfsms.exception.FileException;

/**
 * @author WANGHAO 2019-11-27
 * @since 0.0.1
 */
public interface BaseService<T,ID> {
    /**
     * save entity
     * insert or update
     * @param t entity
     * @return entity with id
     * @see FileException
     * @throws FileException fileException
     */
    T save(T t) throws FileException;

    /**
     * find entity
     * @param id id
     * @return entity
     * @throws FileException fileException
     */
    T find(ID id) throws FileException;

    /**
     * delete entity
     * @param id id
     * @throws FileException fileException
     */
    void delete(ID id) throws FileException;
}
