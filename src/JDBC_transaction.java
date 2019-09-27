import java.sql.*;

/**
 *  事物：一组要么同事成功，要么同时失败的SQL语句，是数据库操作的一个单元
 *         关闭自动提交
 *         当发生错误时进行回滚，connection.rollback
 * @author 张浩
 * @date 2019.09.27
 */
public class JDBC_transaction {
    public static void main(String[] args) {
        Connection connection=null;
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
        ResultSet set=null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
            //关闭自动提交
            connection.setAutoCommit(false);

            ps=connection.prepareStatement("INSERT into table_test(username,number,regDate)VALUE ('张三','132456',NOW())");
            ps.executeUpdate();

            ps2=connection.prepareStatement("INSERT into table_test(username,number,regDate)VALUE (,'132456',NOW())");
            ps2.executeUpdate();
            //手动提交
            connection.commit();
        } catch (ClassNotFoundException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("没找到类！");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.err.println("数据库连接失败！");
        }finally {
            /**
             * 别忘了断开连接    后开的先关
             */
            try {
                if (set!=null)
                    set.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps!=null)
                    ps.close();
                if (ps2!=null)
                    ps2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!=null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
