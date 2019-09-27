import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class JDBC_clob {
    public static void main(String[] args) throws ParseException {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet set=null;

        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
            //存储大量的文本数据 CLOB
//            ps=connection.prepareStatement("insert into table_test (username,number) values (?,?)");
//            ps.setClob(1,new BufferedReader(new InputStreamReader(new FileInputStream("c:/123.txt"),"gbk")));
//            ps.setObject(2,45123);
//            ps.setObject(2,123);

            //读取clob
            ps=connection.prepareStatement("select * from table_test where ID=403021");
            set=ps.executeQuery();
            while (set.next()){
                Clob clob=set.getClob("username");
                Reader reader=clob.getCharacterStream();
                int car=0;
                while (-1!=(car=reader.read())){
                    System.out.print((char)car);
                }
            }
            //手动提交
//            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            System.err.println("没找到类！");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
