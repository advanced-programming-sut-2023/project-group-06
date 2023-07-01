package org.example.Model;

public class FriendRequest {
    private User sender;
    private User target;
    private boolean isAccepted = false;

    public FriendRequest(User sender, User target) {
        this.sender = sender;
        this.target = target;
    }

    public User getSender() {
        return sender;
    }

    public User getTarget() {
        return target;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
