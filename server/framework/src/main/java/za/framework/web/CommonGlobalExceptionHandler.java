package za.framework.web;

import com.za.common.dto.ResultDTO;
import com.za.common.exceptions.BusErroException;
import com.za.common.exceptions.BusException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CommonGlobalExceptionHandler {

    private static final String SYSTEM_ERROR_INFO = "系统响应异常.";

    private static final String OPTIMISTIC_LOCKING_FAILURE_INFO = "存在用户同时操作,提交失败,请重试.";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDTO<String> exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        return handleExceptionInfo(ResultDTO.EXCEPTION_CODE, exception);
    }

    @ExceptionHandler(BusErroException.class)
    @ResponseBody
    public ResultDTO<String> exceptionHandler(HttpServletRequest request, BusErroException exception) throws Exception {
        return handleExceptionInfo(ResultDTO.EXCEPTION_CODE, exception);
    }

    /**
     * 乐观锁异常
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    @ResponseBody
    public ResultDTO<String> exceptionHandler(HttpServletRequest request, OptimisticLockingFailureException exception) throws Exception {
        return handleErroInfo(ResultDTO.ERROR_CODE, OPTIMISTIC_LOCKING_FAILURE_INFO, exception);
    }

    /**
     * 正常业务抛出信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(BusException.class)
    @ResponseBody
    public ResultDTO<String> exceptionHandler(HttpServletRequest request, BusException exception) throws Exception {
        return handleErroInfo(ResultDTO.ERROR_CODE, exception.getShowInfo(), exception);
    }

    /**
     * 处理异常信息
     *
     * @param code
     * @param message
     * @param exception
     * @return
     */
    private ResultDTO<String> handleErroInfo(int code, String message, Exception exception) {
        log.debug("message:" + message + "," + getStackMsg(exception));
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMsg(message);
        return resultDTO;
    }

    private static String getStackMsg(Throwable e) {

        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString() + "\n");
        }
        return sb.toString();
    }

    /**
     * 处理异常信息
     *
     * @param code
     * @param exception
     * @return
     */
    private ResultDTO<String> handleExceptionInfo(int code, Exception exception) {
        log.error("message:" + exception.getMessage() + "," + getStackMsg(exception));
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMsg(SYSTEM_ERROR_INFO);
        return resultDTO;
    }

}