package service;

import model.Cliente;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClienteProcessadorCsv {

    //  Adicionar uma lista de Clientes no csv
    //Path
    public static void addToCsv(Path path, List<Cliente> Clientes) {
        boolean arquivoExiste = Files.exists(path);

        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            if (!arquivoExiste) {
                writer.write("nome,cpf,email");
                writer.newLine();
            }

            for (Cliente Cliente : Clientes) {
                writer.write(toCsvLine(Cliente));
                writer.newLine();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToCsv(Path path, List<Cliente> Clientes) throws IOException {

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("nome,cpf,email");
            writer.newLine();
            for (Cliente Cliente : Clientes) {
                writer.write(toCsvLine(Cliente));
                writer.newLine();
            }
        }
    }

    //Leitura do Csv
    public static List<Cliente> readFromCsv(Path path) throws IOException {

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (Stream<String> lines = Files.lines(path)) {
            return lines.map(String::trim)
                    .filter(l -> !l.toLowerCase().startsWith("nome"))
                    .map(ClienteProcessadorCsv::parseCsvLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
    }

    private static String toCsvLine(Cliente Cliente) {
        return String.join(",",
                escapeCsv(Cliente.getNome()),
                escapeCsv(Cliente.getCpf()),
                Cliente.getEmail()
        );
    }

    private static Optional<Cliente> parseCsvLine(String line) {
        try {
            String[] parts = line.split(",", -1);
            if (parts.length < 3) {
                return Optional.empty();
            }
            String nome = unquote(parts[0].trim());
            String cpf = unquote(parts[1].trim());
            String email = unquote(parts[2].trim());

            return Optional.of(new Cliente(nome, cpf, email));
        } catch (Exception e) {
            System.err.println("Erro parse linha: " + line + " -> " + e.getMessage());
            return Optional.empty();
        }
    }


    private static String escapeCsv(String s) {
        if (s == null) {
            return "";
        }
        if (s.contains(",") || s.contains("\"") || s.contains("\n") || s.contains("\r")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private static String unquote(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        }
        return s;
    }
}
