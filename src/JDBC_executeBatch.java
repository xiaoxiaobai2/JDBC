import java.sql.*;

/**
 * 测试添加大量数据
 *      1、关闭自动提交  connection.setAutoCommit(false)
 *      2、添加数据语句   statement.addBatch(sql)
 *      3、执行数据语句  statement.executeBatch()
 *      4、手动提交      connection.commit()
 * @author 张浩
 * @date 2019.09.26
 */
public class JDBC_executeBatch {
    public static void main(String[] args) {
        Connection connection=null;
        Statement statement=null;
        ResultSet set=null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
            //关闭自动提交
            connection.setAutoCommit(false);

            long start=System.currentTimeMillis();
            statement=connection.createStatement();
            for (int i = 0; i < 200000; i++) {
                statement.addBatch("INSERT into table_test(username,number,regDate)VALUE ('张三"+i+"','132456',NOW())");
            }
            //执行大量数据 插入
            statement.executeBatch();
            //手动提交
            connection.commit();
            long end=System.currentTimeMillis();
            System.out.println("耗时"+(end-start)+"ms");
        } catch (ClassNotFoundException e) {
            System.err.println("没找到类！");
        } catch (SQLException e) {
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
                if (statement!=null)
                    statement.close();
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
