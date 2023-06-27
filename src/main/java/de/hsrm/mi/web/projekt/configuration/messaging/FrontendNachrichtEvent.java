package de.hsrm.mi.web.projekt.configuration.messaging;

public class FrontendNachrichtEvent {
    private Nachrichtentyp Nachrichtentyp;
    private String id;
    private Operation operation;

    public FrontendNachrichtEvent() {
    }

    public FrontendNachrichtEvent(Nachrichtentyp Nachrichtentyp, String id, Operation operation) {
        this.Nachrichtentyp = Nachrichtentyp;
        this.id = id;
        this.operation = operation;
    }

    public Nachrichtentyp getNachrichtentyp() {
        return Nachrichtentyp;
    }

    public void setNachrichtentyp(Nachrichtentyp eventType) {
        this.Nachrichtentyp = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
