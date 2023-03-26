package AB.Backend.Config;

import AB.Backend.DailyMachines.MachineDaily;
import AB.Backend.HourMachine.MachineHour;
import AB.Backend.ProducedParts.Part;
import AB.Backend.TenMinutesMachine.MachineTenMinutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.convert.translation.JacksonTranslationService;
import org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

/**
 * Configuration, contains the configuration to access couchbase database
 */
@Configuration
public class Config extends AbstractCouchbaseConfiguration {

    private String hourMachineBucket = "hourlyBucket";
    private String tenMinuteBucket = "tenMinuteBucket";
    private String partBucket = "partBucket";
    private String dailyMachineBucket ="dailyBucket";

    @Autowired
    private ApplicationContext context;

    @Override
    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    }

    @Override
    public String getUserName() {
        return "backend";
    }

    @Override
    public String getPassword() {
        return "backend123";
    }

    @Override
    public String getBucketName() {
        return "machineState";
    }

    @Override
    public void configureReactiveRepositoryOperationsMapping(ReactiveRepositoryOperationsMapping baseMapping) {
        try {
            ReactiveCouchbaseTemplate dailyTemplate = myReactiveCouchbaseTemplate(myCouchbaseClientFactory(dailyMachineBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(MachineDaily.class, dailyTemplate);

            ReactiveCouchbaseTemplate hourlyTemplate = myReactiveCouchbaseTemplate(myCouchbaseClientFactory(hourMachineBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(MachineHour.class, hourlyTemplate);

            ReactiveCouchbaseTemplate tenMinuteTemplate = myReactiveCouchbaseTemplate(myCouchbaseClientFactory(tenMinuteBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(MachineTenMinutes.class, tenMinuteTemplate);

            ReactiveCouchbaseTemplate partTemplate = myReactiveCouchbaseTemplate(myCouchbaseClientFactory(partBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(Part.class, partTemplate);


            // everything else goes in getBucketName()
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping baseMapping) {
        try {
            CouchbaseTemplate dailyTemplate = myCouchbaseTemplate(myCouchbaseClientFactory(dailyMachineBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));

            baseMapping.mapEntity(MachineDaily.class, dailyTemplate);



            CouchbaseTemplate hourlyTemplate = myCouchbaseTemplate(myCouchbaseClientFactory(hourMachineBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(MachineHour.class, hourlyTemplate);

            CouchbaseTemplate tenMinuteTemplate = myCouchbaseTemplate(myCouchbaseClientFactory(tenMinuteBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(MachineTenMinutes.class, tenMinuteTemplate);

            CouchbaseTemplate partTemplate = myCouchbaseTemplate(myCouchbaseClientFactory(partBucket),
                    (MappingCouchbaseConverter) (baseMapping.getDefault().getConverter()));
            baseMapping.mapEntity(Part.class, partTemplate);

            // other goes is getBucketName() = machineStates

        } catch (Exception e) {
            throw e;
        }
    }



    // do not use reactiveCouchbaseTemplate for the name of this method, otherwise the value of that bean
// will be used instead of the result of this call (the client factory arg is different)
    public ReactiveCouchbaseTemplate myReactiveCouchbaseTemplate(CouchbaseClientFactory couchbaseClientFactory,
                                                                 MappingCouchbaseConverter mappingCouchbaseConverter) {
        return new ReactiveCouchbaseTemplate(couchbaseClientFactory, mappingCouchbaseConverter,
                new JacksonTranslationService(), getDefaultConsistency());
    }

    // do not use couchbaseTemplate for the name of this method, otherwise the value of that been
// will be used instead of the result from this call (the client factory arg is different)
    public CouchbaseTemplate myCouchbaseTemplate(CouchbaseClientFactory couchbaseClientFactory,
                                                 MappingCouchbaseConverter mappingCouchbaseConverter) {
        return new CouchbaseTemplate(couchbaseClientFactory, mappingCouchbaseConverter, new JacksonTranslationService(),
                getDefaultConsistency());
    }

    // do not use couchbaseClientFactory for the name of this method, otherwise the value of that bean will
// will be used instead of this call being made ( bucketname is an arg here, instead of using bucketName() )
    public CouchbaseClientFactory myCouchbaseClientFactory(String bucketName) {
        return new SimpleCouchbaseClientFactory(getConnectionString(), authenticator(), bucketName);
    }

}
