package com.example.demo.configuration;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttCallback;

@Component
public class MqttListener {

    @Autowired
    private MqttClient mqttClient;

    @PostConstruct
    public void init() throws MqttException {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void disconnected(MqttDisconnectResponse disconnectResponse) {
                System.out.println("Disconnected: " + disconnectResponse);
            }

            @Override
            public void mqttErrorOccurred(MqttException exception) {
                System.out.println("Error: " + exception.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                handleIncomingMessage(topic, message);
            }

            @Override
            public void deliveryComplete(IMqttToken token) {
                System.out.println("Delivery complete: " + token);
            }

            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                System.out.println("Connected to: " + serverURI);
            }

            @Override
            public void authPacketArrived(int reasonCode, MqttProperties properties) {
                System.out.println("Auth packet arrived: " + reasonCode);
            }
        });

        MqttSubscription[] subscriptions = {new MqttSubscription("test")};
        mqttClient.subscribe(subscriptions);
    }

    public void handleIncomingMessage(String topic, MqttMessage message) {
        System.out.println("Received message from topic " + topic + ": " + new String(message.getPayload()));
    }
}
