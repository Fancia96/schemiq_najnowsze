package project.schemiq.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
//class MyExceptionHandler {
//    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
//    public void handleConflict() {
//        // Nothing to do
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" BŁĄD 400 WYSKOCZYL ");
//    }
//}

//to bylo dobre
//@ControllerAdvice
//public class MyExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value
//            = { ObjectNotFoundException.class, IllegalStateException.class })
//    protected ResponseEntity<Object> handleConflict(
//            RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "Wyszkoczył mi tu błąd 400 badrequest";
//        HashMap<String, String> errors = new HashMap<>();
//            errors.put("NOT_FOUND", ((ObjectNotFoundException) ex).getEntityName());
//
//        return handleExceptionInternal(ex, errors,
//                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//}

@RestControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleThis(RuntimeException runtimeException){
        String excMsg = "Exception on request. Exception message" + runtimeException.getLocalizedMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excMsg);
    }

}

//@RestControllerAdvice
//class MyExceptionHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> exception(Exception ex){
//        if(ex instanceof ObjectNotFoundException){
//
//            HashMap<String, String> errors = new HashMap<>();
//            errors.put("NOT_FOUND", ((ObjectNotFoundException) ex).getEntityName());
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
//        }
//        else if(ex instanceof DuplicateKeyException){
//            HashMap<String, String> errors = new HashMap<>();
//            errors.put("BAD_REQUEST", ex.getMessage());
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
//}
