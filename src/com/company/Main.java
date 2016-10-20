package com.company;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.h2.tools.Server;
import spark.Session;
import spark.Spark;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./web");
        createTables(conn);
        Spark.externalStaticFileLocation("public");
        Spark.init();
        int rng = (int) Math.ceil(Math.random() * 100);
        System.out.println(rng);

        ArrayList<String> words = selectAllWords(conn);

        if (words.size() == 0) {
            fileImport(conn);
        }

        Spark.get(
                "/user",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("username");
                    User user = selectUser(conn, name);
                    JsonSerializer serializer = new JsonSerializer();
                    return serializer.serialize(user);
                }
        );

        Spark.post(
                "/login",
                (request, response) -> {
                    String body = request.body();
                    JsonParser parser = new JsonParser();
                    HashMap<String, String> theUser = parser.parse(body);
                    String name = theUser.get("name");
                    String pass = theUser.get("password");
                    User user = selectUser(conn, name);
                    if (user == null) {
                        insertUser(conn, name, pass);
                    }
                    else if (!user.password.equals(pass)) {
                        Spark.halt(403);
                    }
                    return "";
                }
        );

//        Spark.get(
//                "/character",
//                (request, response) -> {
//
//                }
//        );
    }

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, name VARCHAR, password VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS characters (id IDENTITY, str INT, per INT, end INT, cha INT, intel INT, agi INT, luck INT, desc VARCHAR, user_id INT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS first_word (id IDENTITY, first VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS second_word (id IDENTITY, second VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS third_word (id IDENTITY, third VARCHAR)");
    }

    public static ArrayList<String> selectAllWords(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM first_word");
        ArrayList<String> words = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            String first = results.getString("first");
            words.add(first);
        }
        PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM second_word");
        ResultSet results1 = stmt1.executeQuery();
        while (results1.next()) {
            String second = results1.getString("second");
            words.add(second);
        }
        PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM third_word");
        ResultSet results2 = stmt2.executeQuery();
        while (results2.next()) {
            String third = results2.getString("third");
            words.add(third);
        }
        return words;
    }

    public static User selectUser(Connection conn, String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
        stmt.setString(1, name);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            int id = results.getInt("id");
            String password = results.getString("password");
            return new User(id, name, password);
        }
        return null;
    }

    public static void insertUser(Connection conn, String name, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES (NULL, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.execute();
    }

    public static void updateUser(Connection conn, String name, String pass, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE users SET name = ?, password = ? WHERE id = ?");
        stmt.setString(1, name);
        stmt.setString(2, pass);
        stmt.setInt(3, id);
        stmt.execute();
    }

    public static void deleteUser(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static String selectFirst(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM first_word WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String first = results.getString("first");
            return first;
        }
        return null;
    }

    public static ArrayList<String> selectAllFirst(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM first_word");
        ArrayList<String> firsts = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            String first = results.getString("first");
            firsts.add(first);
        }
        return firsts;
    }

    public static void insertFirst(Connection conn, String first) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO first_word VALUES (NULL, ?)");
        stmt.setString(1, first);
        stmt.execute();
    }

    public static void updateFirst(Connection conn, String first) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE first_word SET first = ?");
        stmt.setString(1, first);
        stmt.execute();
    }

    public static void deleteFirst(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM first_word WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static String selectSecond(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM second_word WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String second = results.getString("second");
            return second;
        }
        return null;
    }

    public static ArrayList<String> selectAllSecond(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM second_word");
        ArrayList<String> seconds = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            String second = results.getString("second");
            seconds.add(second);
        }
        return seconds;
    }

    public static void insertSecond(Connection conn, String second) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO second_word VALUES (NULL, ?)");
        stmt.setString(1, second);
        stmt.execute();
    }

    public static void updateSecond(Connection conn, String second) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE second_word SET second = ?");
        stmt.setString(1, second);
        stmt.execute();
    }

    public static void deleteSecond(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM second_word WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static String selectThird(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM third_word WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String third = results.getString("third");
            return third;
        }
        return null;
    }

    public static ArrayList<String> selectAllThird(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareCall("SELECT * FROM third_word");
        ArrayList<String> thirds = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            String third = results.getString("third");
            thirds.add(third);
        }
        return thirds;
    }

    public static void insertThird(Connection conn, String third) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO third_word VALUES (NULL, ?)");
        stmt.setString(1, third);
        stmt.execute();
    }

    public static void updateThird(Connection conn, String third) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE third_word SET third = ?");
        stmt.setString(1, third);
        stmt.execute();
    }

    public static void deleteThird(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM third_word WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void insertCharacter(Connection conn, int str, int per, int end, int cha, int intel, int agi, int luck, String desc, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO characters VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, str);
        stmt.setInt(2, per);
        stmt.setInt(3, end);
        stmt.setInt(4, cha);
        stmt.setInt(5, intel);
        stmt.setInt(6, agi);
        stmt.setInt(7, luck);
        stmt.setString(8, desc);
        stmt.setInt(9, id);
        stmt.execute();
    }

    public static ArrayList<Character> selectCharacters(Connection conn, int uID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM characters INNER JOIN users ON characters.user_id = users.id WHERE characters.user_id = ?");
        stmt.setInt(1, uID);
        ArrayList<Character> characters = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            int str = results.getInt("str");
            int per = results.getInt("per");
            int end = results.getInt("end");
            int cha = results.getInt("cha");
            int intel = results.getInt("intel");
            int agi = results.getInt("agi");
            int luck = results.getInt("luck");
            String desc = results.getString("desc");
            Character character = new Character(id, str, per, end, cha, intel, agi, luck, desc);
            characters.add(character);
        }
        return characters;
    }

    public static Character selectOneCharacter(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM characters WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            int str = results.getInt("str");
            int per = results.getInt("per");
            int end = results.getInt("end");
            int cha = results.getInt("cha");
            int intel = results.getInt("intel");
            int agi = results.getInt("agi");
            int luck = results.getInt("luck");
            String desc = results.getString("desc");
            return new Character(id, str, per, end, cha, intel, agi, luck, desc);
        }
        return null;
    }

    public static void deleteCharacter(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM characters WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void fileImport(Connection conn) throws FileNotFoundException, SQLException {
        File f = new File("firstwords.txt");
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            String[] columns = line.split("\\|");
            String first = columns[0];
            String second = columns[1];
            String third = columns[2];
            insertFirst(conn, first);
            insertSecond(conn, second);
            insertThird(conn, third);
        }
    }
}
