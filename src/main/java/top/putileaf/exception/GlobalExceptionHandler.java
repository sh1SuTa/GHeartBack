package top.putileaf.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.putileaf.pojo.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public Result handleConstraintViolationException(ConstraintViolationException e) {
//        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//        StringBuilder errorMessage = new StringBuilder("请输入完整的账号密码：");
//        for (ConstraintViolation<?> violation : violations) {
//            errorMessage.append(violation.getMessage()).append("; ");
//        }
        return Result.error("请输入完整的账号密码");
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        BindingResult result = e.getBindingResult();
//        StringBuilder errorMessage = new StringBuilder("请输入完整的账号密码：");
//        for (FieldError fieldError : result.getFieldErrors()) {
//            errorMessage.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append("; ");
//        }
//        return Result.error(errorMessage.toString());
//    }

    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        e.printStackTrace();
        return Result.error( StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败" );
    }
}
