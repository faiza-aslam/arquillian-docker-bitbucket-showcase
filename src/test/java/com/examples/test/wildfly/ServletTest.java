package com.examples.test.wildfly;

import com.examples.type.WildflyTest;
import com.examples.wildfly.servlet.TestServlet;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.assertions.DockerJavaAssertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(Arquillian.class)
@Category(WildflyTest.class)
public class ServletTest {

    @ArquillianResource
    private DockerClient dockerClient;

    @Deployment(testable = false)
    public static WebArchive create() {
        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClass(TestServlet.class);
    }

    @Test
    @InSequence(1)
    public void exposeCorrectPort() {
        DockerJavaAssertions.assertThat(dockerClient).container("wildfly10").hasExposedPorts("8080/tcp");
    }

    @Test
    @InSequence(2)
    public void wildflyRunning() {
        DockerJavaAssertions.assertThat(dockerClient).container("wildfly10").isRunning();
    }

    @Test
    @InSequence(3)
    public void should_parse_and_load_configuration_file(@ArquillianResource URL resource) throws IOException {
        System.out.println("resource: " + resource.toString());
        URL obj = new URL(resource, "TestServlet");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Assert.assertEquals(response.toString(), "Testing Servlet");
    }
}
