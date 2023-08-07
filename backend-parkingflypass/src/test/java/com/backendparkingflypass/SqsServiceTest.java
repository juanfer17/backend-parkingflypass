package com.backendparkingflypass;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.auth.BasicAWSCredentials;
import com.backendparkingflypass.config.AWSClient;
import com.backendparkingflypass.service.SqsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class SqsServiceTest {

    @Mock
    private AmazonSQS amazonSQS;

    @InjectMocks
    private SqsService sqsService;
    @Test
    public void testGetMessages() {
        // Create a mock AmazonSQS object
        AmazonSQS mockAmazonSQS = Mockito.mock(AmazonSQS.class);

        // Configure the mock to return a list of messages
        List<Message> messages = new ArrayList<>();
        messages.add(new Message());
        messages.add(new Message());
        Mockito.when(mockAmazonSQS.receiveMessage((ReceiveMessageRequest) Mockito.any())).thenReturn((ReceiveMessageResult) messages);
        // Call the getMessages() method
        List<Message> actualMessages = sqsService.getMessages("queueUrl");

        // Assert that the expected messages were returned
        assertEquals(messages, actualMessages);
    }

    @Test
    public void testGetMessagesCount() {
        // Create a mock AmazonSQS object
        AmazonSQS mockAmazonSQS = Mockito.mock(AmazonSQS.class);

        // Configure the mock to return the number of messages
        int messagesCount = 2;
        // Call the getMessagesCount() method
        int actualMessagesCount = sqsService.getMessagesCount("queueUrl");

        // Assert that the expected messages count was returned
        assertEquals(messagesCount, actualMessagesCount);
    }

    @Test
    public void testDeleteMessages() {
        // Create a mock AmazonSQS object
        AmazonSQS mockAmazonSQS = Mockito.mock(AmazonSQS.class);

        // Configure the mock to delete the messages
        Mockito.doNothing().when(mockAmazonSQS).deleteMessageBatch(Mockito.any());
        // Call the deleteMessages() method
        sqsService.deleteMessages(new ArrayList<>(), "queueUrl");

        // Verify that the mock was called
        Mockito.verify(mockAmazonSQS, Mockito.times(1)).deleteMessageBatch(Mockito.any());
    }
}
