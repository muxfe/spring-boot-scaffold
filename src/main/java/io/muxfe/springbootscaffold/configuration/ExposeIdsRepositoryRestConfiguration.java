package io.muxfe.springbootscaffold.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ExposeIdsRepositoryRestConfiguration extends RepositoryRestConfigurerAdapter {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    List<Class<?>> entityClasses = getAllManagedEntityTypes();
    for (Class<?> entityClass : entityClasses) {
      config.exposeIdsFor(entityClass);
    }
  }

  private List<Class<?>> getAllManagedEntityTypes() {
    List<Class<?>> entityClasses = new ArrayList<>();
    Metamodel metamodel = entityManagerFactory.getMetamodel();
    for (ManagedType<?> managedType : metamodel.getManagedTypes()) {
      Class<?> javaType = managedType.getJavaType();
      if (javaType.isAnnotationPresent(Entity.class)) {
        entityClasses.add(managedType.getJavaType());
      }
    }
    return entityClasses;
  }

}
