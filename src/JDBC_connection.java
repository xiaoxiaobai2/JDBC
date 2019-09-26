import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 建立数据库连接
 * @author 张浩
 * @date 2019.09.26
 */
public class JDBC_connection {
    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            long start=System.currentTimeMillis();
            //建立连接比较耗时（内部包含了Socket对象，是一个远程连接）
            //后期为了提高效率会用连接池来管理连接对象
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
            long end=System.currentTimeMillis();
            System.out.println(connection);
            System.out.println("耗时"+(end-start)+"ms");
        } catch (ClassNotFoundException e) {
            System.err.println("没找到类！");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
        }
    }
}
