package alura.locacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.ArrayList;

public class AutomovelRepositorySQL implements AutomovelRepository {
    @Override
    public void save(Automovel automovel) throws Exception {
        //Definição do comando de inserção
        String query = "INSERT INTO automoveis(\n" +
                "\tid, marca, modelo, grupo, valor_diaria, quantidade)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?);";

        //Obtem conexão com o banco de dados
        Connection connection = JavaPostgreSql.obtemConexao();

        //Formata comando SQL com os valores dinamicos
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setObject(1, automovel.getId(), Types.OTHER);
        pst.setString(2, automovel.getMarca());
        pst.setString(3, automovel.getModelo());
        pst.setString(4, automovel.getGrupo().toString());
        pst.setDouble(5, automovel.getValorDiaria());
        pst.setInt(6, automovel.getQuantidade());

        //Executa comando no banco de dados
        pst.executeUpdate();
    }

    @Override
    public ArrayList<Automovel> list() throws Exception {
        //Definição do comando SQL
        String query = "SELECT * FROM public.automoveis;";

        //Lista para armazenar registros do banco de dados
        ArrayList<Automovel> automoveis = new ArrayList<>();

        //Obtem conexão com o banco de dados
        try (Connection connection = JavaPostgreSql.obtemConexao();
             //Formata comando
             PreparedStatement pst = connection.prepareStatement(query);

             //Executa comando
             ResultSet rs = pst.executeQuery()) {

            //Enquanto houver uma linha de resultado
            while (rs.next()) {
                //Obtem dados do banco e converte no formato do contrato da API
                Automovel automovel = new Automovel(
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    Grupo.valueOf(rs.getString("grupo")),
                    rs.getDouble("valor_diaria"),
                    rs.getInt("quantidade")
                );
                automovel.setId(rs.getObject("id", java.util.UUID.class));
                automoveis.add(automovel);
            }
            return automoveis;
        } catch (Exception ex) {
            throw new SQLException();
        }
    }
}
