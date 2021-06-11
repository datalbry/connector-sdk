plugins {
    id("connector-development")
    ...
}

connector {
    /*
      Name and Version of the connector, defaults to project.name and project.version
     */
    name = ""
    version = ""

    /*
      The path to lookup the schema files.
      OPTIONAL, below values are the default ones
      (!) The path is relative to the build directory
     */
    configSchemaPath = "./resources/main/META-INF/datalbry/schema-config.json"
    documentSchemaPath = "./resources/main/META-INF/datalbry/schema.json"

    /*
      Keycloak configuration properties,
      Required because our primary connector registry is hidden behind a Keycloak authentication proxy
     */
    keycloak {
        /*
          Defaults to login.datalbry.io/auth/internal
         */
        url = ""
        /*
          The username and password to authenticate with against the keycloak instance.
          If the values are not set, the Plugin checks the environment variables KEYCLOAK_USER, KEYCLOAK_PASSWORD
         */
        user = ""
        password = ""
    }

    registry {
        url = ""
    }

    /*

     */
    docker {
        url = ""
        user = ""
        password = ""
    }
}
