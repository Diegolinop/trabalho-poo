package main;
import java.util.ArrayList;
import java.util.Scanner;
import entidades.Medico;
import entidades.Paciente;
import entidades.Secretaria;

public class SaudeCia { 
    public static void main(String[] args) {
        ArrayList<Medico> medicos = new ArrayList<>();
        ArrayList<Paciente> pacientes = new ArrayList<>();
    
        Medico medicoNovo = new Medico(
            "123.456.789-00", 
            "Diego", 
            "Suárez", 
            "(67) 99999-9999", 
            "Rua XXX, 123", 
            "Cardiologista", 
            "12345"
        );
        
        adicionarMedico(medicos, medicoNovo);
        
        Scanner leitura = new Scanner(System.in);
        int opcao;
        
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Menu da Secretária");
            System.out.println("2 - Menu do Médico");
            System.out.println("3 - Enviar mensagens");
            System.out.println("0 - Sair do Sistema\n");
            
            System.out.print("Escolha uma opção: ");            
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    break;
                case 2:
                    menuMedico(medicos, pacientes);
                    break;
                case 3:
                    break;
                case 0:
                    break;
            }
        } while (opcao != 0);
    }
    
    private static void menuMedico(ArrayList<Medico> medicos, ArrayList<Paciente> pacientes) {
        Scanner leitura = new Scanner(System.in);
        System.out.println("\nInsira o crm do médico para obter acesso ao sistema: ");
        String crmMedico = leitura.nextLine();
        
        Medico medicoLogin = null;
        for (Medico medico : medicos) {
            if (medico.getCrm().equals(crmMedico)) {
                medicoLogin = medico;
                break;
            }
        }
        
        if (medicoLogin == null) {
            System.out.println("Insira um CRM válido para entrar no sistema");
            return;
        }
        
        // Menu do médico
        int opcao;
        
        do {
            System.out.println("\n--- MENU MÉDICO ----");
            System.out.println("1 - Ver pacientes");
            System.out.println("2 - Ver minhas consultas");
            System.out.println("0 - Voltar ao menu principal\n");
            
            System.out.print("Escolha uma opção: ");            
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    break;
            }
        } while (opcao != 0);
    }
    
    private static void menuSecretaria(){
        
    }
    
    public static void adicionarMedico(ArrayList<Medico> medicos, Medico medicoNovo) {
        medicos.add(medicoNovo);
    }
}
