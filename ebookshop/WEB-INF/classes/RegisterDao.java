/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adver
 */
import java.sql.Connection; //username=root, password=mysql
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterDao {
    private String dburl="jdbc:mysql://localhost:3306/userdb";
    private String dbuname="root";
    private String dbpassword="mysql";
    private String dbdriver="com.mysql.jdbc.Driver";
    
    public Connection getConnection(){
        Connection con=null;
        try {
            con=DriverManager.getConnection(dburl, dbuname, dbpassword);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public void loadDriver(String dbDriver){
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String insert(Member member){
        loadDriver(dbdriver);
        Connection con=getConnection();
        String result= "data entered successfully";
        String sql= "insert into userdb.member values(?,?,?,?)";
    try{
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setString(1, member.getUname());
        ps.setString(2, member.getPassword());
        ps.setString(3, member.getEmail());
        ps.setString(4, member.getPhone());
        ps.executeUpdate();
    } catch (SQLException e){
        result= "data not entered";
    }
    return result;
    }
    
}
