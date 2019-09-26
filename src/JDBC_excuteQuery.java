import java.sql.*;

/**
 * 测试查询语句  executeQuery()  返回ResultSet对象  利用迭代器遍历  输出查询结果
 *  最后结束时  断开数据库连接
 * @author 张浩
 * @date 2019.09.26
 */
public class JDBC_excuteQuery {
    public static void main(String[] args) {
        Connection connection=null;
        PreparedStatement pstatement=null;
        ResultSet set=null;
        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");

            //防止 SQL注入
            String sql="select regDate,username,ID from table_test where ID>?";

            pstatement=connection.prepareStatement(sql);
            //设置数据
            pstatement.setObject(1,5);
//            java.util.Date date=new java.util.Date(System.currentTimeMillis());
//            System.out.println(date.toString());
            //查询语句   返回一个ResultSet 对象
            set=pstatement.executeQuery();
            //类似于迭代器，输出查询的结果
            while (set.next()){
                System.out.println(set.getDate(1)+"---"+set.getString(2)+"---"+set.getInt(3));
            }
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
                if (pstatement!=null)
                    pstatement.close();
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
