package ServiceImplementation;

import Repository.Repository;
import Service.Service;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;
import Functions.Functions;

import java.util.Map;

public class ServiceImpl extends Service {

    private final CosmosContainer container = new Functions().getContainer();

    public ServiceImpl(Repository repository) {
        super(repository);
    }

    public void save(Map<String, Object> data) {
        CosmosItemResponse<Object> response = container.createItem(data);
    }
}
