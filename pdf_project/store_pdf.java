package pdf_project;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
 
public class store_pdf {
    public static void main(String[] args) {
        DB db = new DB();
        Connection conn=db.dbConnect(
                "jdbc:oracle:thin:@//localhost:1521/orcl","CISADM","CISADM");
         
        db.insertPDF(conn,"C:\\Users\\RIA-Admin\\Downloads\\sample.pdf");
        db.getPDFData(conn);
    }
     
}
 
class DB {
    public DB() {}
     
    public Connection dbConnect(String db_connect_string,
            String db_userid, String db_password) {
        try {
        //  Class.forName("net.sourceforge.jtds.jdbc.Driver");
        	Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(
                    db_connect_string, db_userid, db_password);
             
            System.out.println("connected");
            return conn;
             
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     
    public void insertPDF(Connection conn,String filename) {
        int len;
        String query;
        PreparedStatement pstmt;
         
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            len = (int)file.length();
            query = ("insert into CM_PDF_STORE VALUES(?,?,?)");
            pstmt = conn.prepareStatement(query);
       //     pstmt.setString(1,file.getName());
         //   pstmt.setInt(2, len);
           pstmt.setLong(1,genRefNo());  
           pstmt.setString(2,file.getName());
            //method to insert a stream of bytes
            pstmt.setBinaryStream(3, fis, len); 
           pstmt.executeUpdate();
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public long genRefNo()
    {
    	return ThreadLocalRandom.current().nextLong(100000000000L,999999999999L);
    }
     
    public void getPDFData(Connection conn) {
    	        
        byte[] fileBytes;
        String query;
        try {
            query = 
              "select CM_PDF_BLOB from CM_PDF_STORE where CM_PDF_NAME like '%sample.pdf%' ";
            Statement state =conn.createStatement();
            ResultSet rs = state.executeQuery(query);
            if (rs.next()) {
                fileBytes = rs.getBytes(1);
                OutputStream targetFile=  new FileOutputStream(
                        "D://saved//retrieved.pdf");
                targetFile.write(fileBytes);
                targetFile.close();
            }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
};
