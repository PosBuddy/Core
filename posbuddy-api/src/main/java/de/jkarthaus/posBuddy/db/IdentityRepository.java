package de.jkarthaus.posBuddy.db;

import de.jkarthaus.posBuddy.db.entities.IdentityEntity;
import de.jkarthaus.posBuddy.exception.posBuddyIdNotAllocatedException;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;


public interface IdentityRepository {
    IdentityEntity findById(String podBuddyId) throws posBuddyIdNotAllocatedException;

    @ReadOnly
    boolean isPosBuddyIdAllocatable(String posBuddyId);


    @Transactional
    void updateIdentityEntity(IdentityEntity identityEntity);

    @Transactional
    void allocatePosBuddyId(IdentityEntity identityEntity);
}
