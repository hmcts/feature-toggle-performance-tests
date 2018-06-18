# Feature Toggle API performance tests
Performance tests for [feature-toggle-api](https://github.com/hmcts/feature-toggle-api).

## Configuration
By default, configuration points to a locally running feature-toggle-api.  
The url (along with other settings) can be changed in the [application.conf](src/gatling/resources/conf/application.conf) file
(or by setting corresponding environment variables).

## Running
```bash
$ ./gradlew gatlingRun-uk.gov.hmcts.reform.featuretoggle.MainSimulation
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
