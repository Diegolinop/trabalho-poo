package main;

import entidades.Paciente;

public class Main {
    public static void main(String[] args) {
        Paciente paciente = new Paciente(
            "123.456.789-00", 
            "Diego", 
            "Suárez", 
            "(67) 99999-9999", 
            "Rua XXX, 123", 
            "Plano Unimed", 
            "08/10/2006"
        );

        paciente.setFuma(true);
        paciente.setBebe(true);
        paciente.setDiabetes(false);
        paciente.setDoencaCardiaca(false);
        paciente.setColesterol("Normal");

        paciente.adicionarAlergia("Cachorro");
        paciente.adicionarAlergia("Acaros");
        paciente.adicionarAlergia("Penicilina");
        
        paciente.removerAlergia("Acaros");

        paciente.adicionarCirurgia("Apendicite (2018)");
        paciente.adicionarCirurgia("Remoção de dente siso (2022)");

        System.out.println("Nome Completo: " + paciente.getNome() + " " + paciente.getSobrenome());
        System.out.println("CPF: " + paciente.getCpf());
        System.out.println("Telefone: " + paciente.getTelefone());
        System.out.println("Convênio: " + paciente.getTipoConvenio());
        System.out.println("Data de Nascimento: " + paciente.getDataNascimento());
        
        System.out.println("Fumante? " + (paciente.getFuma() ? "Sim" : "Não"));
        System.out.println("Consome álcool? " + (paciente.getBebe() ? "Sim" : "Não"));
        System.out.println("Diabético? " + (paciente.getDiabetes() ? "Sim" : "Não"));
        System.out.println("Possui doença cardíaca? " + (paciente.getDoencaCardiaca() ? "Sim" : "Não"));
        System.out.println("Nível de Colesterol: " + paciente.getColesterol());

        System.out.println("\nAlergias Registradas:");
        if (paciente.getAlergias().isEmpty()) {
            System.out.println("Nenhuma alergia registrada.");
        } else {
            for (String alergia : paciente.getAlergias()) {
                System.out.println(alergia);
            }
        }

        System.out.println("\nHistórico de Cirurgias:");
        if (paciente.getCirurgias().isEmpty()) {
            System.out.println("Nenhuma cirurgia registrada.");
        } else {
            for (String cirurgia : paciente.getCirurgias()) {
                System.out.println(cirurgia);
            }
        }
        
    }
}