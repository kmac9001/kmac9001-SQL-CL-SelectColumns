
import Util.ConnectionUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterColumnsTest {
    FilterColumns filterColumns = new FilterColumns();

    /**
     * This test compares the result of the problem1 method to the hardcoded values below which ensures that only the
     * firstname column is retrieved.
     */
    @Test
    public void problem1Test(){
        //arrange
        User user1 = new User(0, "Steve", null);
        User user2 = new User(0,"Alexa", null);
        User user3 = new User(0,"Steve",null);
        User user4 = new User(0, "Brandon", null);
        User user5 = new User(0,"Adam",null);
        List<User> expectedResult = new ArrayList<>();
        expectedResult.add(user1);
        expectedResult.add(user2);
        expectedResult.add(user3);
        expectedResult.add(user4);
        expectedResult.add(user5);

        //act
        List<User> actualResult = filterColumns.problem1();

        //assert
        Assert.assertEquals(expectedResult, actualResult);
    }

    /**
     * The @Before annotation runs before every test so that way we create the tables required prior to running the test
     */
    @Before
    public void beforeTest(){

        try {

            Connection connection = ConnectionUtil.getConnection();

            //Write SQL logic here
            String sql1 = "CREATE TABLE site_user (id SERIAL PRIMARY KEY, firstname varchar(100), lastname varchar(100));";
            String sql2 = "INSERT INTO site_user (firstname, lastname) VALUES ('Steve', 'Garcia');";
            String sql3 = "INSERT INTO site_user (firstname, lastname) VALUES ('Alexa', 'Smith');";
            String sql4 = "INSERT INTO site_user (firstname, lastname) VALUES ('Steve', 'Jones');";
            String sql5 = "INSERT INTO site_user (firstname, lastname) VALUES ('Brandon', 'Smith');";
            String sql6 = "INSERT INTO site_user (firstname, lastname) VALUES ('Adam', 'Jones');";

            PreparedStatement ps = connection.prepareStatement(sql1 + sql2 + sql3 + sql4 + sql5 + sql6);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("failed creating table");
            e.printStackTrace();
        }
    }

    /**
     * The @After annotation runs after every test so that way we drop the tables to avoid conflicts in future tests
     */
    @After
    public void cleanup(){

        try {

            Connection connection = ConnectionUtil.getConnection();

            String sql = "DROP TABLE site_user;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("dropping table");
        }
    }
}
