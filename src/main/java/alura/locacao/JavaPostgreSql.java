package alura.locacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaPostgreSql {
    public static Connection obtemConexao() throws SQLException {
        // Definição do endereço do banco de dados, usuário e senha.
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        //Obtem a conexão com o banco de dados
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}

