# Setup
- Ensure postgres is running and accessible
- Set necessary values for .env (look at .env.template for guidance) and ensure postgres db_url is set correctly
- Ensure application.yml is configured correctly for environment (is able to read .env properly)
# Running locally
- Run `./mvnw spring-boot:run` to start the application
- OR run the `main` method from `OfCourseApplication.java`
# Testing
- Run `./mvnw test` to execute unit tests
- Run `./mvnw verify` to execute integration tests