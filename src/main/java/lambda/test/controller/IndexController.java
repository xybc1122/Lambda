package lambda.test.controller;


import com.google.common.util.concurrent.RateLimiter;
import lambda.test.customize.Limiting;
import lambda.test.service.OredeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * 使用RateLimiter  使用令牌桶方式限流
 **/
@RestController
public class IndexController {
    @Autowired
    private OredeService oredeService;
    // 令牌桶  create 方法传入一个参数 以美妙速率1r/s 传入一个token
    RateLimiter rateLimiter = RateLimiter.create(1);

    @RequestMapping("/add")
    public String addOrder() {
        //正常的是要把这段代码放到网关里去
        //如果在500秒没拿到token 走服务降级
        boolean tryAcquire = rateLimiter.tryAcquire(500, TimeUnit.MICROSECONDS);
        if (!tryAcquire) {
            return "一直等待!";
        }
        //限流正常放在网关
        boolean result = oredeService.addOrder();
        if (result) {
            return "抢购成功";
        }
        return "抢购失败";
    }


    @RequestMapping("/a")
    public String add() {
        return "成功~!";
    }

    @Limiting(premitsPerSecond = 1.0, timeout = 500)
    @RequestMapping("/find")
    public String findIndex() {
        System.out.println("findIndex" + System.currentTimeMillis());
        return "findIndex" + System.currentTimeMillis();

    }



    /*
     * 将时间转换为时间戳
     */
    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = df.parse("2018-8-20");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long timestamp = cal.getTimeInMillis();
        System.out.println(timestamp);

    }
}
