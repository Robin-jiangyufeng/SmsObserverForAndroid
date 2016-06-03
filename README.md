# 项目介绍
### 项目地址:
   [SmsObserverForAndroid](https://github.com/Robin-jiangyufeng/SmsObserverForAndroid) 
   
### 介绍
   * 这是一个用于拦截android实时短信的库,可以进行短信过滤,得到自己想要的内容,可以用于需要自动填写短信验证码的app项目
   
### 功能
   * 用于监听当前接收到的短信信息
   * 过滤接收到的短信，得到自己想要的内容
   
### 使用场景
   * 可以用于自动填写短信验证码的app项目
   * 自己想...
   
# 使用方法
### 所需权限
```java
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.READ_SMS" />
```
### 初始化
```java
    /***
     * 构造器
     * @param context
     * @param callback 短信接收器
     * @param smsFilter 短信过滤器
     */
     SmsObserver smsObserver=new SmsObserver(this, new SmsResponseCallback() {
                 @Override
                 public void onCallbackSmsContent(String smsContent) {
                     //这里接收短信
                 }
             }, new VerificationCodeSmsFilter("180"));
```
### 注册短信变化监听器
   * 在注册监听器以后,短信观察者就已经启动短信变化监听,接下只要接收短信,对短信做处理就可以了
   
```java
    /***
     * 注册短信变化观察者
     *
     * @see [类、类#方法、类#成员]
     */
   smsObserver.registerSMSObserver();
```
### 注销短信变化监听器
  * 在不需要再使用短信接收功能的时候,请注销短信监听器,不然后续还是可以接收得到短信
  
```java
    /***
     * 注销短信变化观察者
     *
     * @see [类、类#方法、类#成员]
     */
   smsObserver.unregisterSMSObserver();
```

### 短信过滤器
  * 要接收到自己想要的短信内容只要实现自己的SmsFilter短信过滤器即可

```java
/**
 * 短信验证码过滤器
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/6/2]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class VerificationCodeSmsFilter implements SmsFilter {
    /**
     * 需要过滤的发短信的人
     */
    private String filterAddress;

    public VerificationCodeSmsFilter(String filterAddress) {
        this.filterAddress = filterAddress;
    }

    @Override
    public String filter(String address, String smsContent) {
        if (address.startsWith(filterAddress)) {
            Pattern pattern = Pattern.compile("(\\d{4,8})");//匹配4-8位的数字
            Matcher matcher = pattern.matcher(smsContent);
            if (matcher.find()) {
                return matcher.group(0);
            }
        }
        return null;
    }
}
```

# 关于作者Robin
* 屌丝程序员
* GitHub: [Robin-jiangyufeng](https://github.com/Robin-jiangyufeng)
* QQ:429257411
* 交流QQ群 236395044