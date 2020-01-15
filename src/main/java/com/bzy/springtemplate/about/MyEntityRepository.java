package com.bzy.springtemplate.about;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.bzy.springtemplate.about.QMyEntity.myEntity;

@Repository
public class MyEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void createOne() {
        MyEntity myEntity = new MyEntity();
        myEntity.setText("text");
        myEntity.setNtext("nText");
        entityManager.persist(myEntity);
    }

    public MyEntity getOne() {
        return new JPAQuery<MyEntity>(entityManager).from(myEntity)
                .orderBy(myEntity.id.desc())
                .fetchFirst();
    }

}
