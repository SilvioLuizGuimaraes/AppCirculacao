package circulacao.control.seguranca;
import com.sun.jersey.spi.container.ContainerResponse;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class CORSFilter implements ContainerResponseFilter{

	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHttpHeaders().add("Access-Control-Allow-Headers", "CRRF-Token, X-Requested-By, Authorization, Content-Type");
		response.getHttpHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHttpHeaders().add("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS, HEAD");
		
		return response;
	}
	
}
