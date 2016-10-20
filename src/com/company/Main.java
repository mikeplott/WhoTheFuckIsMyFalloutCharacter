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
        ArrayList<SkyrimCharacter> skyrimCharacters = selectAllSkyrimCharacters(conn);
        ArrayList<String> falloutStories = selectAllFalloutStories(conn);

        if (words.size() == 0) {
            fileImport(conn);
        }

        if (skyrimCharacters.size() == 0) {
            skyrimImport(conn);
        }

        if (falloutStories.size() == 0) {
            fileImportFalloutStory(conn);
        }

        Spark.get(
                "/skyrim",
                (request, response) -> {
                    int id = (int)Math.ceil(Math.random() * 10);
                    SkyrimCharacter sc = selectASkyrimCharacter(conn, id);
                    sc.health = (int) Math.ceil(Math.random() * 200);
                    sc.magicka = (int) Math.ceil(Math.random() * 200);
                    sc.stamina = (int) Math.ceil(Math.random() * 200);
                    if (sc.health < 100) {
                        sc.health = 100;
                    }
                    if (sc.magicka < 100) {
                        sc.magicka = 100;
                    }
                    if (sc.stamina < 100) {
                        sc.stamina = 100;
                    }

                    JsonSerializer serializer = new JsonSerializer();
                    return serializer.serialize(sc);
                }
        );

        Spark.get(
                "/fallout",
                (request, response) -> {
                    int ranStr = (int) Math.ceil(Math.random() * 10);
                    int ranPer = (int) Math.ceil(Math.random() * 10);
                    int ranEnd = (int) Math.ceil(Math.random() * 10);
                    int ranCha = (int) Math.ceil(Math.random() * 10);
                    int ranIntel = (int) Math.ceil(Math.random() * 10);
                    int ranAgi = (int) Math.ceil(Math.random() * 10);
                    int ranLuck = (int) Math.ceil(Math.random() * 10);
                    int ranFirst = (int) Math.ceil(Math.random() * 5);
                    int ranSecond = (int) Math.ceil(Math.random() * 5);
                    int ranThird = (int) Math.ceil(Math.random() * 5);
                    String first = selectFirst(conn, ranFirst);
                    String second = selectSecond(conn, ranSecond);
                    String third = selectThird(conn, ranThird);
                    String desc = first + " " + second + " " + third;
                    FalloutCharacter fc = new FalloutCharacter(ranStr, ranPer, ranEnd, ranCha, ranIntel, ranAgi, ranLuck, desc);
                    JsonSerializer serializer = new JsonSerializer();
                    return serializer.serialize(fc);

                }
        );

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
                    } else if (!user.password.equals(pass)) {
                        Spark.halt(403);
                        return null;
                    }
                    return "";
                }
        );
    }

    public static void createTables(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY, name VARCHAR, password VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS fallout_characters (id IDENTITY, str INT, per INT, end INT, cha INT, intel INT, agi INT, luck INT, desc VARCHAR, user_id INT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS first_word (id IDENTITY, first VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS second_word (id IDENTITY, second VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS third_word (id IDENTITY, third VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS skyrim_characters (id IDENTITY, race VARCHAR, smithing INT, heavy_armor INT, block INT, two_handed INT, one_handed INT, archery INT, light_armor INT, " +
                "sneak INT, lockpicking INT, pickpocket INT, speech INT, alchemy INT, illusion INT, conjuration INT, destruction INT, restoration INT, alteration INT, enchanting INT)");
        stmt.execute("CREATE TABLE IF NOT EXISTS fallout_stories (id IDENTITY, story VARCHAR)");
        stmt.execute("CREATE TABLE IF NOT EXISTS skyrim_stories (id IDENTITY, story VARCHAR)");
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
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fallout_characters VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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

    public static ArrayList<FalloutCharacter> selectCharacters(Connection conn, int uID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fallout_characters INNER JOIN users ON fallout_characters.user_id = users.id WHERE fallout_characters.user_id = ?");
        stmt.setInt(1, uID);
        ArrayList<FalloutCharacter> falloutCharacters = new ArrayList<>();
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
            FalloutCharacter falloutCharacter = new FalloutCharacter(id, str, per, end, cha, intel, agi, luck, desc);
            falloutCharacters.add(falloutCharacter);
        }
        return falloutCharacters;
    }

    public static FalloutCharacter selectOneCharacter(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fallout_characters WHERE id = ?");
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
            return new FalloutCharacter(id, str, per, end, cha, intel, agi, luck, desc);
        }
        return null;
    }

    public static void deleteCharacter(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM fallout_characters WHERE id = ?");
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

    public static void skyrimImport(Connection conn) throws FileNotFoundException, SQLException {
        File f = new File("skyrimcharacter.csv");
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            String[] columns = line.split(",");
            String race = columns[0];
            int smithing = Integer.parseInt(columns[1]);
            int heavyArmor = Integer.parseInt(columns[2]);
            int block = Integer.parseInt(columns[3]);
            int twoHanded = Integer.parseInt(columns[4]);
            int oneHanded = Integer.parseInt(columns[5]);
            int archery = Integer.parseInt(columns[6]);
            int lightArmor = Integer.parseInt(columns[7]);
            int sneak = Integer.parseInt(columns[8]);
            int lockpicking = Integer.parseInt(columns[9]);
            int pickpocket = Integer.parseInt(columns[10]);
            int speech = Integer.parseInt(columns[11]);
            int alchemy = Integer.parseInt(columns[12]);
            int illusion = Integer.parseInt(columns[13]);
            int conjuration = Integer.parseInt(columns[14]);
            int destruction = Integer.parseInt(columns[15]);
            int restoration = Integer.parseInt(columns[16]);
            int alteration = Integer.parseInt(columns[17]);
            int enchanting = Integer.parseInt(columns[18]);
            insertSkryimCharacter(conn, race, smithing, heavyArmor, block, twoHanded, oneHanded, archery, lightArmor, sneak, lockpicking, pickpocket, speech, alchemy, illusion,
                    conjuration, destruction, restoration, alteration, enchanting);
        }
    }

    public static void insertSkryimCharacter(Connection conn, String race, int smithing, int heavyArmor, int block, int twoHanded, int oneHanded, int archery, int lightArmor, int sneak,
                                             int lockpicking, int pickpocket, int speech, int alchemy, int illusion, int conjuration, int destruction, int restoration, int alteration,
                                             int enchanting) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO skyrim_characters VALUES(NULL, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, race);
        stmt.setInt(2, smithing);
        stmt.setInt(3, heavyArmor);
        stmt.setInt(4, block);
        stmt.setInt(5, twoHanded);
        stmt.setInt(6, oneHanded);
        stmt.setInt(7, archery);
        stmt.setInt(8, lightArmor);
        stmt.setInt(9, sneak);
        stmt.setInt(10, lockpicking);
        stmt.setInt(11, pickpocket);
        stmt.setInt(12, speech);
        stmt.setInt(13, alchemy);
        stmt.setInt(14, illusion);
        stmt.setInt(15, conjuration);
        stmt.setInt(16, destruction);
        stmt.setInt(17, restoration);
        stmt.setInt(18, alteration);
        stmt.setInt(19, enchanting);
        stmt.execute();
    }

    public static ArrayList<SkyrimCharacter>selectAllSkyrimCharacters(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM skyrim_characters");
        ArrayList<SkyrimCharacter> skyrimCharacters = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String race = results.getString("race");
            int smithing = results.getInt("smithing");
            int heavyArmor = results.getInt("heavy_armor");
            int block = results.getInt("block");
            int twoHanded = results.getInt("two_handed");
            int oneHanded = results.getInt("one_handed");
            int archery = results.getInt("archery");
            int lightArmor = results.getInt("light_armor");
            int sneak = results.getInt("sneak");
            int lockpicking = results.getInt("lockpicking");
            int pickpocket = results.getInt("pickpocket");
            int speech = results.getInt("speech");
            int alchemy = results.getInt("alchemy");
            int illusion = results.getInt("illusion");
            int conjuration = results.getInt("conjuration");
            int destruction = results.getInt("destruction");
            int restoration = results.getInt("restoration");
            int alteration = results.getInt("alteration");
            int enchanting = results.getInt("enchanting");
            skyrimCharacters.add(new SkyrimCharacter(id, race, smithing, heavyArmor, block, twoHanded, oneHanded, archery, lightArmor, sneak,
                    lockpicking, pickpocket, speech, alchemy, illusion, conjuration, destruction, restoration, alteration, enchanting));
        }
        return skyrimCharacters;
    }

    public static SkyrimCharacter selectASkyrimCharacter(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM skyrim_characters WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if (results.next()) {
            String race = results.getString("race");
            int smithing = results.getInt("smithing");
            int heavyArmor = results.getInt("heavy_armor");
            int block = results.getInt("block");
            int twoHanded = results.getInt("two_handed");
            int oneHanded = results.getInt("one_handed");
            int archery = results.getInt("archery");
            int lightArmor = results.getInt("light_armor");
            int sneak = results.getInt("sneak");
            int lockpicking = results.getInt("lockpicking");
            int pickpocket = results.getInt("pickpocket");
            int speech = results.getInt("speech");
            int alchemy = results.getInt("alchemy");
            int illusion = results.getInt("illusion");
            int conjuration = results.getInt("conjuration");
            int destruction = results.getInt("destruction");
            int restoration = results.getInt("restoration");
            int alteration = results.getInt("alteration");
            int enchanting = results.getInt("enchanting");
            return new SkyrimCharacter(id, race, smithing, heavyArmor, block, twoHanded, oneHanded, archery, lightArmor, sneak, lockpicking, pickpocket,
                    speech, alchemy, illusion, conjuration, destruction, restoration, alteration, enchanting);
        }
        return null;
    }

    public static void insertFalloutStory(Connection conn, String story) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO fallout_stories VALUES (NULL, ?)");
        stmt.setString(1, story);
        stmt.execute();
    }

    public static ArrayList<String> selectAllFalloutStories(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM fallout_stories");
        ArrayList<String> stories = new ArrayList<>();
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String story = results.getString("story");
            stories.add(story);
        }
        return stories;
    }

    public static void fileImportFalloutStory(Connection conn) throws FileNotFoundException, SQLException {
        File f = new File("falloutstories.txt");
        Scanner fileScanner = new Scanner(f);
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            String[] columns = line.split("\\|");
            String story = columns[0];
            insertFalloutStory(conn, story);
        }
    }
}
