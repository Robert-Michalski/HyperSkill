package antifraud.model.User;

public enum UserRoles {
    ANONYMOUS, MERCHANT, ADMINISTRATOR, SUPPORT;


    @Override
    public String toString() {
        return "ROLE_"+super.toString();
    }
}
