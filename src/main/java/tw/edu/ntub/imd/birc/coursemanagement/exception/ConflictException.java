package tw.edu.ntub.imd.birc.coursemanagement.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class ConflictException extends ProjectException {
    public ConflictException(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return "Conflict";
    }
}
