-- EMPRESA

CREATE TABLE informacoes (
                             emp_id INTEGER PRIMARY KEY,
                             emp_nome VARCHAR(255),
                             emp_nomeFantasia VARCHAR(300),
                             emp_descricao TEXT,
                             emp_endereco TEXT,
                             emp_telefone VARCHAR(20),
                             emp_capacidade integer,
                             emp_cnpj VARCHAR(40) unique
);

CREATE SEQUENCE empresa_id_seq START 1;
ALTER TABLE informacoes ALTER COLUMN emp_id SET DEFAULT nextval(' empresa_id_seq');

-- FIM EMPRESA



-- PESSOA

CREATE TABLE Pessoa (
                        Pess_ID INTEGER PRIMARY KEY,
                        Pess_Nome VARCHAR(4000) NOT NULL,
                        Pess_CPF VARCHAR(4000) NOT NULL UNIQUE,
                        Pess_Telefone VARCHAR(4000) NOT NULL UNIQUE,
                        Pess_Email VARCHAR(4000) NOT NULL UNIQUE
);
CREATE SEQUENCE pessoa_id_seq START 1;
ALTER TABLE Pessoa ALTER COLUMN Pess_ID SET DEFAULT nextval('pessoa_id_seq');

-- FIM PESSOA


-- FUNCIONARIO

CREATE TABLE funcionario (
                             Usu_id INTEGER PRIMARY KEY,
                             Func_Login VARCHAR(4000) NOT NULL,
                             Func_Senha VARCHAR(4000) NOT NULL,
                             Func_Matricula INTEGER UNIQUE,
                             CONSTRAINT funcionario_Usuario_FK FOREIGN KEY (Usu_id) REFERENCES Pessoa(Pess_id)
);
CREATE SEQUENCE funcionario_matricula_seq START 1;
ALTER TABLE funcionario ALTER COLUMN func_matricula SET DEFAULT nextval('funcionario_matricula_seq');

-- FIM FUNCIONARIO

-- DOADOR

CREATE TABLE Doador (
                        Usu_id INTEGER PRIMARY KEY,
                        Doador_Matricula INTEGER UNIQUE,
                        CONSTRAINT Doador_Pessoa_FK FOREIGN KEY (Usu_id) REFERENCES Pessoa(Pess_ID)
);
CREATE SEQUENCE doador_matricula_seq START 100;
ALTER TABLE Doador ALTER COLUMN doador_matricula SET DEFAULT nextval('doador_matricula_seq');



-- ADOTANTE
CREATE TABLE adotante (
                          Usu_id Integer PRIMARY KEY,
                          Adotante_Matricula INTEGER UNIQUE,
    --adocao_id INTEGER,

                          CONSTRAINT adotante_Usuario_FK FOREIGN KEY (Usu_id) REFERENCES Pessoa(Pess_ID)
    --CONSTRAINT adotante_adocao_FK FOREIGN KEY (adocao_id) REFERENCES Adocao_Animal(ado_id)
);

-- FIM ADOTANTE

-- ACOLHIMENTO

CREATE TABLE Acolhimento (
                             aco_cod INTEGER PRIMARY KEY,
                             aco_data timestamp NOT NULL,
                             funcionario_func_mat Integer,
                             adotante_Ado_cod INTEGER,
                             Animal_ani_cod INTEGER NOT NULL,

                             CONSTRAINT Acolhimento_funcionario_FK FOREIGN KEY (funcionario_func_mat) REFERENCES funcionario(func_matricula),
                             CONSTRAINT Acolhimento_adotante_FK FOREIGN KEY (adotante_Ado_cod) REFERENCES adotante(Adotante_Matricula)
);

CREATE SEQUENCE acolhimento_id_seq START 1;
ALTER TABLE Acolhimento ALTER COLUMN aco_cod SET DEFAULT nextval('acolhimento_id_seq');


-- FIM ACOLHIMENTO



-- BAIAS

CREATE TABLE Baia (
                      baia_id INTEGER PRIMARY KEY,
                      baia_qtde INTEGER,
                      baia_nome VARCHAR(100) NOT NULL,
                      baia_categoria VARCHAR(50) NOT NULL

);

CREATE SEQUENCE baia_id_seq START 1;
ALTER TABLE Baia ALTER COLUMN baia_id SET DEFAULT nextval('baia_id_seq');

-- FIM BAIAS

-- ANIMAL
CREATE TABLE Animal (
                        ani_cod INTEGER PRIMARY KEY,
                        ani_nome VARCHAR(4000) NOT NULL,
                        ani_raca VARCHAR(4000) NOT NULL,
                        ani_desc VARCHAR(4000) NOT NULL,
                        ani_status CHAR(1) NOT NULL,
                        ani_idade INTEGER,
                        ani_statusVida CHAR(1),
                        Acolhimento_aco_cod INTEGER,
                        Baia_baia_cod INTEGER,
                        CONSTRAINT Animal_Acolhimento_FK FOREIGN KEY (Acolhimento_aco_cod) REFERENCES Acolhimento(aco_cod),
                        CONSTRAINT Animal_Baia_FK FOREIGN KEY (Baia_baia_cod) REFERENCES Baia(baia_id)
);

CREATE SEQUENCE animal_id_seq START 1;
ALTER TABLE Animal ALTER COLUMN ani_cod SET DEFAULT nextval('animal_id_seq');


-- FIM ANIMAL



-- Inicio Adocao_animal

CREATE TABLE ADOCAO_ANIMAL(
                              adota_cod INTEGER PRIMARY KEY,
                              adota_data date,
                              funcionario_func_cod INTEGER,
                              animal_ani_cod INTEGER,
                              adotante_mat INTEGER,
                              CONSTRAINT Adocao_Animal_func_FK FOREIGN KEY (funcionario_func_cod) REFERENCES funcionario(func_matricula),
                              CONSTRAINT Adocao_Animal_animal_FK FOREIGN KEY (animal_ani_cod) REFERENCES Animal(ani_cod),
                              CONSTRAINT Adocao_Animal_adotante_FK FOREIGN KEY (adotante_mat) REFERENCES Adotante(adotante_matricula)
);

CREATE SEQUENCE ADOCAO_ANIMAL_id_seq START 1;
ALTER TABLE ADOCAO_ANIMAL ALTER COLUMN adota_cod SET DEFAULT nextval('ADOCAO_ANIMAL_id_seq');
-- FIM adocao_animal




-- TRANSFERENCIAS

CREATE TABLE Transferencia (
                               tb_id INTEGER PRIMARY KEY,
                               tb_date TIMESTAMP,
                               func_mat INTEGER NOT NULL,
                               CONSTRAINT Transferir_Baia_funcionario_FK FOREIGN KEY (func_mat) REFERENCES funcionario(Func_Matricula)

);

CREATE SEQUENCE transferencia_id_seq START 1;
ALTER TABLE Transferencia ALTER COLUMN tb_id SET DEFAULT nextval('transferencia_id_seq');

CREATE TABLE Transferir_to_Baia (
                                    ttb_id INTEGER PRIMARY KEY NOT NULL,
                                    ttb_Destino INTEGER,
                                    ttb_Origem INTEGER,
                                    tb_id INTEGER,
                                    ani_id INTEGER,
    --baia_cod INTEGER,

                                    CONSTRAINT Transferencia_Associativa_FK FOREIGN KEY (tb_id) REFERENCES Transferencia(tb_id),
    --CONSTRAINT Transferir_Baia_FK FOREIGN KEY (baia_cod) REFERENCES Baia (baia_id),
                                    CONSTRAINT Transferir_Animal_FK FOREIGN KEY (ani_id) REFERENCES Animal(ani_cod)

);
CREATE SEQUENCE Transferir_to_ttb_id_seq START 1;
ALTER TABLE Transferir_to_Baia ALTER COLUMN ttb_id SET DEFAULT nextval('Transferir_to_ttb_id_seq');
--ALTER TABLE transferir_to_baia DROP COLUMN baia_cod;


-- FIM TRANSFERENCIAS



-- CATEGORIA


CREATE TABLE Categoria (
                           Cat_Id INTEGER PRIMARY KEY,
                           Cat_Nome VARCHAR(4000),
                           Cat_Descricao VARCHAR(4000)
);
CREATE SEQUENCE categoria_id_seq START 1;
ALTER TABLE Categoria ALTER COLUMN Cat_Id SET DEFAULT nextval('categoria_id_seq');
-- FIM CATEGORIA


-- ARMAZENAMENTO

CREATE TABLE Armazenamento (
                               Arm_Id INTEGER PRIMARY KEY,
                               Arm_Tipo VARCHAR(4000) NOT NULL,
                               Arm_Quant INTEGER,
                               Arm_estado CHAR
);
CREATE SEQUENCE armazenamento_id_seq START 1;
ALTER TABLE Armazenamento ALTER COLUMN Arm_Id SET DEFAULT nextval('armazenamento_id_seq');

-- FIM ARMAZENAMENTO


-- PRODUTO

CREATE TABLE Produto (
                         Prod_Id INTEGER PRIMARY KEY,
                         Prod_Nome VARCHAR(4000) NOT NULL,
                         Prod_Descricao VARCHAR(4000),
                         Prod_Preco FLOAT
);

CREATE SEQUENCE produto_id_seq START 1;
ALTER TABLE Produto ALTER COLUMN Prod_Id SET DEFAULT nextval('produto_id_seq');

-- FIM PRODUTO


-- SERVICO
CREATE TABLE Servico (
                         serv_cod INTEGER PRIMARY KEY,
                         serv_nome VARCHAR(4000) NOT NULL,
                         serv_desc VARCHAR(4000)
);

CREATE SEQUENCE servico_cod_seq START 1;
ALTER TABLE Servico ALTER COLUMN serv_cod SET DEFAULT nextval('servico_cod_seq');

-- FIM SERVICO



-- EXECUTA


CREATE TABLE executa (
                         exe_id integer primary key,
                         funcionario_Usu_mat integer,
                         Servico_serv_cod INTEGER,
                         CONSTRAINT executa_funcionario_FK FOREIGN KEY (funcionario_Usu_mat) REFERENCES funcionario(func_matricula),
                         CONSTRAINT executa_Servico_FK FOREIGN KEY (Servico_serv_cod) REFERENCES Servico(serv_cod)
);

-- FIM EXECUTA




-- INSUMO

CREATE TABLE Insumo (
                        insu_id INTEGER PRIMARY KEY,
                        insu_nome VARCHAR(4000) NOT NULL,
                        insu_categoria VARCHAR,
                        insu_qtd_estoque INTEGER,
                        Insu_Descricao VARCHAR(4000),
                        Categoria_Cat_Id INTEGER,
                        CONSTRAINT Insumo_Categoria_FK FOREIGN KEY (Categoria_Cat_Id) REFERENCES Categoria(Cat_Id)
);

CREATE SEQUENCE insumo_id_seq START 1;
ALTER TABLE Insumo ALTER COLUMN insu_id SET DEFAULT nextval('insumo_id_seq');

-- FIM INSUMO



-- COMPRA

CREATE TABLE Compra (
                        comp_id INTEGER PRIMARY KEY,
                        comp_data timestamp,
                        comp_valorFinal FLOAT,

                        Func_Matricula INTEGER,
                        CONSTRAINT Compra_funcionario_FK FOREIGN KEY (Func_Matricula) REFERENCES funcionario(Func_Matricula)
);

CREATE SEQUENCE compra_id_seq START 1;
ALTER TABLE Compra ALTER COLUMN comp_id SET DEFAULT nextval('compra_id_seq');


-- FIM COMPRA


-- DOACAO INSUMO

CREATE TABLE Doacao_Insumos (
                                doa_id INTEGER PRIMARY KEY,
                                doa_data timestamp,
                                Usuario_Usu_ID INTEGER,
                                CONSTRAINT Doacao_Insumos_Usuario_FK FOREIGN KEY (Usuario_Usu_ID) REFERENCES Pessoa(Pess_ID)
);

CREATE SEQUENCE doacao_insumos_id_seq START 1;
ALTER TABLE Doacao_Insumos ALTER COLUMN doa_id SET DEFAULT nextval('doacao_insumos_id_seq');

-- FIM DOACAO INSUMO





-- ITENS COMPRA PRODUTO

CREATE TABLE Itens_Compras_Produto (
                                       icp_id INTEGER PRIMARY KEY,
                                       Produto_Prod_Id INTEGER,
                                       Compra_comp_id INTEGER,
                                       Qtde INTEGER,
                                       ValorParcial FLOAT,
                                       CONSTRAINT Itens_Compras_Produto_Produto_FK FOREIGN KEY (Produto_Prod_Id) REFERENCES Produto(Prod_Id),
                                       CONSTRAINT Itens_Compras_Produto_Compra_FK FOREIGN KEY (Compra_comp_id) REFERENCES Compra(comp_id)
);

CREATE SEQUENCE Itens_Compras_Produto_id_seq START 1;
ALTER TABLE Itens_Compras_Produto ALTER COLUMN icp_id SET DEFAULT nextval('Itens_Compras_Produto_id_seq');


-- FIM ITENS COMPRA PRODUTO


-- ITENS COMPRA INSUMO

CREATE TABLE Itens_Compras_Insumo (
                                      ici_id INTEGER PRIMARY KEY,
                                      comp_cod INTEGER,
                                      insu_cod INTEGER,
                                      qtde INTEGER,
                                      valorF FLOAT,
                                      CONSTRAINT Itens_Compras_Insumo_Compra_FK FOREIGN KEY (comp_cod) REFERENCES Compra(comp_id),
                                      CONSTRAINT Itens_Compras_Insumo_Insumo_FK FOREIGN KEY (insu_cod) REFERENCES Insumo(insu_id)
);
CREATE SEQUENCE Itens_Compras_Insumo_id_seq START 1;
ALTER TABLE Itens_Compras_Insumo ALTER COLUMN ici_id SET DEFAULT nextval('Itens_Compras_Insumo_id_seq');

-- FIM ITENS COMPRA INSUMO


-- ITENS MOVIMENTADOS DOACAO

CREATE TABLE Itens_Movimentados_Doacao (
                                           imd_id INTEGER PRIMARY KEY,
                                           Doacao_Insumos_doa_id INTEGER,
                                           Insumo_insu_id INTEGER,
                                           quantidade INTEGER,
                                           data_validade timestamp,
                                           CONSTRAINT Itens_Movimentados_Doacao_Doacao_Insumos_FK FOREIGN KEY (Doacao_Insumos_doa_id) REFERENCES Doacao_Insumos(doa_id),
                                           CONSTRAINT Itens_Movimentados_Doacao_Insumo_FK FOREIGN KEY (Insumo_insu_id) REFERENCES Insumo(insu_id)
);
CREATE SEQUENCE Itens_Movimentados_Doacao_id_seq START 1;
ALTER TABLE Itens_Movimentados_Doacao ALTER COLUMN imd_id SET DEFAULT nextval('Itens_Movimentados_Doacao_id_seq');

-- FIM ITENS MOVIMENTADOS DOACAO


-- ITENS MOVIMENTADOS

CREATE TABLE Itens_Movimentados (
                                    IM_ID INTEGER PRIMARY KEY,
                                    Armazenamento_Arm_Cod INTEGER,
                                    funcionario_Usu_mat integer,
                                    Data_Entrada timestamp,
                                    Data_Saida timestamp,
                                    Compra_comp_id INTEGER,
                                    Doacao_Insumos_doa_id INTEGER,
                                    CONSTRAINT Itens_Movimentados_Armazenamento_FK FOREIGN KEY (Armazenamento_Arm_Cod) REFERENCES Armazenamento(Arm_Id),
                                    CONSTRAINT Itens_Movimentados_funcionario_FK FOREIGN KEY (funcionario_Usu_mat) REFERENCES funcionario(func_matricula),
                                    CONSTRAINT Itens_Movimentados_Compra_FK FOREIGN KEY (Compra_comp_id) REFERENCES Compra(comp_id),
                                    CONSTRAINT Itens_Movimentados_Doacao_Insumos_FK FOREIGN KEY (Doacao_Insumos_doa_id) REFERENCES Doacao_Insumos(doa_id)
);

CREATE SEQUENCE Itens_Movimentados_id_seq START 1;
ALTER TABLE Itens_Movimentados ALTER COLUMN im_id SET DEFAULT nextval('itens_Movimentados_id_seq');

-- FIM ITENS MOVIMENTADOS


-- ANIMAIS_INSUMO

CREATE TABLE Animal_Insumo (
                               AI_ID INTEGER PRIMARY KEY,
                               ani_id INTEGER,
                               insu_cod INTEGER,
                               AI_DataExecucao timestamp,
                               CONSTRAINT Animal_Insumo_Animal_FK FOREIGN KEY (ani_id) REFERENCES Animal(ani_cod),
                               CONSTRAINT Animal_Insumo_Insumo_FK FOREIGN KEY (insu_cod) REFERENCES Insumo(insu_id)
);

CREATE SEQUENCE Animal_Insumos_id_seq START 1;
ALTER TABLE Animal_Insumo ALTER COLUMN ani_id SET DEFAULT nextval('Animal_Insumos_id_seq');


-- FIM ANIMAL_INSUMO



CREATE SEQUENCE adotante_matricula_seq START 100;
ALTER TABLE adotante ALTER COLUMN adotante_matricula SET DEFAULT nextval('adotante_matricula_seq');

ALTER TABLE adotante ADD COLUMN adocao_id INTEGER;
ALTER TABLE adotante ADD CONSTRAINT adotante_adocao_FK FOREIGN KEY (adocao_id) REFERENCES Adocao_Animal(adota_cod);

---- TABELA FUNCIONARIO ----
ALTER TABLE funcionario
    ADD COLUMN func_role TEXT DEFAULT 'USER' CHECK (func_role IN ('ADMIN', 'USER'));


ALTER TABLE baia DROP COLUMN baia_qtde;
ALTER TABLE baia ADD COLUMN baia_qtde NUMERIC DEFAULT 0;
ALTER TABLE baia ADD CONSTRAINT positive_baia_qtde CHECK (baia_qtde >= 0);

--UPDATE adocao_animaFl a
--SET Func_Matricula = 15;

--ALTER TABLE adocao_animal
--ALTER COLUMN Func_Matricula TYPE INTEGER
--USING Func_Matricula::INTEGER;



--ALTER TABLE adocao_animal
--ADD COLUMN adotante_mat INTEGER;

--ALTER TABLE adocao_animal
--ADD CONSTRAINT fk_adotante
--FOREIGN KEY (adotante_mat)
--REFERENCES adotante(adotante_matricula);

--UPDATE ADOCAO_ANIMAL
--SET ADOTANTE_MAT = AD.ADOTANTE_MATRICULA
--FROM ADOTANTE AD
--WHERE ADOTA_COD = AD.ADOCAO_ANIMAL_ADOTA_COD;