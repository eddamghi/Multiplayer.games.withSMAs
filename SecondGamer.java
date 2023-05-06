package org.example;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class SecondGamer extends GuiAgent {
    private SecondGamerGui clientGui;
    @Override
    protected void setup() {
        clientGui = (SecondGamerGui) getArguments()[0];
        clientGui.setAgentClient1(this);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG != null) {
                    clientGui.showMessage("Server"
                            + receivedMSG.getContent());
                } else {
                    block();
                }
            }
        });
    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("server", AID.ISLOCALNAME));
        message.setContent(guiEvent.getParameter(0).toString());
        send(message);
    }
}
