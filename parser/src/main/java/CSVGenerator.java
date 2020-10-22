import java.sql.*;

import com.opencsv.CSVWriter;

import java.io.FileWriter;


public class CSVGenerator {
    public static void main(String[] args) throws Exception {


        String url = "jdbc:mysql://whizdm-analytics-replica.cvqk957donxp.ap-south-1.rds.amazonaws.com/whizdb";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection(url, "ryuk", "shIniGami");
        Statement stmt = conn.createStatement();
        int collection_agency_count = 5;
        for (int i = 1; i <= collection_agency_count; i++) {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from collection_agency_allocation where coll_agency_id_ref=? ");
            preparedStatement.setInt(1, i);

            ResultSet resultSet = preparedStatement.executeQuery();
            CSVWriter csvWriter = new CSVWriter(new FileWriter("output_" + i + ".csv"));


            Boolean includeHeaders = true;
            csvWriter.writeAll(resultSet, includeHeaders);
        }


    }
}
