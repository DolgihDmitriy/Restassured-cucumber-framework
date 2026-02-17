package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;
    ResponseSpecification respSpec;
    public RequestSpecification requestSpecification() throws IOException {
        if(req == null){
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalProperties("baseURI")).addQueryParam("key","qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public ResponseSpecification responseSpecification(){
        respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return respSpec;
    }

    public static String getGlobalProperties(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\QA\\APIFramework\\src\\test\\java\\resources\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String extractDataFromResponseByKey(String key, Response response){
        String responseBody = response.asString();
        JsonPath js = new JsonPath(responseBody);
        return js.get(key).toString();
    }
}
