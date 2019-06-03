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

    private static final String SYSTEM_ERRO_INFO = "系统响应异常.";

    private static final String OPTIMISTIC_LOCKING_FAILURE_INFO = "存在用户同时操作,提交失败,请重试.";

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDTO<String> exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
        return handleExceptionInfo(ResultDTO.EXCEPTION, exception);
    }

    @ExceptionHandler(BusErroException.class)
    @ResponseBody
    public ResultDTO<String> exceptionHandler(HttpServletRequest request, BusErroException exception) throws Exception {
        return handleExceptionInfo(ResultDTO.EXCEPTION, exception);
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
        return handleErroInfo(ResultDTO.ERROR, OPTIMISTIC_LOCKING_FAILURE_INFO, exception);
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
        return handleErroInfo(ResultDTO.ERROR, exception.getShowInfo(), exception);
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
        log.debug("message:" + message + "," + exception.getStackTrace().toString());
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMsg(message);
        return resultDTO;
    }

    /**
     * 处理异常信息
     *
     * @param code
     * @param exception
     * @return
     */
    private ResultDTO<String> handleExceptionInfo(int code, Exception exception) {
        log.error("message:" + exception.getMessage() + "," + exception.getStackTrace().toString());
        ResultDTO<String> resultDTO = new ResultDTO<>();
        resultDTO.setCode(code);
        resultDTO.setMsg(SYSTEM_ERRO_INFO);
        return resultDTO;
    }

}