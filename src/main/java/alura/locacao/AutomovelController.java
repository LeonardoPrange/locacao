package alura.locacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("automovel")
public class AutomovelController {
    private List<Automovel> automoveis = new ArrayList<Automovel>();
    @GetMapping
    public ResponseEntity<ArrayList<AutomovelViewModel>> obtemAutomoveis() throws SQLException {
        //Definição do comando SQL
        String query = "SELECT * FROM public.automovel;";

        //Lista para armazenar registros do banco de dados
        ArrayList<AutomovelViewModel> automoveisViewModel = new ArrayList<AutomovelViewModel>();

        //Obtem conexão com o banco de dados
        try (Connection connection = JavaPostgreSql.obtemConexao();
             //Formata comando
             PreparedStatement pst = connection.prepareStatement(query);

             //Executa comando
             ResultSet rs = pst.executeQuery()) {

            //Enquanto houver uma linha de resultado
            while (rs.next()) {
                //Obtem dados do banco e converte no formato do contrato da API
                AutomovelViewModel automovel = new AutomovelViewModel();
                automovel.id = rs.getObject("id", java.util.UUID.class);
                automovel.marca = rs.getString("marca");
                automovel.modelo = rs.getString("modelo");
                automovel.grupo = Grupo.valueOf(rs.getString("grupo"));
                automovel.valorDiaria = rs.getDouble("valor_diaria");
                automovel.quantidade = rs.getInt("quantidade");
                automoveisViewModel.add(automovel);
            }
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Retorna lista convertida
        return new ResponseEntity(automoveisViewModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity cadastraAutomovel(@RequestBody CadastraAutomovelPayload automovelPayload) throws Exception {
        //Definição do comando de inserção
        String query = "INSERT INTO automovel(\n" +
                "\tid, marca, modelo, grupo, valor_diaria, quantidade)\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?);";

        //Obtem conexão com o banco de dados
        Connection connection = JavaPostgreSql.obtemConexao();

        //Instancia um novo automovel
        Automovel novoAutomovel = new Automovel(
                automovelPayload.marca,
                automovelPayload.modelo,
                automovelPayload.grupo,
                automovelPayload.valorDiaria,
                automovelPayload.quantidade
        );

        //Formata comando SQL com os valores dinamicos
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setObject(1, novoAutomovel.getId(), Types.OTHER);
        pst.setString(2, novoAutomovel.getMarca());
        pst.setString(3, novoAutomovel.getModelo());
        pst.setString(4, novoAutomovel.getGrupo().toString());
        pst.setDouble(5, novoAutomovel.getValorDiaria());
        pst.setInt(6, novoAutomovel.getQuantidade());

        //Executa comando no banco de dados
        pst.executeUpdate();
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
