import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *  字符串时间转化为long
 *    查询符合条件的  信息
 * @author 张浩
 * @date 2019.09.27
 */
public class JDBC_Date {
    private static long strToDate(String string) throws ParseException {
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
        long s=format.parse(string).getTime();//String转Date时间戳
        String date=format.format(s);//时间戳转String
        return s;
    }
    public static void main(String[] args) throws ParseException {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet set=null;
        
        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
            //关闭自动提交
            connection.setAutoCommit(false);
            ps=connection.prepareStatement("SELECT * FROM table_test where regDate>? and regDate < ?");
            long start =strToDate("2019-09-17 00:00:00:0");
            long end =strToDate("2019-09-20 00:00:00:0");
            System.out.println(new Timestamp(start).toString());
            ps.setTimestamp(1,new Timestamp(start));
            ps.setTimestamp(2,new Timestamp(end));
            set=ps.executeQuery();
            while (set.next()){
                System.out.println(set.getInt("ID")+"--"+set.getString("username")+"--"+set.getTimestamp("regDate"));
            }
            //手动提交
            connection.commit();
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
                if (ps!=null)
                    ps.close();
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
