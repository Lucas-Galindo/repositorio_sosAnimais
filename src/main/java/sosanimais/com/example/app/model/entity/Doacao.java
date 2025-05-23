    package sosanimais.com.example.app.model.entity;

    public class Doacao {

        private Long id;
        private String nomeDoador;
        private String email;
        private String tipo; // NOVO: Tipo de doação (dinheiro, comida, etc.)
        private Double valor; // Somente se tipo == "dinheiro"
        private Produto produto; // NOVO: Produto aninhado (remédios, comida, etc.)
        private String mensagem;
        private String dataDoacao;

        public Doacao() {
        }

        public Doacao(Long id, String nomeDoador, String email, String tipo, Double valor,
                      Produto produto, String mensagem, String dataDoacao) {
            this.id = id;
            this.nomeDoador = nomeDoador;
            this.email = email;
            this.tipo = tipo;
            this.valor = valor;
            this.produto = produto;
            this.mensagem = mensagem;
            this.dataDoacao = dataDoacao;
        }

        public static class Produto {
            private String nome;
            private String validade;
            private String descricao;

            public Produto() {
            }

            public Produto(String nome, String validade, String descricao) {
                this.nome = nome;
                this.validade = validade;
                this.descricao = descricao;
            }

            public String getNome() {
                return nome;
            }

            public void setNome(String nome) {
                this.nome = nome;
            }

            public String getValidade() {
                return validade;
            }

            public void setValidade(String validade) {
                this.validade = validade;
            }

            public String getDescricao() {
                return descricao;
            }

            public void setDescricao(String descricao) {
                this.descricao = descricao;
            }
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNomeDoador() {
            return nomeDoador;
        }

        public void setNomeDoador(String nomeDoador) {
            this.nomeDoador = nomeDoador;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }

        public Produto getProduto() {
            return produto;
        }

        public void setProduto(Produto produto) {
            this.produto = produto;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getDataDoacao() {
            return dataDoacao;
        }

        public void setDataDoacao(String dataDoacao) {
            this.dataDoacao = dataDoacao;
        }
    }
