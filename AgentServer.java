package org.example;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.util.HashSet;
import java.util.Set;


public class AgentServer extends GuiAgent {
    private AgentServerGui agentServerGui;
    @Override
    protected void setup() {
        agentServerGui = (AgentServerGui) getArguments()[0];
        int magicNumber;
        magicNumber = (int) (Math.random() * 100 + 1);
        Set<AID> clients = new HashSet<>();

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG != null){
                    agentServerGui.showMessage(receivedMSG.getSender().getLocalName() + " : " + receivedMSG.getContent());
                    ACLMessage messageResponse = new ACLMessage(ACLMessage.INFORM);
                    messageResponse.addReceiver(receivedMSG.getSender());

                    clients.add(receivedMSG.getSender());
                    System.out.println(clients.size());

                    int numberClient = Integer.parseInt(receivedMSG.getContent());
                    if(numberClient == magicNumber) {
                        messageResponse.setContent("  yikes!");
                        clients.remove(receivedMSG.getSender());

                        for(AID client : clients) {
                            if(client != receivedMSG.getSender()){
                                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                                System.out.println(receivedMSG.getSender().getLocalName());
                                message.addReceiver(client);
                                System.out.println(client);
                                message.setContent("" + receivedMSG.getSender().getLocalName() +" " + "  wins");
                                send(message);
                            }
                        }
                    }
                if(numberClient < magicNumber){
                    messageResponse.setContent("  Try higher!");
                }else if (numberClient > magicNumber){
                    messageResponse.setContent("  Try smaller!");
                }
                send(messageResponse);
                }else{
                    block();
                }

            }}
        );

    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("client",AID.ISLOCALNAME));
        message.setContent(guiEvent.getParameter(0).toString());
        send(message);
    }
}
