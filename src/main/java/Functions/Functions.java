package Functions;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemResponse;

public class Functions {
    private static final String COSMOS_DB_URI = System.getenv("COSMOS_DB_URI");
    private static final String COSMOS_DB_KEY = System.getenv("COSMOS_DB_KEY");
    private static final String DATABASE_NAME = "YourDatabaseName";
    private static final String CONTAINER_NAME = "YourContainerName";

    private CosmosClient cosmosClient;
    private CosmosContainer container;

    public Functions() {
        this.cosmosClient = new CosmosClientBuilder()
                .endpoint(COSMOS_DB_URI)
                .key(COSMOS_DB_KEY)
                .buildClient();
        this.container = cosmosClient.getDatabase(DATABASE_NAME).getContainer(CONTAINER_NAME);
    }

    public CosmosContainer getContainer() {
        return container;
    }
}
