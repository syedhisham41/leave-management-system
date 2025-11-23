package config;

import io.github.cdimascio.dotenv.Dotenv;

public class PropertyConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./leave-request-service")
            .filename(".env")
            .ignoreIfMissing() // Docker env support
            .load();

    public static String get(String key) {

        // Prefer system environment variables (Docker)
        String value = System.getenv(key);
        if (value != null)
            return value;

        // Fallback to local .env (dev mode)
        return dotenv.get(key);
    }

}
