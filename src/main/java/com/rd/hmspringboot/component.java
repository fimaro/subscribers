/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rd.hmspringboot;

import com.google.pubsub.v1.PubsubMessage;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author mfigu
 */
@Component
public final class component {

    public void inicio() {
        start();
    }
    
    
    // use the default project id
  private static final String PROJECT_ID = "pruebasbigquery-218802";

  private static final BlockingQueue<PubsubMessage> messages = new LinkedBlockingDeque<>();

  static class MessageReceiverExample implements MessageReceiver {
       @Override
        public void receiveMessage(PubsubMessage message, AckReplyConsumer arc) {
            messages.offer(message);
            arc.ack();
        }
  }

  /** Receive messages over a subscription. */
  public void start(){
    // set subscriber id, eg. my-sub
    String subscriptionId = "firstSubscription";
    ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(PROJECT_ID, subscriptionId);
    Subscriber subscriber = null;
    try {
      // create a subscriber bound to the asynchronous message receiver
      subscriber =
          Subscriber.newBuilder(subscriptionName, new MessageReceiverExample()).build();
      subscriber.startAsync().awaitRunning();
      // Continue to listen to messages
      while (true) {
        PubsubMessage message = messages.take();
        System.out.println("Message Id: " + message.getMessageId());
        System.out.println("Data: " + message.getData().toStringUtf8());
      }
    }   catch (InterruptedException ex) {
            Logger.getLogger(component.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
      if (subscriber != null) {
        subscriber.stopAsync();
      }
    }
  }
}
