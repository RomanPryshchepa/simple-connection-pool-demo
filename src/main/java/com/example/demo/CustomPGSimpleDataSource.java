package com.example.demo;

import java.sql.Connection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;


public class CustomPGSimpleDataSource extends PGSimpleDataSource {

  private Queue<CustomConnection> storage;

  private final int poolSize = 10;

  @SneakyThrows
  @Override
  public Connection getConnection() {
    if (storage == null) {
      storage = new LinkedBlockingQueue<>(poolSize);
      for (int i = 0; i < poolSize; i++) {
        storage.add(new CustomConnection(super.getConnection(), storage));
      }
    }
    return storage.poll();
  }

}
