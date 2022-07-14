package service;
import java.io.InputStream;

public abstract class ResourceLoader {

    /**
     * Fetches a file from the resources' folder.
     * @param name The file's name.
     * @return A {@code File} if found, {@code null} if not.
     */
    public static InputStream get(String name) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(name);
    }
}
