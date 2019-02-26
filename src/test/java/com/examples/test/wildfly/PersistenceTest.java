package com.examples.test.wildfly;

import com.examples.type.WildflyTest;
import com.examples.wildfly.dto.UserDto;
import com.examples.wildfly.service.UserService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

@RunWith(Arquillian.class)
@Category(WildflyTest.class)
public class PersistenceTest {

    @Inject
    private UserService userService;

    @Deployment
    public static Archive<?> createDeployment() {
        WebArchive war = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackages(true, "com.examples.wildfly")
                .addClasses(WildflyTest.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
        System.out.println("war contents: "+war.toString(true));

        return war;
    }

    @Test
    @InSequence(1)
    public void addUser() {
        userService.add(new UserDto("Test", 20));
    }

    @Test
    @InSequence(2)
    public void getAllUsers() {
        List<UserDto> users = userService.getAll();
        Assert.assertNotNull(users);
        System.out.println("Users size: "+users.size());
        Assert.assertEquals("List size should be 1", 1, users.size());
    }
}
