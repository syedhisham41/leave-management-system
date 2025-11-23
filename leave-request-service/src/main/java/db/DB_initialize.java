package db;

import org.flywaydb.core.Flyway;

import config.PropertyConfig;

public class DB_initialize {

	public static void db_initializer() {

		String dbUrl = PropertyConfig.get("DB_URL");
		String migrationLocation = PropertyConfig.get("MIGRATION_LOCATION");
		String dbUser = PropertyConfig.get("DB_USER");
		String dbPassword = PropertyConfig.get("DB_PASSWORD");

		Flyway flyway = Flyway.configure()
				.dataSource(dbUrl, dbUser, dbPassword)
				.locations(migrationLocation)
				.load();

		flyway.migrate();
	}
}
