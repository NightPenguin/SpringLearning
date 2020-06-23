package com.springboot.aoplog.aspect;

import com.springboot.aoplog.annotation.Log;
import com.springboot.aoplog.entity.SysLog;
import com.springboot.aoplog.mapper.SysLogMapper;
import com.springboot.aoplog.util.HttpContextUtils;
import com.springboot.aoplog.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定义日志切点和切面
 */
@Aspect
public class LogAspect {

    @Resource
    private SysLogMapper sysLogMapper;

    @Pointcut("@annotation(com.springboot.aoplog.annotation.Log)")
    public void ponitcut() {
    }

    @Around("ponitcut()")
    public Object arount(ProceedingJoinPoint point) {
        Object result = null;
        long starttime = System.currentTimeMillis();
        try {
            result = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis() - starttime;
        saveLog(point,time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法信息
        Method method = methodSignature.getMethod();
        // 实体类
        SysLog sysLog = new SysLog();

        // 获取方法的注解
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            sysLog.setOperation(logAnnotation.value());
        }

        // 获取请求方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = methodSignature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 请求方法的参数
        Object[] args = joinPoint.getArgs();
        // 获取请求方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params = " " + paramNames[i] + ": " + args[i];
            }

            sysLog.setParams(params);
        }

        // 获取request
        HttpServletRequest httpServletRequest = HttpContextUtils.getHttpServletRequest();
        // 设置ip
        sysLog.setIp(IPUtils.getIpAddr(httpServletRequest));
        // 模拟用户
        sysLog.setUsername("user001");
        sysLog.setTime((int)time);
        Date data = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysLog.setCtime(simpleDateFormat.format(data));

        sysLogMapper.saveSysLog(sysLog);
    }

}
