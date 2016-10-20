package com.company;

import org.junit.Test;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Created by michaelplott on 10/19/16.
 */
public class MainTest {

    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Main.createTables(conn);
        return conn;
    }

    public ArrayList<User> selectAllUsers() throws SQLException {
        Connection conn = startConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
        ArrayList<User> users = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String pass = results.getString("password");
            users.add(new User(id, name, pass));
        }
        return users;
    }

    @Test
    public void testInsertAndSelectUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        conn.close();
        assertTrue(user != null);
    }

    @Test
    public void testUpdateUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        Main.updateUser(conn, "NotMike", "321", user.id);
        User user1 = Main.selectUser(conn, "NotMike");
        conn.close();
        assertTrue(user1.name.equals("NotMike") && user1.password.equals("321"));
    }

    @Test
    public void testDeleteUser() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        Main.insertUser(conn, "Sam", "4321");
        User user = Main.selectUser(conn, "Mike");
        Main.deleteUser(conn, user.id);
        ArrayList<User> users = selectAllUsers();
        conn.close();
        assertTrue(users.size() == 1);
    }

    @Test
    public void testInsertWords() throws SQLException {
        Connection conn = startConnection();
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.insertFirst(conn, "purple");
        Main.insertFirst(conn, "yellow");
        Main.insertFirst(conn, "orange");
        Main.insertSecond(conn, "monday");
        Main.insertSecond(conn, "tuesday");
        Main.insertSecond(conn, "wednesday");
        Main.insertSecond(conn, "thursday");
        Main.insertSecond(conn, "friday");
        Main.insertThird(conn, "jdalfjf");
        Main.insertThird(conn, "ldkasjfkjlasf");
        Main.insertThird(conn, "lajsdfjasjdflkasfljksdalkfj");
        Main.insertThird(conn, "aa");
        Main.insertThird(conn, "bbbkasdf");
        ArrayList<String> firstWords = Main.selectAllFirst(conn);
        ArrayList<String> secondWords = Main.selectAllSecond(conn);
        ArrayList<String> thirdWords = Main.selectAllThird(conn);
        conn.close();
        assertTrue(firstWords.size() == 5);
        assertTrue(secondWords.size() == 5);
        assertTrue(thirdWords.size() == 5);
    }

    @Test
    public void selectRandomWords() throws SQLException {
        Connection conn = startConnection();
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.insertFirst(conn, "purple");
        Main.insertFirst(conn, "yellow");
        Main.insertFirst(conn, "orange");
        Main.insertSecond(conn, "monday");
        Main.insertSecond(conn, "tuesday");
        Main.insertSecond(conn, "wednesday");
        Main.insertSecond(conn, "thursday");
        Main.insertSecond(conn, "friday");
        Main.insertThird(conn, "jdalfjf");
        Main.insertThird(conn, "ldkasjfkjlasf");
        Main.insertThird(conn, "lajsdfjasjdflkasfljksdalkfj");
        Main.insertThird(conn, "aa");
        Main.insertThird(conn, "bbbkasdf");
        int ranFirst = (int) Math.ceil(Math.random() * 5);
        int ranSecond = (int) Math.ceil(Math.random() * 5);
        int ranThird = (int) Math.ceil(Math.random() * 5);
        String first = Main.selectFirst(conn, ranFirst);
        String second = Main.selectSecond(conn, ranSecond);
        String third = Main.selectThird(conn, ranThird);
        String desc = first + " " + second + " " + third;
        conn.close();
        assertTrue(!desc.isEmpty());
    }

    @Test
    public void testInsertAndSelectCharacter() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.insertFirst(conn, "purple");
        Main.insertFirst(conn, "yellow");
        Main.insertFirst(conn, "orange");
        Main.insertSecond(conn, "monday");
        Main.insertSecond(conn, "tuesday");
        Main.insertSecond(conn, "wednesday");
        Main.insertSecond(conn, "thursday");
        Main.insertSecond(conn, "friday");
        Main.insertThird(conn, "jdalfjf");
        Main.insertThird(conn, "ldkasjfkjlasf");
        Main.insertThird(conn, "lajsdfjasjdflkasfljksdalkfj");
        Main.insertThird(conn, "aa");
        Main.insertThird(conn, "bbbkasdf");
        int ranFirst = (int) Math.ceil(Math.random() * 5);
        int ranSecond = (int) Math.ceil(Math.random() * 5);
        int ranThird = (int) Math.ceil(Math.random() * 5);
        int ranStr = (int) Math.ceil(Math.random() * 10);
        int ranPer = (int) Math.ceil(Math.random() * 10);
        int ranEnd = (int) Math.ceil(Math.random() * 10);
        int ranCha = (int) Math.ceil(Math.random() * 10);
        int ranIntel = (int) Math.ceil(Math.random() * 10);
        int ranAgi = (int) Math.ceil(Math.random() * 10);
        int ranLuck = (int) Math.ceil(Math.random() * 10);
        String first = Main.selectFirst(conn, ranFirst);
        String second = Main.selectSecond(conn, ranSecond);
        String third = Main.selectThird(conn, ranThird);
        String desc = first + " " + second + " " + third;
        Main.insertCharacter(conn, ranStr, ranPer, ranEnd, ranCha, ranIntel, ranAgi, ranLuck, desc, user.id);
        FalloutCharacter falloutCharacter = Main.selectOneCharacter(conn, 1);
        conn.close();
        assertTrue(falloutCharacter != null);
    }

    @Test
    public void testDeleteCharacter() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.insertFirst(conn, "purple");
        Main.insertFirst(conn, "yellow");
        Main.insertFirst(conn, "orange");
        Main.insertSecond(conn, "monday");
        Main.insertSecond(conn, "tuesday");
        Main.insertSecond(conn, "wednesday");
        Main.insertSecond(conn, "thursday");
        Main.insertSecond(conn, "friday");
        Main.insertThird(conn, "jdalfjf");
        Main.insertThird(conn, "ldkasjfkjlasf");
        Main.insertThird(conn, "lajsdfjasjdflkasfljksdalkfj");
        Main.insertThird(conn, "aa");
        Main.insertThird(conn, "bbbkasdf");
        int ranFirst = (int) Math.ceil(Math.random() * 5);
        int ranSecond = (int) Math.ceil(Math.random() * 5);
        int ranThird = (int) Math.ceil(Math.random() * 5);
        int ranStr = (int) Math.ceil(Math.random() * 10);
        int ranPer = (int) Math.ceil(Math.random() * 10);
        int ranEnd = (int) Math.ceil(Math.random() * 10);
        int ranCha = (int) Math.ceil(Math.random() * 10);
        int ranIntel = (int) Math.ceil(Math.random() * 10);
        int ranAgi = (int) Math.ceil(Math.random() * 10);
        int ranLuck = (int) Math.ceil(Math.random() * 10);
        String first = Main.selectFirst(conn, ranFirst);
        String second = Main.selectSecond(conn, ranSecond);
        String third = Main.selectThird(conn, ranThird);
        String desc = first + " " + second + " " + third;
        Main.insertCharacter(conn, ranStr, ranPer, ranEnd, ranCha, ranIntel, ranAgi, ranLuck, desc, user.id);
        FalloutCharacter falloutCharacter = Main.selectOneCharacter(conn, 1);
        Main.deleteCharacter(conn, 1);
        FalloutCharacter falloutCharacter1 = Main.selectOneCharacter(conn, 1);
        conn.close();
        assertTrue(falloutCharacter1 == null);
    }

    @Test
    public void testSelectAllCharacters() throws SQLException {
        Connection conn = startConnection();
        Main.insertUser(conn, "Mike", "123");
        User user = Main.selectUser(conn, "Mike");
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.insertFirst(conn, "purple");
        Main.insertFirst(conn, "yellow");
        Main.insertFirst(conn, "orange");
        Main.insertSecond(conn, "monday");
        Main.insertSecond(conn, "tuesday");
        Main.insertSecond(conn, "wednesday");
        Main.insertSecond(conn, "thursday");
        Main.insertSecond(conn, "friday");
        Main.insertThird(conn, "jdalfjf");
        Main.insertThird(conn, "ldkasjfkjlasf");
        Main.insertThird(conn, "lajsdfjasjdflkasfljksdalkfj");
        Main.insertThird(conn, "aa");
        Main.insertThird(conn, "bbbkasdf");
        int ranFirst = (int) Math.ceil(Math.random() * 5);
        int ranSecond = (int) Math.ceil(Math.random() * 5);
        int ranThird = (int) Math.ceil(Math.random() * 5);
        int ranStr = (int) Math.ceil(Math.random() * 10);
        int ranPer = (int) Math.ceil(Math.random() * 10);
        int ranEnd = (int) Math.ceil(Math.random() * 10);
        int ranCha = (int) Math.ceil(Math.random() * 10);
        int ranIntel = (int) Math.ceil(Math.random() * 10);
        int ranAgi = (int) Math.ceil(Math.random() * 10);
        int ranLuck = (int) Math.ceil(Math.random() * 10);
        int ranFirst1 = (int) Math.ceil(Math.random() * 5);
        int ranSecond1 = (int) Math.ceil(Math.random() * 5);
        int ranThird1 = (int) Math.ceil(Math.random() * 5);
        int ranStr1 = (int) Math.ceil(Math.random() * 10);
        int ranPer1 = (int) Math.ceil(Math.random() * 10);
        int ranEnd1 = (int) Math.ceil(Math.random() * 10);
        int ranCha1 = (int) Math.ceil(Math.random() * 10);
        int ranIntel1 = (int) Math.ceil(Math.random() * 10);
        int ranAgi1 = (int) Math.ceil(Math.random() * 10);
        int ranLuck1 = (int) Math.ceil(Math.random() * 10);
        String first = Main.selectFirst(conn, ranFirst);
        String second = Main.selectSecond(conn, ranSecond);
        String third = Main.selectThird(conn, ranThird);
        String first1 = Main.selectFirst(conn, ranFirst1);
        String second1 = Main.selectSecond(conn, ranSecond1);
        String third1 = Main.selectThird(conn, ranThird1);
        String desc = first + " " + second + " " + third;
        String desc1 = first1 + " " + second1 + " " + third1;
        Main.insertCharacter(conn, ranStr, ranPer, ranEnd, ranCha, ranIntel, ranAgi, ranLuck, desc, user.id);
        Main.insertCharacter(conn, ranStr1, ranPer1, ranEnd1, ranCha1, ranIntel1, ranAgi1, ranLuck1, desc1, user.id);
        FalloutCharacter falloutCharacter = Main.selectOneCharacter(conn, 1);
        FalloutCharacter falloutCharacter1 = Main.selectOneCharacter(conn, 2);
        System.out.println(falloutCharacter);
        System.out.println(falloutCharacter1);
        ArrayList<FalloutCharacter> falloutCharacters = Main.selectCharacters(conn, user.id);
        conn.close();
        assertTrue(falloutCharacters.size() == 2);
    }

    @Test
    public void testSelectEveryWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.insertFirst(conn, "purple");
        Main.insertFirst(conn, "yellow");
        Main.insertFirst(conn, "orange");
        Main.insertSecond(conn, "monday");
        Main.insertSecond(conn, "tuesday");
        Main.insertSecond(conn, "wednesday");
        Main.insertSecond(conn, "thursday");
        Main.insertSecond(conn, "friday");
        Main.insertThird(conn, "jdalfjf");
        Main.insertThird(conn, "ldkasjfkjlasf");
        Main.insertThird(conn, "lajsdfjasjdflkasfljksdalkfj");
        Main.insertThird(conn, "aa");
        Main.insertThird(conn, "bbbkasdf");
        ArrayList<String> words = Main.selectAllWords(conn);
        conn.close();
        assertTrue(words.size() == 15);
    }

    @Test
    public void testUpdateFirstWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertFirst(conn, "blue");
        Main.updateFirst(conn, "red");
        String first = Main.selectFirst(conn, 1);
        conn.close();
        assertTrue(first.equals("red"));
    }

    @Test
    public void testDeleteFirstWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertFirst(conn, "blue");
        Main.insertFirst(conn, "red");
        Main.deleteFirst(conn, 2);
        ArrayList<String> words = Main.selectAllFirst(conn);
        conn.close();
        assertTrue(words.size() == 1);
    }

    @Test
    public void testUpdateSecondWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertSecond(conn, "blue");
        Main.updateSecond(conn, "red");
        String second = Main.selectSecond(conn, 1);
        conn.close();
        assertTrue(second.equals("red"));
    }

    @Test
    public void testDeleteSecondWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertSecond(conn, "blue");
        Main.insertSecond(conn, "red");
        Main.deleteSecond(conn, 2);
        ArrayList<String> words = Main.selectAllSecond(conn);
        conn.close();
        assertTrue(words.size() == 1);
    }

    @Test
    public void testUpdateThirdWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertThird(conn, "blue");
        Main.updateThird(conn, "red");
        String third = Main.selectThird(conn, 1);
        conn.close();
        assertTrue(third.equals("red"));
    }

    @Test
    public void testDeleteThirdWord() throws SQLException {
        Connection conn = startConnection();
        Main.insertThird(conn, "blue");
        Main.insertThird(conn, "red");
        Main.deleteThird(conn, 2);
        ArrayList<String> words = Main.selectAllThird(conn);
        conn.close();
        assertTrue(words.size() == 1);
    }

    @Test
    public void testSelectSkyrimCharacters() throws SQLException, FileNotFoundException {
        Connection conn = startConnection();
        Main.skyrimImport(conn);
        ArrayList<SkyrimCharacter> skyrimCharacters = Main.selectAllSkyrimCharacters(conn);
        conn.close();
        assertTrue(skyrimCharacters.size() == 10);
    }

    @Test
    public void testSelectOneSkyrimCharacter() throws FileNotFoundException, SQLException {
        Connection conn = startConnection();
        Main.skyrimImport(conn);
        int randomSC = (int) Math.ceil(Math.random() * 10);
        SkyrimCharacter sc = Main.selectASkyrimCharacter(conn, randomSC);
        sc.health = (int) Math.ceil(Math.random() * 200);
        if (sc.health < 100) {
            sc.health = 100;
        }
        sc.magicka = (int) Math.ceil(Math.random() * 200);
        if (sc.magicka < 100) {
            sc.magicka = 100;
        }
        sc.stamina = (int) Math.ceil(Math.random() * 200);
        if (sc.stamina < 100) {
            sc.stamina = 100;
        }
        System.out.println(sc);
        conn.close();
        assertTrue(sc != null);
    }

    @Test
    public void testSelectFalloutStory() throws SQLException, FileNotFoundException {
        Connection conn = startConnection();
        Main.fileImportFalloutStory(conn);
        int ranSto = (int) Math.ceil(Math.random() * 6);
        String story = Main.selectAFalloutStory(conn, ranSto);
        System.out.println(story);
        conn.close();
        assertTrue(story != null);
    }

    @Test
    public void testSelectAllFalloutStories() throws SQLException, FileNotFoundException {
        Connection conn = startConnection();
        Main.fileImportFalloutStory(conn);
        ArrayList<String> stories = Main.selectAllFalloutStories(conn);
        conn.close();
        assertTrue(stories.size() == 6);
    }

}