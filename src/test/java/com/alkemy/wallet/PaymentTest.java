package com.alkemy.wallet;


import com.alkemy.wallet.controller.TransactionController;
import com.alkemy.wallet.dto.TransactionPaymentDto;
import com.alkemy.wallet.model.Account;
import com.alkemy.wallet.model.Transaction;
import com.alkemy.wallet.model.User;
import com.alkemy.wallet.service.ITransactionService;
import com.alkemy.wallet.util.CurrencyEnum;
import com.alkemy.wallet.util.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ModelAndViewMethodReturnValueHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = WalletApplication.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ITransactionService transactionService;

    @Mock
    Transaction transaction;

    User user = new User("Giu", "Cas", "anonymousUser", "pass");

    Account account = new Account(CurrencyEnum.ARS, Double.valueOf(10000), Double.valueOf(3000), user, Timestamp.valueOf(LocalDateTime.now()));

    String examplePayment = "{\"amount\":\"150\",\"description\":\"hola\",\"accountId\":\"1\",\"userId\":\"1\"}";

    @Test
    public void createTransaction() throws Exception {

        transaction.setId(1);
        transaction.setUser(user);
        transaction.setType(Type.payment);
        transaction.setAccount(account);
        transaction.setDescription("Hola");
        transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));

        Mockito.when(transactionService.savePayment(transaction).getUser()).thenReturn(user);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transactions/payment")
                .accept(MediaType.APPLICATION_JSON).content(examplePayment)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
}
