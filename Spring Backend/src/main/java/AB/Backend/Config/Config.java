package AB.Backend.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;


@Configuration
public class Config extends AbstractCouchbaseConfiguration {


    @Autowired
    private ApplicationContext context;

    @Override
    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    }

    @Override
    public String getUserName() {
        return "admin";
    }

    @Override
    public String getPassword() {
        return "abc123";
    }

    @Override
    public String getBucketName() {
        return "machineState";
    }


}
