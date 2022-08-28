package com.backend.seasenora.test;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class SpringappApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void BE_Add_User() throws Exception {
        String newProduct = "{\"email\":\"tests@gmail.com\",\"password\":\"Test@18723\",\"username\":\"test18723\",\"mobileNumber\":\"5336543210\",\"role\":\"user\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProduct)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    public void BE_Add_Boat() throws Exception {
        String newProduct = "{\"productId\":\"01\",\"imageUrl\":\"beetroot.com\",\"boatName\":\"beetroot\",\"pricePerDay\":\"35\",\"fuelType\":\"Petrol\",\"PassengersAllowed\":\"30\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProduct)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Transactional
    public void BE_Get_Boats() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/boats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andReturn();
    }

    @Test
    @Transactional
    public void BE_Update_Product() throws Exception {
        String newProduct = "{\"productId\":\"01\",\"imageUrl\":\"beetroot.com\",\"productName\":\"rootBoat\",\"price\":\"3000\",\"fuelType\":\"Disel\",\"passengersAllowed\":\"8\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/admin/productEdit")
                        .param("productId","01")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProduct)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}