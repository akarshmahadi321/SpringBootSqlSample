

import java.sql.*;
import java.util.*;

public class RDSConn {
    //Redshift driver: "jdbc:redshift://x.y.us-west-2.redshift.amazonaws.com:5439/dev";
    static final String dbURL = "jdbc:redshift://redshift-mum.cesq3a9slllq.ap-south-1.redshift.amazonaws.com:5439/whizdb";
    static final String Username = "akarsh";
    static final String UserPassword = "Suvarna1*";


    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;
        String UserInput;
        try{
            //Dynamically load driver at runtime.
            //Redshift JDBC 4.1 driver: com.amazon.redshift.jdbc42.Driver
            //Redshift JDBC 4.1 driver: com.amazon.redshift.jdbc41.Driver
            //Redshift JDBC 4 driver: com.amazon.redshift.jdbc4.Driver
            Class.forName("com.amazon.redshift.jdbc42.Driver");

            //Open a connection and define properties.
            System.out.println("Connecting to database...");
            Properties props = new Properties();

            //Uncomment the following line if using a keystore.
            //props.setProperty("ssl", "true");
            props.setProperty("user", Username);
            props.setProperty("password", UserPassword);

            conn = DriverManager.getConnection(dbURL, props);


            System.out.println("Getting content of table...");
            stmt = conn.createStatement();
            String sql;

            //Write your Query here
            sql = "select * from aws_events_loans_v2 where user_id='2c9f87a45d30b2ff015d352ef77b2cf4' ;";
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsm= rs.getMetaData();

            int columnCount=rsm.getColumnCount();
            // Print names of the columns
            for(int i=1;i<=columnCount;i++)
            {
                System.out.print(rsm.getColumnName(i)+",    ");
            }
            System.out.println();

            //Get the data from the result set.
            while(rs.next()){
                //Retrieve all columns of ResultSet
                for(int i=1;i<=columnCount;i++)
                {
                   System.out.print(rs.getString(i)+ ",    ");
                }

                System.out.println();

            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(Exception ex){
            //For convenience, handle all errors here.
            ex.printStackTrace();
        }finally{
            //Finally block to close resources.
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(Exception ex){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        System.out.println("Finished connectivity test.");
    }
}
