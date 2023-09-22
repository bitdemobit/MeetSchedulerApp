package org.bitdemo.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void gePersonsEmpty() throws Exception {
        this.mockMvc.perform(get("/get-persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


    @Test
    public void insertPerson() throws Exception {

        String expected = "{\"id\":1,\"name\":\"Person1\",\"roles\":[],\"slots\":[]}";
        this.mockMvc.perform(post("/insert-person")
                        .param("name", "Person1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }



    @Test
    public void retrievePersons() throws Exception {

        // Insertion 1
        String expected = "{\"id\":1,\"name\":\"Person1\",\"roles\":[],\"slots\":[]}";
        this.mockMvc.perform(post("/insert-person")
                        .param("name", "Person1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        // Insertion 2
        String expected2 = "{\"id\":2,\"name\":\"Person2\",\"roles\":[],\"slots\":[]}";
        this.mockMvc.perform(post("/insert-person")
                        .param("name", "Person2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected2));

        // Retrieve
        String res = "[{\"id\":1,\"name\":\"Person1\",\"roles\":[],\"slots\":[]}," +
                      "{\"id\":2,\"name\":\"Person2\",\"roles\":[],\"slots\":[]}]";
        this.mockMvc.perform(get("/get-persons"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(res));

    }



    @Test
    public void insertPersonAvailability() throws Exception {

        // Insert non existent Person1 and non existent availability
        String expected = "OK";
        this.mockMvc.perform(post("/insert-person-availability")
                        .param("name", "Person1")
                        .param("start", "2011-12-03T10:00:00+01:00")
                        .param("finish", "2011-12-03T11:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        // Insert non existent Person2 and non existent availability
        String expected2 = "OK";
        this.mockMvc.perform(post("/insert-person-availability")
                        .param("name", "Person1")
                        .param("start", "2011-12-03T11:00:00+01:00")
                        .param("finish", "2011-12-03T12:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected2));

    }

    @Test
    public void insertPersonAvailabilityExistentPerson() throws Exception {

        // Insert Person1
        String expected = "{\"id\":1,\"name\":\"Person1\",\"roles\":[],\"slots\":[]}";
        this.mockMvc.perform(post("/insert-person")
                        .param("name", "Person1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        // Insert existent Person1 with non existent slot
        String expected2 = "OK";
        this.mockMvc.perform(post("/insert-person-availability")
                        .param("name", "Person1")
                        .param("start", "2011-12-03T10:00:00+01:00")
                        .param("finish", "2011-12-03T11:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected2));

    }



    @Test
    public void insertPersonRole() throws Exception {

        String expected = "OK";
        this.mockMvc.perform(post("/insert-person-role")
                        .param("name", "Person1")
                        .param("name", "Tester"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }



    @Test
    public void insertPersonAvailabilityExistentSlot() throws Exception {

        // Insert slot
        String expected = "{\"id\":1,\"start\":\"2011-12-03T10:00:00+01:00\"" +
                ",\"finish\":\"2011-12-03T11:00:00+01:00\",\"people\":null}";
        this.mockMvc.perform(post("/insert-slot")
                        .param("start", "2011-12-03T10:00:00+01:00")
                        .param("finish", "2011-12-03T11:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));

        // Insert non existent Person1 with existent slot
        String expected2 = "OK";
        this.mockMvc.perform(post("/insert-person-availability")
                        .param("name", "Person1")
                        .param("start", "2011-12-03T10:00:00+01:00")
                        .param("finish", "2011-12-03T11:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected2));

    }


    @Test
    public void getPersonsOverlaps() throws Exception {

        // Insert three people: Person1, Person2, Person3
        this.mockMvc.perform(post("/insert-person").param("name", "Person1"));
        this.mockMvc.perform(post("/insert-person").param("name", "Person2"));
        this.mockMvc.perform(post("/insert-person").param("name", "Person3"));

        // Insert availability for the three people
        // Person1 10-11h and 11-12h
        this.mockMvc.perform(post("/insert-person-availability")
                                                .param("name", "Person1")
                                                .param("start", "2011-12-03T10:00:00+01:00")
                                                .param("finish", "2011-12-03T11:00:00+01:00"));
        this.mockMvc.perform(post("/insert-person-availability")
                                                .param("name", "Person1")
                                                .param("start", "2011-12-03T11:00:00+01:00")
                                                .param("finish", "2011-12-03T12:00:00+01:00"));

        // Person2 11-12h and 12-13h
        this.mockMvc.perform(post("/insert-person-availability")
                                                .param("name", "Person2")
                                                .param("start", "2011-12-03T11:00:00+01:00")
                                                .param("finish", "2011-12-03T12:00:00+01:00"));
        this.mockMvc.perform(post("/insert-person-availability")
                                                .param("name", "Person2")
                                                .param("start", "2011-12-03T12:00:00+01:00")
                                                .param("finish", "2011-12-03T13:00:00+01:00"));

        // Person3 11-12h and 12-13h
        this.mockMvc.perform(post("/insert-person-availability")
                                                .param("name", "Person3")
                                                .param("start", "2011-12-03T11:00:00+01:00")
                                                .param("finish", "2011-12-03T12:00:00+01:00"));
        this.mockMvc.perform(post("/insert-person-availability")
                                                .param("name", "Person3")
                                                .param("start", "2011-12-03T12:00:00+01:00")
                                                .param("finish", "2011-12-03T13:00:00+01:00"));

        // Retrieve overlaps expecting 11-12h
        String res = "[{\"id\":0,\"start\":\"2011-12-03T11:00:00+01:00\"" +
                                ",\"finish\":\"2011-12-03T12:00:00+01:00\",\"people\":[]}]";
        this.mockMvc.perform(get("/get-persons-overlaps")
                            .param("names", "Person1", "Person2", "Person3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(res));

    }







    @Test
    public void getPersonsOverlapsEmpty() throws Exception {

        // Insert three people: Person1, Person2, Person3
        this.mockMvc.perform(post("/insert-person").param("name", "Person1"));
        this.mockMvc.perform(post("/insert-person").param("name", "Person2"));
        this.mockMvc.perform(post("/insert-person").param("name", "Person3"));

        // Insert availability for the three people
        // Person1 10-11h and 11-12h
        this.mockMvc.perform(post("/insert-person-availability")
                .param("name", "Person1")
                .param("start", "2011-12-03T10:00:00+01:00")
                .param("finish", "2011-12-03T11:00:00+01:00"));
        this.mockMvc.perform(post("/insert-person-availability")
                .param("name", "Person1")
                .param("start", "2011-12-03T11:00:00+01:00")
                .param("finish", "2011-12-03T12:00:00+01:00"));

        // Person2 12-13h and 13-14h
        this.mockMvc.perform(post("/insert-person-availability")
                .param("name", "Person2")
                .param("start", "2011-12-03T12:00:00+01:00")
                .param("finish", "2011-12-03T13:00:00+01:00"));
        this.mockMvc.perform(post("/insert-person-availability")
                .param("name", "Person2")
                .param("start", "2011-12-03T13:00:00+01:00")
                .param("finish", "2011-12-03T14:00:00+01:00"));

        // Person3 10-11h and 14-15h
        this.mockMvc.perform(post("/insert-person-availability")
                .param("name", "Person3")
                .param("start", "2011-12-03T10:00:00+01:00")
                .param("finish", "2011-12-03T11:00:00+01:00"));
        this.mockMvc.perform(post("/insert-person-availability")
                .param("name", "Person3")
                .param("start", "2011-12-03T14:00:00+01:00")
                .param("finish", "2011-12-03T15:00:00+01:00"));

        // Retrieve overlaps - expecting no overlaps
        String res = "[]";
        this.mockMvc.perform(get("/get-persons-overlaps")
                        .param("names", "Person1", "Person2", "Person3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(res));

    }










}