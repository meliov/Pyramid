package demo1.bank;



import bank.service.BankService;
import bank.service.TransactionContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankServiceTest {
    @Autowired
    private BankService bankService;



    @Test
    public void createAccountTest(){
        bankService.createAccount();
        bankService.createAccount();
        bankService.createAccount();
    }

    @Test
    public void payTest(){

        bankService.payTax(2L, BigDecimal.valueOf(300));

    }
    @Test
    public void multiTransferTest(){
        bankService.transfer(TransactionContext.of( 1L, 2L, BigDecimal.valueOf(50)), TransactionContext.of(1L, 3L, BigDecimal.valueOf(50) ));
        bankService.transfer(TransactionContext.of( 1L, 2L, BigDecimal.valueOf(50)), TransactionContext.of(1L, 3L, BigDecimal.valueOf(50) ));
    }
    @Test
    public void reverseTest(){
        bankService.reverse(1L);
    }


}
