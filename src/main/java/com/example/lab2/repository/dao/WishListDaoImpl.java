package com.example.lab2.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.lab2.entity.WishList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class WishListDaoImpl implements WishListDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long create(WishList wishList) {
        entityManager.persist(wishList);
        return wishList.getUserId();
    }

    @Override
    public Optional<WishList> read(Long userId) {
       return Optional.ofNullable(entityManager.find(WishList.class, userId));
    }

    @Override
    public void update(WishList wishList) {
        entityManager.merge(wishList);
    }

    @Override
    public void delete(Long userId) {
        WishList wishList = entityManager.find(WishList.class, userId);
        if (wishList != null) {
            entityManager.remove(wishList);
        }
    }

    @Override
    public List<WishList> findAllByPostId(Long postId) {
        String sql = "SELECT w FROM wish_lists w WHERE ANY(list) = :post-id";

        return entityManager.createQuery(sql, WishList.class)
                .setParameter("post-id", postId)
                .getResultList();
    }
}
