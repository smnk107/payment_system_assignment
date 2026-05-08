package com.smnk107.PaymentService;

import com.smnk107.PaymentService.DTOs.PaymentRequest;
import com.smnk107.PaymentService.DTOs.PaymentResponse;
import com.smnk107.PaymentService.Entities.IdempotencyKey;
import com.smnk107.PaymentService.Entities.Payment;
import com.smnk107.PaymentService.DTOs.PaymentStatus;
import com.smnk107.PaymentService.Repositories.IdempotencyRepository;
import com.smnk107.PaymentService.Repositories.PaymentRepository;
import com.smnk107.PaymentService.Services.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PaymentServiceApplicationTests {



	@Mock
	private PaymentRepository paymentRepository;

	@Mock
	private IdempotencyRepository idempotencyRepository;

	@InjectMocks
	private PaymentService paymentService;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldReturnExistingPayment_whenIdempotencyKeyExists() {
		String key = "abc123";

		IdempotencyKey idempotencyKey = new IdempotencyKey(1L, key, 10L);
		Payment payment = new Payment(10L, 100.0, PaymentStatus.SUCCESS, key, LocalDateTime.now());

		when(idempotencyRepository.findByIdempotencyKey(key))
				.thenReturn(Optional.of(idempotencyKey));

		when(paymentRepository.findById(10L))
				.thenReturn(Optional.of(payment));

		PaymentResponse response = paymentService.initiatePayment(new PaymentRequest(), key);

		assertEquals(10L, response.getPaymentId());
	}

}
