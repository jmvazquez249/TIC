package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AvionRestService {
    private RestTemplate restTemplate;

    @Autowired
    private AvionRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}


}
