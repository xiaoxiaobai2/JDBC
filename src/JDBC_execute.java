import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 利用Statement  执行Sql语句   可能发生SQL注入
 * @author 张浩
 * @date 2019.09.26
 */
public class JDBC_execute {
    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
            //SQL语句
            String sql="INSERT into table_test(username,number,regDate)VALUE ('张三','132456',NOW())";

            Statement statement=connection.createStatement();
            //可能会发生SQL注入问题     例如在后面加    or 1=1,将会删除所有内容
            statement.execute(sql);
        } catch (ClassNotFoundException e) {
            System.err.println("没找到类！");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
        }
    }
}
