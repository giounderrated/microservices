package com.example.customer;
import com.example.amqp.RabbitMQMessageProducer;
import com.example.clients.fraud.FraudCheckResponse;
import com.example.clients.fraud.FraudClient;
//import com.example.clients.notification.NotificationClient;
import com.example.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public record CustomerService(
        CustomerRepository repository,
//        RestTemplate template,
        FraudClient fraudClient,
//        NotificationClient notificationClient,
        RabbitMQMessageProducer messageProducer
) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        final Customer customer = Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .build();
        // todo: check if email is valid
        // todo check if email is not taken
        repository.save(customer);

        //the next lines are changed because of the implementation of OPENFEIGN
//        FraudCheckResponse response =   template.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );
        FraudCheckResponse response = fraudClient.isFraudster(customer.getId());
        assert response != null;
        if(response.isFraudster()){
            throw new IllegalStateException("Is fraudster");
        }
        // todo send notification
        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, from gio.",customer.getFirstname()));
//        notificationClient.sendNotification(notificationRequest);
        //adding to the queue
        messageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

}
