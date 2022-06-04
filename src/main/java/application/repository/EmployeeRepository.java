package application.repository;

import application.model.Employee;
import application.model.Name;
import application.model.Specialization;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRepository {
    private HashMap<Specialization, List<Employee>> repository = new HashMap<>();

    public List<Employee> getEmployees(){
        return repository.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public EmployeeRepository(String file) throws IOException {
        if (file.endsWith(".json")) {
            ObjectMapper mapper = new ObjectMapper();
            try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(Path.of(file)))) {
                this.repository = mapper.readValue(bis, new TypeReference<>() {
                });
            }
        }else if (file.endsWith(".csv")) {
            try(BufferedReader reader = new BufferedReader(new FileReader(file))){
                while (reader.ready()){
                    try {
                        String line = reader.readLine();
                        String[] split = line.split(";");
                        String firstName = split[0];
                        String lastName = split[1];
                        String middleName = split[2];
                        Name name = new Name(firstName, lastName, middleName);
                        int age = Integer.parseInt(split[3]);
                        double salary = Double.parseDouble(split[4]);
                        int experience = Integer.parseInt(split[5]);
                        Specialization specialization = Specialization.valueOf(split[6]);
                        Employee employee = new Employee(name, age, salary, experience, specialization);
                        List<Employee> list = this.repository.getOrDefault(specialization, new ArrayList<>());
                        list.add(employee);
                        repository.put(specialization, list);
                    }catch (Exception ignored){

                    }
                }
            }
        }else {
            throw new FileSystemException("Only '.json' or '.csv' extension files qualify.");
        }
    }
}
