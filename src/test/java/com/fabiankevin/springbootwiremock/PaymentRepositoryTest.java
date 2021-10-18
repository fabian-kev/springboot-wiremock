package com.fabiankevin.springbootwiremock;

import com.fabiankevin.springbootwiremock.model.entity.PaymentEntity;
import com.fabiankevin.springbootwiremock.repository.PaymentRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Payment Repository")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentRepositoryTest implements MysqlTestContainer {
    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("Save 3 transactions")
    @Order(1)
    public void save3Transaction(){
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        List<PaymentEntity> paymentEntityList = Arrays.asList(
                PaymentEntity.builder()
                        .amount(random.nextDouble())
                        .transactionDate(LocalDateTime.now().minusHours(randomInt))
                        .userId(random.nextLong())
                        .build(),
                PaymentEntity.builder()
                        .amount(random.nextDouble())
                        .transactionDate(LocalDateTime.now().minusHours(randomInt))
                        .userId(random.nextLong())
                        .build(),
                PaymentEntity.builder()
                        .amount(random.nextDouble())
                        .transactionDate(LocalDateTime.now().minusHours(randomInt))
                        .userId(random.nextLong())
                        .build()
        );
        List<PaymentEntity> paymentEntities = paymentRepository.saveAll(paymentEntityList);
        assertThat(paymentEntities.size()).isEqualTo(paymentEntityList.size());

    }

    @Order(2)
    @Test
    @DisplayName("Find all should return 3 transactions and each of them must have the current date")
    public void findAllShouldEqualToExpectedSize(){
        List<PaymentEntity> entities = paymentRepository.findAll();
        assertThat(entities.size()).isEqualTo(3);
        entities.forEach(transaction -> assertThat(transaction.getCreatedAt().toLocalDate()).isEqualTo(LocalDate.now()));
    }

    @Test
    @Order(3)
    @DisplayName("Update the amount of the transaction id 2 by 5000")
    public void updateTransactionNo2(){
        double updateAmount = 5000.0;
        PaymentEntity paymentEntity = paymentRepository.findById(2l).get();
        paymentEntity.setAmount(updateAmount);
        PaymentEntity updatedPaymentEntity = paymentRepository.save(paymentEntity);
        assertThat(updatedPaymentEntity.getAmount()).isEqualTo(updateAmount);
    }

    @Test
    @Order(4)
    @DisplayName("Delete all transactions")
    public void deleteTransactionNo2(){
        paymentRepository.deleteAll();
        assertThat(paymentRepository.findAll().size()).isEqualTo(0);
    }


}
