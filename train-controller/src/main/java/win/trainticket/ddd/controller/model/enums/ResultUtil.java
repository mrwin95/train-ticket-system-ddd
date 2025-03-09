package win.trainticket.ddd.controller.model.enums;

import win.trainticket.ddd.controller.model.vo.ResultMessage;

public class ResultUtil<T> {

    private final ResultMessage<T> responseMessage;
    private static final Integer SUCCESS = 200;

    public ResultUtil() {
        responseMessage = new ResultMessage<>();
        responseMessage.setCode(SUCCESS);
        responseMessage.setSuccess(true);
        responseMessage.setMessage("success");
    }

    public ResultMessage<T> setData(T data) {
        this.responseMessage.setData(data);
        return this.responseMessage;
    }

    public static <T> ResultMessage<T> data(T t) {
        return new ResultUtil<T>().setData(t);
    }
}
