package wang.mh.lock;

public interface AquiredLockWorker<T> {

    T invokeAfterLockAquire() throws Exception;
}
