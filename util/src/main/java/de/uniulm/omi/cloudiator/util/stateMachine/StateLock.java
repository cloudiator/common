package de.uniulm.omi.cloudiator.util.stateMachine;

import static com.google.common.base.Preconditions.checkNotNull;

import de.uniulm.omi.cloudiator.domain.Identifiable;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Objects;

public class StateLock {


  private final HashMap<HashKey, Boolean> lockMap = new HashMap<>();

  public void startTransition(Identifiable identifiable) {
    synchronized (StateLock.class) {

      final Boolean aBoolean = lockMap.get(HashKey.of(identifiable));
      if (aBoolean != null && aBoolean) {
        throw new ConcurrentModificationException(
            String.format("Object %s is already in a state transition.", identifiable));
      }
      lockMap.put(HashKey.of(identifiable), true);

    }
  }

  public void stopTransition(Identifiable identifiable) {
    synchronized (StateLock.class) {
      lockMap.remove(HashKey.of(identifiable));
    }
  }

  private static final class HashKey {

    private final Class<? extends Identifiable> clazz;
    private final String id;


    private static HashKey of(Identifiable identifiable) {
      return new HashKey(identifiable.getClass(), identifiable.id());
    }

    private HashKey(Class<? extends Identifiable> clazz, String id) {

      checkNotNull(clazz, "clazz is null");
      checkNotNull(id, "id is null");

      this.clazz = clazz;
      this.id = id;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      HashKey hashKey = (HashKey) o;
      return clazz.equals(hashKey.clazz) &&
          id.equals(hashKey.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(clazz, id);
    }
  }


}
