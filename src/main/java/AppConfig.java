import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//Defines api root
@ApplicationPath("/rest")
public class AppConfig extends ResourceConfig {
}
