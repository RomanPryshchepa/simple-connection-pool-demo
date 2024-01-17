package com.example.demo;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import javax.sql.DataSource;
import lombok.SneakyThrows;


public class CustomDataSource implements DataSource {

  private Queue<CustomConnection> storage;

  private final int poolSize = 5;

  private String url;
  private String user;
  private String password;

  @SneakyThrows
  @Override
  public Connection getConnection() {
    if (storage == null) {
      storage = new LinkedBlockingQueue<>(poolSize);
      for (int i = 0; i < poolSize; i++) {
        Connection realConnection = DriverManager.getConnection(url, user, password);
        storage.add(new CustomConnection(realConnection, storage));
      }
    }
    return storage.poll();
  }

  @Override
  public Connection getConnection(String username, String password) throws SQLException {
    return null;
  }

  @Override
  public PrintWriter getLogWriter() throws SQLException {
    return null;
  }

  @Override
  public void setLogWriter(PrintWriter out) throws SQLException {

  }

  @Override
  public void setLoginTimeout(int seconds) throws SQLException {

  }

  @Override
  public int getLoginTimeout() throws SQLException {
    return 0;
  }

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return false;
  }
}
