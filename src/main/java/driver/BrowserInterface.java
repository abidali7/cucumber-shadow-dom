package driver;

public interface BrowserInterface {
    public void createWebdriver() throws Exception;

    default void stop() {
        // noop
    }
}
