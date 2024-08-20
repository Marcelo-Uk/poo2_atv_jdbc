package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import entities.Aluno;
import jdbc.AlunoJDBC;

public class Program {

    public static void main(String[] args) {
        
        try {
            
            int opcao = 0;
            try (Scanner console = new Scanner(System.in)) {
                do {
                    System.out.println("####### Menu #######"
                                       + "\n1 - Cadastrar"
                                       + "\n2 - Listar"
                                       + "\n3 - Alterar"
                                       + "\n4 - Excluir"
                                       + "\n5 - Sair");
                    System.out.print("\n\tOpção: ");
                    opcao = Integer.parseInt(console.nextLine());
                    
                    AlunoJDBC acao = new AlunoJDBC();
                    
                    if (opcao == 1) {
                        Aluno a = new Aluno();
                        
                        System.out.println("\n ### Cadastrar Aluno ### \n");
                        
                        System.out.print("Nome: ");
                        a.setNome(console.nextLine());
                        
                        System.out.print("Sexo: ");
                        a.setSexo(console.nextLine());
                    
                        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        a.setDt_nasc(LocalDate.parse(console.nextLine(), formato));
                        
                        acao.salvar(a);
                    }
                    
                    if (opcao == 2) {
                        System.out.println("\n ### Listar Alunos ### \n");
                        List<Aluno> alunos = acao.listar();
                        for (Aluno aluno : alunos) {
                            System.out.println("ID: " + aluno.getId() 
                                + " | Nome: " + aluno.getNome() 
                                + " | Sexo: " + aluno.getSexo() 
                                + " | Data de Nascimento: " + aluno.getDt_nasc());
                        }
                    }
                    
                    if (opcao == 3) {
                        Aluno a = new Aluno();
                        
                        System.out.println("\n ### Alterar Aluno ### \n");
                        
                        System.out.print("ID do Aluno: ");
                        a.setId(Integer.parseInt(console.nextLine()));
                        
                        System.out.print("Novo Nome: ");
                        a.setNome(console.nextLine());
                        
                        System.out.print("Novo Sexo: ");
                        a.setSexo(console.nextLine());
                        
                        System.out.print("Nova Data de Nascimento (dd-mm-aaaa): ");
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        a.setDt_nasc(LocalDate.parse(console.nextLine(), formato));
                        
                        acao.alterar(a);
                    }
                    
                    if (opcao == 4) {
                        System.out.println("\n ### Excluir Aluno ### \n");
                        
                        System.out.print("ID do Aluno a ser Excluído: ");
                        int id = Integer.parseInt(console.nextLine());
                        acao.apagar(id);
                    }
                    
                } while (opcao != 5);
            }
            
        } catch (Exception e){
            System.out.println("Erro: " + e);
        }
    } 
}
