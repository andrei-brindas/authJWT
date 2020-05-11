package ro.andrei.auth.service;

import java.util.UUID;

public interface CRUDService<T> {
   T create(T t);
   T find(Long i);
   T update(T t);
   void delete(T t);
}
