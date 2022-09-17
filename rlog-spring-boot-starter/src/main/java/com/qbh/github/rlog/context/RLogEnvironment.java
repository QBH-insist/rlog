package com.qbh.github.rlog.context;

import java.util.Map;

/**
 * @author QBH
 * @date 2022/8/29
 */
public interface RLogEnvironment {

  /**
   * return aviator parse environment
   *
   * @return
   */
  Map<String, Object> getEnvironment();

}
