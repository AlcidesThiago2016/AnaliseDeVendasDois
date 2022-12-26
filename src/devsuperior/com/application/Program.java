package devsuperior.com.application;

import devsuperior.com.entities.Sale;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Entre com o caminho do arquivo:  ");
        String patch = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(patch))){
            List<Sale> list = new ArrayList<>();

            String line = br.readLine();
            while (line != null){
                String[] fields = line.split(",");
                list.add(new Sale(Integer.parseInt(fields[0]),Integer.parseInt(fields[1]),fields[2],
                        Integer.parseInt(fields[3]),Double.parseDouble(fields[4])));
                line = br.readLine();
            }

            Set<String> names = new HashSet<>();
            names.addAll(list
                    .stream()
                    .map(s -> s.getSeller()).collect(Collectors.toList()));
            System.out.println("Total de vendas por vendedor: ");
            for (String name : names) {
                Double totalSum = list
                        .stream()
                        .filter(p -> p.getSeller().equals(name))
                        .map(Sale::getTotal)
                        .reduce(0.0, Double::sum);
                System.out.println(name + " - R$ " + String.format("%.2f",totalSum));
            }

        }catch (FileNotFoundException e){
            System.out.println("Erro: "+ patch + " (O sistema não pode encontrar o arquivo especificado)");
        }catch (IOException e){
            System.out.println("Erro: " + e.getMessage());
        }
        sc.close();
    }
}
