import java.sql.*;

/**
 * prepareStatement 进行预处理，防止sql注入
 * @author 张浩
 * @date 2019.09.26
 */
public class JDBC_PrepareStatement {
    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");

            //防止 SQL注入
            String sql="INSERT into table_test(username,number,regDate)VALUE (?,?,?)";

            PreparedStatement pstatement=connection.prepareStatement(sql);
            //设置数据
            pstatement.setObject(1,"你");
            pstatement.setObject(2,"你");
            pstatement.setObject(2,"wo");
            pstatement.setObject(3,new java.util.Date(System.currentTimeMillis()));
//            java.util.Date date=new java.util.Date(System.currentTimeMillis());
//            System.out.println(date.toString());
            pstatement.execute();
        } catch (ClassNotFoundException e) {
            System.err.println("没找到类！");
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
        }
    }
}
