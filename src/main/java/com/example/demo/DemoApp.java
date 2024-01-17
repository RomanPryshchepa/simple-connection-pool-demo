package com.example.demo;

import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource; // for test

public class DemoApp {
    @SneakyThrows
    public static void main(String[] args) {
        DataSource dataSource = initDb();
        var start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            try (var connection = dataSource.getConnection()) {
                try (var selectStatement = connection.createStatement()) {
                    selectStatement.executeQuery("select random()");
                }
            }
        }
        
        var end = System.nanoTime();
        System.out.println((end - start) / 1_000_000 + "ms");
    }

    private static DataSource initDb() {
        var dataSource = new CustomDataSource();
//        var dataSource = new PGSimpleDataSource(); // for test
//        var dataSource = new CustomPGSimpleDataSource(); // for test
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }
}
