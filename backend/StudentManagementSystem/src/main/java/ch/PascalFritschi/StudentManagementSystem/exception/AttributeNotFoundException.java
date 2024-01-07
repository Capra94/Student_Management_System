package ch.PascalFritschi.StudentManagementSystem.exception;

public class AttributeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public AttributeNotFoundException(String message) {
        super(message);
    }
}