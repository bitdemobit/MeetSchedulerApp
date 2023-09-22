package org.bitdemo.slot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SlotTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getSlotsEmpty() throws Exception {
        this.mockMvc.perform(get("/get-slots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void insertSlot() throws Exception {

        String expected = "{\"id\":1,\"start\":\"2011-12-03T10:00:00+01:00\"" +
                                    ",\"finish\":\"2011-12-03T11:00:00+01:00\",\"people\":null}";

        this.mockMvc.perform(post("/insert-slot")
                        .param("start", "2011-12-03T10:00:00+01:00")
                        .param("finish", "2011-12-03T11:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void retrieveSlots() throws Exception {

        // Insertion 1
        String expected1 = "{\"id\":1,\"start\":\"2011-12-03T10:00:00+01:00\"" +
                                    ",\"finish\":\"2011-12-03T11:00:00+01:00\",\"people\":null}";
        this.mockMvc.perform(post("/insert-slot")
                        .param("start", "2011-12-03T10:00:00+01:00")
                        .param("finish", "2011-12-03T11:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected1));

        // Insertion 2
        String expected2 = "{\"id\":2,\"start\":\"2011-12-03T11:00:00+01:00\"" +
                                    ",\"finish\":\"2011-12-03T12:00:00+01:00\",\"people\":null}";
        this.mockMvc.perform(post("/insert-slot")
                        .param("start", "2011-12-03T11:00:00+01:00")
                        .param("finish", "2011-12-03T12:00:00+01:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expected2));

        // Retrieve
        String res = "[{\"id\":1,\"start\":\"2011-12-03T10:00:00+01:00\"" +
                                ",\"finish\":\"2011-12-03T11:00:00+01:00\",\"people\":[]}" +
                                ",{\"id\":2,\"start\":\"2011-12-03T11:00:00+01:00\"" +
                                ",\"finish\":\"2011-12-03T12:00:00+01:00\",\"people\":[]}]";
        this.mockMvc.perform(get("/get-slots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(res));

    }

}