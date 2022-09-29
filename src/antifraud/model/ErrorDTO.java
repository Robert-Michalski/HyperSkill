package antifraud.model;

public class ErrorDTO extends RuntimeException{
    public ErrorDTO(String error){
        super(error);
    }
}
