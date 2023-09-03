package com.example.demo.repositories;

import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Pedido;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PedidoRepository {

    // @Autowired
    private final JdbcTemplate jdbcTemplate;
    // @Autowired
    private final RowMapper<Pedido> pedidoRowMapper;

    public List<Pedido> findAll() {
        final List<Pedido> pedidos = jdbcTemplate.query(
                """
                    SELECT 
                        r.id, 
                        r.data_hora, 
                        r.total, 

                        f.id as f_id,
                        f.nome as f_nome, 
                        f.cpf as f_cpf, 
                        f.endereco as f_endereco, 
                        f.telefone as f_telefone, 

                        c.id as c_id,
                        c.nome as c_nome, 
                        c.cpf as c_cpf, 
                        c.telefone as c_telefone, 
                        c.rua as c_rua, 
                        c.bairro as c_bairro, 
                        c.numero as c_numero, 
                        c.complemento as c_complemento, 
                        c.cep as c_cep 
                    FROM 
                        relatorioDeVendas(cast('2023-08-01' as date), cast('2023-09-25' as date)) r 
                    INNER JOIN 
                        funcionario f on r.funcionario_id = f.id 
                    INNER JOIN
                        cliente c on r.cliente_id = c.id;
                """
                    , pedidoRowMapper);

        return pedidos;
    }

    private final String DELETE_SQL = """
            BEGIN;
            DELETE FROM item WHERE pedido_id in (
              SELECT id FROM pedido WHERE cliente_id = ?
            );
            DELETE FROM pedido WHERE cliente_id = ?;
            DELETE FROM cliente WHERE id = ?; COMMIT;
            """;

    public void deletar(int id) {
        jdbcTemplate.update(DELETE_SQL, id, id, id);
    }

    public Pedido inserir(Pedido p) throws SQLException {
        String sql = """
                INSERT INTO pedido (cliente_id, funcionario_id)
                VALUES (?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String[] colunas = new String[] { "id", "data_hora" };

        int insertsCount = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, colunas);
            ps.setInt(1, p.getCliente().getId());
            // ps.setInt(2, p.getFuncionario().getId());

            return ps;
        }, keyHolder);

        if (insertsCount == 1) {
            Map<String, Object> keys = keyHolder.getKeys();

            if (keys != null) keys.forEach((key, value) -> {
                if (key == "id") p.setId((int) value);
                if (key == "data_hora") p.setData_hora((java.sql.Timestamp) value);

            });
            return p;
        } else {
            throw new SQLException("Não retornou nenhum id");
        }
    }

    public void atualizar(Pedido p) {

        // jdbcTemplate.update("""
        //           UPDATE cliente
        //           SET  nome=?, cpf=?, telefone=?,
        //           rua=?, bairro=?, numero=?, complemento=?, cep=?
        //         WHERE id=?;

        //             """, p.getNome(), p.getCpf(), p.getTelefone(), p.getEndereco().getRua(),
        //         p.getEndereco().getBairro(), p.getEndereco().getNumero(),
        //         p.getEndereco().getComplemento(), p.getEndereco().getCep(),
        //         p.getId());
    }

}