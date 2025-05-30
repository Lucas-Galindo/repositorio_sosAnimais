package sosanimais.com.example.app.model.entity;

import sosanimais.com.example.app.model.AnimalInformacao;


public class Animal {

    private Long id;
    private int idBaia;
    private int idAcolhimento;

    private AnimalInformacao informacao;


    public Animal(Long id, int idBaia, int idAcolhimento, AnimalInformacao informacao){
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

        public int getIdBaia () {
            return idBaia;
        }

        public void setIdBaia ( int idBaia){
            this.idBaia = idBaia;
        }

        public int getIdAcolhimento () {
            return idAcolhimento;
        }

        public void setIdAcolhimento ( int idAcolhimento){
            this.idAcolhimento = idAcolhimento;
        }

        public AnimalInformacao getInformacao () {
            return informacao;
        }

        public void setInformacao (AnimalInformacao informacao){
            this.informacao = informacao;
        }

    }