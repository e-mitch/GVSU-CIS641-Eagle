package sample.commentarius;
import java.sql.*;
import java.lang.*;
import java.sql.Connection;

public class Model{
    Connection connection;
    public Model() {
        connection = SqliteConnection.Connector();
        if(connection == null) System.exit(1);
    }

    public boolean isDbConnected(){
        try {
            return !connection.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
