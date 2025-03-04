public class Request {
    private String message;
    private User sender;
    private String status;
    private String projectName;
    private User projectOwner;

    public Request(String message, String senderUsername, String projectName) {
        this.message = message;
        Database db = new Database();
        db.loadUsers();
        this.sender = db.findUser(senderUsername);
        this.status = "pending";
        this.projectName = projectName;
        db.loadProjects();
        Project project = db.findProject(projectName);
        this.projectOwner  = project.getPoster();
        projectOwner.addReceivedRequest(this);
        sender.addSentRequest(this);
    }

    // getters
    public String getMessage() {
        return message;
    }

    public String checkStatus() {
        return status;
    }

    public User getSender() {
        return sender;
    }

    public User getProjectOwner() {
        return projectOwner;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getStatus() {
        return status;
    }

    public void rejectRequest() {
        this.status = "rejected";
        sender.removeSentRequest(this);
        projectOwner.removeReceivedRequest(this);
    }

    public void acceptRequest() {
        this.status = "accepted";
        this.sender.addProjectOn(projectName);
        projectOwner.removeReceivedRequest(this);
        sender.removeSentRequest(this);
    }

    public String toString() {
        return "" + sender + "," + projectName + "," + message;
    }
}
