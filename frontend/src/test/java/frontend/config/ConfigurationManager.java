package frontend.config;

import java.io.*;
import java.util.Properties;

public class ConfigurationManager {
    private final Properties properties;

    public ConfigurationManager() {
        properties = new Properties();
        try {
            //InputStream props = new FileInputStream("resources/config.properties"); el resources folder esta a la misma altura que src
            InputStream props = new FileInputStream("/Users/carbaaid/Documents/projects/intro_to_automation_in_testing-aida/frontend/src/test/resources/config.properties");
            properties.load(props);
        } catch (Error | IOException e) {
            e.printStackTrace();
        }
    }


    public String getBaseUrl() {
        return properties.get("base.url").toString();
    }

    public String getBrowser() {
        return properties.get("browser").toString();
    }

}
