package com.qbh.github.rlog.type;

/**
 * @author QBH
 * @date 2022/8/30
 */
public class JoinPointExecuteResult {

  private static final Integer SUCCESS = 1;

  private static final Integer FAIL = 0;

  private Throwable throwable;

  private String errorMsg;

  /** SUCCESS = 1, FAIL = 0 */
  private Integer executeSuccess;

  public JoinPointExecuteResult() {
    this.executeSuccess = SUCCESS;
  }
  public void executeFail(Throwable e) {
    this.errorMsg = e.getMessage();
    this.throwable = e;
    this.executeSuccess = FAIL;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public Integer getExecuteSuccess() {
    return executeSuccess;
  }
}
