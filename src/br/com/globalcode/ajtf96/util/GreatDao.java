package br.com.globalcode.ajtf96.util;

public interface GreatDao {
    User getUser(int id);
    void saveUser(User u);
}