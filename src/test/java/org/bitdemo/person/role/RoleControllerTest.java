package org.bitdemo.person.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.util.HashMap;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoleControllerTest {

    @Value(value="${local.server.port}")
    private int port;


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getRolesEmpty() throws Exception {
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/get-roles",
                String.class)).isEqualTo("[]");
    }

    @Test
    public void insertRole() throws Exception {

        // Insertion
        URI uri = new URI("http://localhost:" + port + "/insert-role");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<>("roleName=Tester", headers);
        String expected = "{\"id\":1,\"name\":\"Tester\",\"users\":[]}";
        assertThat(restTemplate.postForObject( uri, httpEntity, String.class )).isEqualTo(expected);

        // Retrieval
        String url = "http://localhost:" + port + "/get-roles";
        assertThat(restTemplate.getForObject(url, String.class)).isEqualTo("["+expected+"]");
    }


    @Test
    public void retrieveRoles() throws Exception {

        // Insertion 1
        URI uri = new URI("http://localhost:" + port + "/insert-role");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<>("roleName=Tester", headers);
        String expected = "{\"id\":1,\"name\":\"Tester\",\"users\":[]}";
        assertThat(restTemplate.postForObject( uri, httpEntity, String.class )).isEqualTo(expected);

        // Insertion 2
        HttpEntity<String> httpEntity2 = new HttpEntity<>("roleName=Builder", headers);
        String expected2 = "{\"id\":2,\"name\":\"Builder\",\"users\":[]}";
        assertThat(restTemplate.postForObject( uri, httpEntity2, String.class )).isEqualTo(expected2);

        // Retrieval
        String url = "http://localhost:" + port + "/get-roles";
        String res = "[{\"id\":2,\"name\":\"Builder\",\"users\":[]},{\"id\":1,\"name\":\"Tester\",\"users\":[]}]";
        assertThat(restTemplate.getForObject(url, String.class)).isEqualTo(res);
    }

}