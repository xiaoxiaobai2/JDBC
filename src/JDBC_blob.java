import java.io.*;
import java.sql.*;

/**
 * blob用于存储大量的二进制数据，例如视频
 *      本例首先将一张图片存储进数据库的表中
 *      在讲图片读入并输出到新的文件
 * @author 张浩
 * @date 2019.10.10
 */
public class JDBC_blob {
    public static void main(String[] args) throws FileNotFoundException {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet set=null;
        InputStream is=null;
        OutputStream os=new FileOutputStream("C:\\\\Users\\\\张浩\\\\Desktop\\\\ok_副本.jpg");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/testsql","root","123456");
//            ps=connection.prepareStatement("insert into table_test (username,Myinfo,number) values (?,?,?)");
//            ps.setString(1,"老大");
//            ps.setBlob(2,new FileInputStream(new File("C:\\Users\\张浩\\Desktop\\ok.jpg")));
//            ps.setInt(3,1830922);
//            ps.executeUpdate();

            ps=connection.prepareStatement("select * from table_test where ID=?");
            ps.setInt(1,403022);
            set=ps.executeQuery();
            while(set.next()){
                Blob blob=set.getBlob("Myinfo");
                is=blob.getBinaryStream();
                byte[] flush=new byte[1024];
                int len=0;
                while(-1!=(len=is.read(flush))){
                    os.write(flush,0,len);
                }
                os.flush();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("没有找到类！");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("文件没找到！");
            e.printStackTrace();
        }finally {
            if (os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
