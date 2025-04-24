package me.firas.skypvp.storage.data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;

public class DataManager {
    private final HikariConfig dataSourceConfig = new HikariConfig();
    private HikariDataSource dataSource;
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }
    public boolean connect(final Plugin plugin) {
        final boolean isConnected = this.initDatasource(plugin.getConfig());
        if (isConnected) {
            this.checkTable();
        }
        return isConnected;
    }
    private void checkTable() {
        String query = "CREATE TABLE IF NOT EXISTS SkyPvP (`uuid` varchar(255) NOT NULL,`ign` varchar(20) NOT NULL, `Time` varchar(20) NOT NULL, `Chat` TEXT, `Enderchest1` TEXT,`Enderchest2` TEXT, `Enderchest3` TEXT,`Enderchest4` TEXT,`Enderchest5` TEXT,`Enderchest6` TEXT,`Enderchest7` TEXT,`Enderchest8` TEXT,`Enderchest9` TEXT, `trail` varchar(32) NOT NULL, `kit` varchar(20) NOT NULL, `killEffect` varchar(32) NOT NULL, `killMessage` varchar(32) NOT NULL, `settings` TEXT,`scramble` varchar(32) NOT NULL,`AchivKills` int DEFAULT 0, `AchivBow` int DEFAULT 0, `AchivTime` int DEFAULT 0, `AchivDeath` int DEFAULT 0,`AchivPickup` int DEFAULT 0, `points` int DEFAULT 0, `kills` int DEFAULT 0, `coins` int DEFAULT 0, `deaths` int DEFAULT 0, `streak` int DEFAULT 0) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        try {
            getConnection().prepareStatement(query).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public boolean initDatasource(final FileConfiguration config) {
        try {
            final String driver = config.getString("sql.driver");
            final String host = config.getString("sql.host");
            final String username = config.getString("sql.user");
            final String database = config.getString("sql.dbname");
            final String databaseProperties = config.getString("sql.databaseProperties");
            final String password = config.getString("sql.pass");
            final int port = config.getInt("sql.port");
            final int maximumPoolSize = config.getInt("sql.maximumPoolSize");
            this.dataSourceConfig.setPoolName("skypvp");
            this.dataSourceConfig.setMaximumPoolSize(maximumPoolSize);
            this.dataSourceConfig.setIdleTimeout(0);
            this.dataSourceConfig.setUsername(username);
            this.dataSourceConfig.setPassword(password);
            this.dataSourceConfig.setJdbcUrl(driver + host + ":" + port + "/" + database + databaseProperties);
            this.dataSource = new HikariDataSource(this.dataSourceConfig);
            System.out.println("Connected to database!");
            return true;
        } catch (final Exception exception) {
            System.out.println("Error connecting to sql: "+exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }

    public boolean isClosed() {
        return this.dataSource == null || !this.dataSource.isRunning() || this.dataSource.isClosed();
    }
    public void disconnect() {
        if (this.isClosed()) {
            System.out.println("&cDatabase is already closed!");
        }
        this.dataSource.close();
        System.out.println("Successfully disconnected from the database.");
    }
}
