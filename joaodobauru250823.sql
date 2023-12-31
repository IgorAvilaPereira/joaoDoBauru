DROP DATABASE IF EXISTS joaodobauru;

CREATE DATABASE joaodobauru;

\c joaodobauru;

-- https://www.geradorcpf.com/algoritmo_do_cpf.htm
CREATE OR REPLACE FUNCTION validaCPF(character(11)) RETURNS boolean AS
$$
DECLARE
    i integer;
    somatorio integer;
    multiplicador integer;
    nro1 integer;
    nro2 integer;
BEGIN
    IF ($1 = '00000000000' OR 
        $1 = '11111111111' OR 
        $1 = '22222222222' OR
        $1 = '33333333333' OR 
        $1 = '44444444444' OR 
        $1 = '55555555555' OR 
        $1 = '66666666666' OR 
        $1 = '77777777777' OR 
        $1 = '88888888888' OR 
        $1 = '99999999999') THEN
        RETURN FALSE;
    ELSE
        i := 1;
        somatorio := 0;
        multiplicador := 10;
        WHILE (i <= 9) LOOP
            -- RAISE NOTICE 'numero %', cast(substring($1, i, 1) as integer);
            somatorio := somatorio + cast(substring($1, i, 1) as integer) * multiplicador;
            -- RAISE NOTICE 'Multiplicador %', multiplicador;         
            multiplicador := multiplicador - 1;
            i := i + 1;
        END LOOP;
        
        nro1 := somatorio % 11;
        IF (nro1 < 2) THEN
            IF (cast(substring($1, 10, 1) as integer) != 0) THEN
                RETURN FALSE;
            END IF; 
        ELSIF ((11 - nro1) != cast(substring($1, 10, 1) as integer)) THEN
            RETURN FALSE;            
        END IF;
        
        i := 1;
        somatorio := 0;
        multiplicador := 11;
        WHILE (i <= 10) LOOP
            -- RAISE NOTICE 'numero %', cast(substring($1, i, 1) as integer);
            somatorio := somatorio + cast(substring($1, i, 1) as integer) * multiplicador;
            -- RAISE NOTICE 'Multiplicador %', multiplicador;         
            multiplicador := multiplicador - 1;
            i := i + 1;
        END LOOP;
        
        nro2 := somatorio % 11;
        IF (nro2 < 2) THEN
            IF (cast(substring($1, 11, 1) as integer) != 0) THEN
                RETURN FALSE;
            END IF; 
        ELSIF ((11 - nro2) != cast(substring($1, 11, 1) as integer)) THEN
            RETURN FALSE;            
        END IF;        
        -- RAISE NOTICE 'Somatorio %', somatorio;         
        RETURN TRUE;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION mascaraCPF(character(11)) RETURNS text AS
$$
-- DECLARE
BEGIN
    RETURN substring($1, 1, 3) || '.' || substring($1, 4, 3) || '.' || substring($1, 7, 3) || '-' || substring($1, 10, 2);
END;
$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION mascaraCEP(character(8)) RETURNS text AS
$$
-- DECLARE
BEGIN
    RETURN substring($1, 1, 2) || '.' || substring($1, 3, 3) || '-' || substring($1, 6, 3);
END;
$$ LANGUAGE 'plpgsql';

CREATE TABLE cliente (
    id serial primary key,
    nome character varying (100) not null,
    cpf character(11) check(validaCPF(cpf)) unique,
    telefone character varying(12),
    rua text,
    bairro text,
    numero text,
    complemento text,
    cep character(8),
    ativo boolean default true
);
INSERT INTO cliente (nome, cpf) VALUES 
('IGOR AVILA PEREIRA', '01763917037'),
('SÉRGIO HENRIQUES PEREIRA', '17658586072');

CREATE TABLE funcionario (
    id serial primary key,
    nome character varying (100) not null,
    cpf character(11) unique check (validaCPF(cpf)),
    endereco text,
    telefone character varying(11),
    ativo boolean default true
);

INSERT INTO funcionario (nome, cpf, endereco, telefone) VALUES 
('JOÃO', '98339518054', 'RUA QUE DÁ BUG', '53999999999');



CREATE TABLE pedido (
    id serial primary key,
    data_hora timestamp default current_timestamp,
    cliente_id integer references cliente (id),
    funcionario_id integer references funcionario (id)
);

INSERT INTO pedido (cliente_id, funcionario_id) VALUES
(1, 1);

INSERT INTO pedido (cliente_id, funcionario_id) VALUES
(2, 1);

CREATE TABLE produto (
    id serial primary key,
    descricao text not null,
    valor numeric(8,2),
    estoque integer,
    ativo boolean default true
);
INSERT INTO produto (descricao, valor, estoque) VALUES
('TORRADA AMERICANA', 10.00, 10);

INSERT INTO produto (descricao, valor, estoque) VALUES
('BAURU DE FILÉ', 20.00, 10);

CREATE TABLE item (
    id serial primary key,
    pedido_id integer references pedido (id),
    produto_id integer references produto (id),
    quantidade integer check (quantidade >= 1) DEFAULT 1,
    valor_atual numeric(8,2)
);




--DROP FUNCTION totalPedido;

CREATE OR REPLACE FUNCTION totalPedido(integer) RETURNS numeric(8,2) AS
$$
DECLARE
    total numeric(8,2) := 0.0;
BEGIN
    SELECT INTO total sum(item.valor_atual * item.quantidade) FROM item where item.pedido_id = $1;
    RETURN total;
END;
$$ LANGUAGE 'plpgsql';


-- recebe cliente_id
--  https://www.postgresqltutorial.com/postgresql-aggregate-functions/postgresql-string_agg-function/
-- select * from historicoPedidos(1);
-- DROP FUNCTION historicoPedidos;

CREATE OR REPLACE FUNCTION historicoPedidos() RETURNS TABLE(
        id integer,
		data_hora timestamp,
		f_id integer,
		f_nome character varying(100),
		f_cpf character(11),
		f_endereco  text,
		f_telefone  character varying(11),
        f_ativo boolean,
		c_id integer,
		c_nome  character varying(100),
		c_cpf character(11),
		c_telefone character varying(11),
		c_rua text,
		c_bairro text,
		c_numero text,
		c_complemento text,
		c_cep character(11), 
        c_ativo boolean,
        items text,
        total numeric(8,2)) AS
$$
BEGIN
     RETURN QUERY 
     SELECT 	
        pedido.id,
		pedido.data_hora,
		f.id as f_id,
		f.nome as f_nome,
		f.cpf as f_cpf,
		f.endereco as f_endereco,
		f.telefone as f_telefone,
        f.ativo as f_ativo,
		c.id as c_id,
		c.nome as c_nome,
		c.cpf as c_cpf,
		c.telefone as c_telefone,
		c.rua as c_rua,
		c.bairro as c_bairro,
		c.numero as c_numero,
		c.complemento as c_complemento,
		c.cep as c_cep,
        c.ativo as c_ativo,
        STRING_AGG( CAST(item.id as varchar),';') items,
        sum(item.quantidade*item.valor_atual) as total
        FROM 
            pedido 
        inner join 
            item on(pedido.id = item.pedido_id) 
        inner join 
            produto on (item.produto_id = produto.id) 
		inner join
			cliente c on (pedido.cliente_id = c.id)
		inner join
			funcionario f on (pedido.funcionario_id = f.id)
        group by 
            pedido.id, pedido.data_hora, c.id, f.id; 
END;
$$ LANGUAGE 'plpgsql';

--DROP FUNCTION clientesComMaisPedidos;

CREATE OR REPLACE FUNCTION clientesComMaisPedidos() RETURNS TABLE (cliente_id int) AS
$$
BEGIN
       RETURN QUERY SELECT cliente.id FROM cliente inner join pedido on (cliente.id = pedido.cliente_id) group by cliente.id having count(*) = (SELECT count(*) FROM cliente inner join pedido on (cliente.id = pedido.cliente_id) group by cliente.id ORDER BY COUNT(*) DESC LIMIT 1);
END;
$$ LANGUAGE 'plpgsql';


--DROP FUNCTION funcionariosComMaisPedidos;

CREATE OR REPLACE FUNCTION funcionariosComMaisPedidos() RETURNS TABLE (funcionario_id int) AS
$$
BEGIN
       RETURN QUERY SELECT funcionario.id FROM funcionario inner join pedido on (funcionario.id = pedido.funcionario_id) group by funcionario.id having count(*) = (SELECT count(*) FROM funcionario inner join pedido on (funcionario.id = pedido.funcionario_id) group by funcionario.id ORDER BY COUNT(*) DESC LIMIT 1);
END;
$$ LANGUAGE 'plpgsql';

-- joaodobauru=# SELECT * FROM relatorioDeVendas(cast('2023-08-01' as date), cast('2023-08-25' as date));
-- DROP FUNCTION relatorioDeVendas;

CREATE OR REPLACE FUNCTION relatorioDeVendas(date, date) RETURNS TABLE(id integer, data_hora timestamp, cliente_id integer, funcionario_id integer, total numeric(8,2)) AS
$$
BEGIN
     RETURN QUERY Select pedido.id, pedido.data_hora, pedido.cliente_id, pedido.funcionario_id, sum(item.quantidade*item.valor_atual) as total FROM pedido inner join item on (pedido.id = item.pedido_id) 
     where cast(pedido.data_hora as date) between $1 and $2 GROUP by pedido.id, pedido.data_hora, pedido.cliente_id, pedido.funcionario_id;

END;
$$ LANGUAGE 'plpgsql';


--DROP FUNCTION adicionaItem;
--CREATE OR REPLACE FUNCTION adicionaItem(produto_id integer, qtde integer, pedido_id integer) RETURNS BOOLEAN AS
--$$
--DECLARE
--    qtde_estoque integer := 0;
--    valor_produto numeric(8,2) := 0;
--BEGIN
--    SELECT estoque FROM produto WHERE id = produto_id INTO qtde_estoque;
--    SELECT valor FROM produto WHERE id = produto_id INTO valor_produto;
--    IF (qtde <= qtde_estoque) THEN
--        -- BEGIN;
--            INSERT INTO item (produto_id, pedido_id, quantidade, valor_atual) VALUES (produto_id, pedido_id, qtde, valor_produto * qtde);
--            UPDATE produto SET estoque = estoque - qtde WHERE id = produto_id;
--        -- COMMIT;
--        RETURN TRUE;
--    END IF;
--    RETURN FALSE;
--END;
--$$ LANGUAGE 'plpgsql';

-- 20/10 => adicionaItem agora virou uma Trigger
CREATE OR REPLACE FUNCTION adicionaItemTrigger() RETURNS TRIGGER AS
$$
DECLARE
    qtde_estoque integer := 0;
    valor_produto numeric(8,2) := 0;
BEGIN
    SELECT estoque FROM produto WHERE id = NEW.produto_id INTO qtde_estoque;
--    SELECT valor FROM produto WHERE id = NEW.produto_id INTO valor_produto;
    IF (NEW.quantidade <= qtde_estoque) THEN
        -- BEGIN;
--            INSERT INTO item (produto_id, pedido_id, quantidade, valor_atual) VALUES (NEW.produto_id, NEW.pedido_id, NEW.quantidade, valor_produto * NEW.quantidade);
            UPDATE produto SET estoque = estoque - NEW.quantidade WHERE id = NEW.produto_id;
        -- COMMIT;
        RETURN NEW;
    END IF;
    RETURN null;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER trigger_adicionaItemTrigger BEFORE INSERT ON item FOR EACH ROW EXECUTE PROCEDURE adicionaItemTrigger();

--  INSERT INTO item (produto_id, pedido_id, quantidade, valor_atual) VALUES (NEW.produto_id, NEW.pedido_id, NEW.quantidade, valor_produto * NEW.quantidade);



CREATE OR REPLACE FUNCTION atualizaItem(item_id integer, qtde_nova  integer) RETURNS BOOLEAN AS
$$
DECLARE
    qtde_item integer := 0;
    prod_id integer := 0;
    estoque_atual integer := 0;
BEGIN

 SELECT item.quantidade, item.produto_id, produto.estoque FROM item INNER JOIN produto ON item.produto_id = produto.id where item.id = item_id INTO qtde_item, prod_id, estoque_atual;

    IF (estoque_atual + qtde_item >= qtde_nova) THEN
        IF (qtde_nova < qtde_item) THEN
            UPDATE produto SET estoque = estoque_atual + (qtde_item - qtde_nova) WHERE id = prod_id;
        ELSE
            UPDATE produto SET estoque = estoque_atual - (qtde_nova - qtde_item) WHERE id = prod_id;       
        END IF;
        UPDATE item SET quantidade = qtde_nova WHERE id = item_id;
        RETURN TRUE;
    END IF;    
    RETURN FALSE;
END;
$$ LANGUAGE 'plpgsql';


-- 20/10 => removeItem agora virou uma Trigger
--CREATE OR REPLACE FUNCTION removeItem(item_id integer) RETURNS BOOLEAN AS
--$$
--DECLARE
--    qtde_item integer := 0;
--    prod_id integer := 0;
--    estoque_atual integer := 0;
--BEGIN
--    SELECT item.quantidade, item.produto_id, produto.estoque FROM item INNER JOIN produto ON item.produto_id = produto.id where item.id = item_id INTO qtde_item, prod_id, estoque_atual;
--
--    IF (qtde_item > 0 /*AND qtde_item < estoque_atual*/) THEN
--            DELETE from item WHERE id = item_id;
--            UPDATE produto SET estoque = estoque_atual + qtde_item WHERE id = prod_id;
--        RETURN TRUE;
--	END IF;
--    RETURN FALSE;
--END;
--$$ LANGUAGE 'plpgsql';

CREATE OR REPLACE FUNCTION removeItemTrigger() RETURNS trigger AS
$$
DECLARE
    qtde_item integer := 0;
    prod_id integer := 0;
    estoque_atual integer := 0;
BEGIN
    SELECT item.quantidade, item.produto_id, produto.estoque FROM item INNER JOIN produto ON item.produto_id = produto.id where item.id = OLD.id INTO qtde_item, prod_id, estoque_atual;

    IF (qtde_item > 0 /*AND qtde_item < estoque_atual*/) THEN
--            DELETE from item WHERE id = item_id;
            UPDATE produto SET estoque = estoque_atual + qtde_item WHERE id = prod_id;
        RETURN OLD;
	END IF;
    RETURN NULL;
END;
$$ LANGUAGE 'plpgsql';


CREATE TRIGGER trigger_removeItemTrigger BEFORE DELETE ON item FOR EACH ROW EXECUTE PROCEDURE removeItemTrigger();


-- prod_id, qtde, pedido_id
--SELECT adicionaItem(1, 2, 1);
--SELECT adicionaItem(2, 1, 1);

--INSERT INTO item (pedido_id, produto_id, quantidade, valor_atual) VALUES
--(1, 1, 2, 10.00);
--
--INSERT INTO item (pedido_id, produto_id, quantidade, valor_atual) VALUES
--(1, 2, 1, 20.00);





