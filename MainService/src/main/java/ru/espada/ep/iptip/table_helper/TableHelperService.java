package ru.espada.ep.iptip.table_helper;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Service
public class TableHelperService {

    @Autowired
    private ApplicationContext applicationContext;

    Map<String, JpaRepository<?, ?>> repositoryMap = new HashMap<>(); // table -> repository
    Map<String, Class<?>> entityMap = new HashMap<>(); // table -> entity

    @PostConstruct
    public void init() {
        String[] repositoryBeans = applicationContext.getBeanNamesForType(JpaRepository.class);
        for (String repositoryBean : repositoryBeans) {
            Object repository = applicationContext.getBean(repositoryBean);
            Class<?> entityClass = getEntityClass(repository);
            if (entityClass != null) {
                Table table = entityClass.getAnnotation(Table.class);
                if (table != null) {
                    String tableName = table.name();
                    repositoryMap.put(tableName, (JpaRepository<?, ?>) repository);
                    entityMap.put(tableName, entityClass);
                }
            }
        }
    }

    private Class<?> getEntityClass(Object repository) {
        ParameterizedType parameterizedType = (ParameterizedType) repository.getClass().getGenericSuperclass();
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    public JpaRepository<?, ?> getRepository(String tableName) {
        return repositoryMap.get(tableName);
    }

    public Class<?> getEntityClass(String tableName) {
        return entityMap.get(tableName);
    }

}
