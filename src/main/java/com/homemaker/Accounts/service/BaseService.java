package com.homemaker.Accounts.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Service;
//can not take as service for class outside spring otherwise No-Bean defintion found error occred.

import com.google.common.collect.Lists;
import com.homemaker.Accounts.entities.BaseDomain;

import net.minidev.json.JSONObject;

//@Service //not aware of.
public class BaseService<T extends BaseDomain> {

    protected JpaRepository<T, Long> jpaRepository;

    public BaseService(JpaRepository<T, Long> repository) {
        jpaRepository = repository;
    }

    public Iterable<T> saveAll(List<T> all) {
        return jpaRepository.saveAll(all);
    }

    public T save(T entity) {
        jpaRepository.save(entity);
        return entity;
    }

    public T save(T entity, Object eventPayload, JSONObject author) {
        jpaRepository.save(entity);
        return entity;
    }

    public T update(T entity, Object eventPayload, JSONObject author) {
        jpaRepository.save(entity);
        return entity;
    }

    public T findById(Long id) {
        Optional<T> byId = jpaRepository.findById(id);
        return byId.orElse(null);
    }

    public List<T> findAll() {
        return Lists.newArrayList(jpaRepository.findAll());
    }

}
 