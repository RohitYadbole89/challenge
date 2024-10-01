package com.dws.challenge;


import com.dws.challenge.config.AsyncConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransferControllerTest {

    @Autowired
    MockMvc mvc;

    /**
     * Test case to add account
     * @throws Exception
     */
    @Test
    @Order(1)
    public void testAddAccount() throws Exception
    {
        //1st account
        this.mvc.perform( MockMvcRequestBuilders
                        .post("/v1/accounts")
                        .content("{\n" +
                                "    \"accountId\":\"1\",\n" +
                                "    \"balance\":\"100\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //2nd account
        this.mvc.perform( MockMvcRequestBuilders
                        .post("/v1/accounts")
                        .content("{\n" +
                                "    \"accountId\":\"2\",\n" +
                                "    \"balance\":\"100\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mvc.perform( MockMvcRequestBuilders
                        .post("/v1/transfer")
                        .content("{\n" +
                                "    \"fromAccountId\":\"1\",\n" +
                                "    \"toAccountId\":\"2\",\n" +
                                "    \"amount\":\"50\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test case to test Negative amount transfer
     * @throws Exception
     */
    @Test
    @Order(3)
    public void testNegativeBalanceTransfer() throws Exception
    {

        //1st account
        this.mvc.perform( MockMvcRequestBuilders
                        .post("/v1/accounts")
                        .content("{\n" +
                                "    \"accountId\":\"1\",\n" +
                                "    \"balance\":\"100\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        //2nd account
        this.mvc.perform( MockMvcRequestBuilders
                        .post("/v1/accounts")
                        .content("{\n" +
                                "    \"accountId\":\"2\",\n" +
                                "    \"balance\":\"100\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mvc.perform( MockMvcRequestBuilders
                        .post("/v1/transfer")
                        .content("{\n" +
                                "    \"fromAccountId\":\"1\",\n" +
                                "    \"toAccountId\":\"2\",\n" +
                                "    \"amount\":\"-50\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
