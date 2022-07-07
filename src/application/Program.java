package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			List<Sale> sales = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				
				sales.add(new Sale(month, year, seller, items, total));
				
				line = br.readLine();
			}
			
			Set<String> names = new HashSet<>();
			names = sales.stream().map(s -> s.getSeller()).collect(Collectors.toSet());
			
			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			for (String seller: names) {
				double sum = sales.stream()
						.filter(s -> s.getSeller().equals(seller))
						.map(s -> s.getTotal())
						.reduce(0.0, (x, y) -> x + y);
			System.out.printf("%s - R$ %.2f\n", seller, sum);
			}
			
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
		sc.close();
	}

}
