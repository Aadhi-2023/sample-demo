package Service;

import Repository.Repository;

import java.util.Map;

public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void saveData(Map<String, Object> data) {
        // Add any business logic if required
        repository.save(data);
    }
}
