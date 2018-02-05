@Login
Feature: Test login the CSDN
  Assert the login function

  Scenario: Successful Login the CSDN
    Given 打开CSDN登录页面 "https://passport.csdn.net/account/login?"
    When 输入用户名 "username1" 密码 "password1"
    Then 检查是否登录成功 "登录成功"