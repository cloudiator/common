package io.github.cloudiator.persistance;

public interface DomainRepository<D> {

  void save(D d);

  void delete(D d);

  D findById();
}
