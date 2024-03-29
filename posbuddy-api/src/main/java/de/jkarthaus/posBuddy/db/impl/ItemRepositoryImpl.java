package de.jkarthaus.posBuddy.db.impl;

import de.jkarthaus.posBuddy.db.ItemRepository;
import de.jkarthaus.posBuddy.db.entities.ItemEntity;
import de.jkarthaus.posBuddy.exception.ItemNotFoundException;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Singleton
public class ItemRepositoryImpl implements ItemRepository {

    private final EntityManager entityManager;

    @ReadOnly
    @Override
    public List<ItemEntity> findByStation(String stationId) {
        TypedQuery<ItemEntity> query = entityManager.createQuery(
                "select i from items as i where i.dispensingStationId = :dispensingStation",
                ItemEntity.class
        ).setParameter("dispensingStation", stationId);
        return query.getResultList();
    }

    @ReadOnly
    @Override
    public List<ItemEntity> findAll() {
        TypedQuery<ItemEntity> query = entityManager.createQuery(
                "select i from items as i order by i.dispensingStationId,i.itemText",
                ItemEntity.class
        );
        return query.getResultList();
    }

    @ReadOnly
    @Override
    public ItemEntity findItemById(String itemId) throws ItemNotFoundException {
        try {
            TypedQuery<ItemEntity> query = entityManager.createQuery(
                    "select i from items as i where i.id = :itemId",
                    ItemEntity.class
            ).setParameter("itemId", itemId);
            return query.getSingleResult();
        } catch (NoResultException noResultException) {
            throw new ItemNotFoundException("Item:" + itemId + " not exsits");
        }
    }


    @Override
    @Transactional
    public void clearItems() {
        Query query = entityManager
                .createQuery("delete from items");
        query.executeUpdate();
        entityManager.flush();
    }

    @Override
    @Transactional
    public void addItem(ItemEntity itemEntity) {
        entityManager.persist(itemEntity);
    }

}
