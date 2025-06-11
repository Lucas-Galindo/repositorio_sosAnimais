package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.AnimalInformacao;


public class Animal {

    private Long id;
    private Long idBaia;
    private Long idAcolhimento;

    private AnimalInformacao informacao;


    public Animal(Long id, Long idBaia, Long idAcolhimento, AnimalInformacao informacao){
            this.id = id;
            this.idBaia = idBaia;
            this.idAcolhimento = idAcolhimento;
            this.informacao = informacao;
        }


        public Long getId () {
            return id;
        }

        public void setId ( Long id){
            this.id = id;
        }

        public Long getIdBaia () {
            return idBaia;
        }

        public void setIdBaia ( Long idBaia){
            this.idBaia = idBaia;
        }

        public Long getIdAcolhimento () {
            return idAcolhimento;
        }

        public void setIdAcolhimento ( Long idAcolhimento){
            this.idAcolhimento = idAcolhimento;
        }


        public AnimalInformacao getInformacao () {
            return informacao;
        }

        public void setInformacao (AnimalInformacao informacao){
            this.informacao = informacao;
        }

    }