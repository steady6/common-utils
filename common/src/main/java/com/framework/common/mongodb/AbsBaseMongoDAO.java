/**
 */
package com.framework.common.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

/**
 *
 */
public abstract class AbsBaseMongoDAO<T> implements BaseMongoDAO<T> {

    /**
     * spring mongodb　集成操作类　 
     */
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    @Override
    public T update(Query query, Update update) {
        return mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    @Override
    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    @Override
    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    @Override
    public Page<T> findPage(Page<T> page, Query query) {
        long count = this.count(query);
        page.setCount((int) count);
        int pageNumber = page.getCurrentPage();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.setEntities(rows);
        return page;
    }

    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }


    /**
     * 获取需要操作的实体类class 
     *
     * @return
     */
    private Class<T> getEntityClass() {
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }

    /**
     * 批量插入
     * @param collection
     * @return
     */
    @Override
    public void batchInsert(Collection<T> collection) {
        mongoTemplate.insert(collection, this.getEntityClass());
    }

    /**
     * 注入mongodbTemplate 
     *
     * @param mongoTemplate
     */
    protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);

}

