package com.c.cclientparent.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 打印请求日志
 *
 * @author Nineteen
 * @since 1.0.0 [2022/12/22 20:54]
 */
@Component
@Aspect
public class PrintSessionLog {

        private static final Logger log = LoggerFactory.getLogger(PrintSessionLog.class);

        /**
         * 切入点
         */
        @Pointcut("execution(* com.c.cclientparent.translate.controller..*(..)) ")
        public void entryPoint() {
            // 无需内容
        }

        @Before("entryPoint()")
        public void doBefore(JoinPoint joinPoint) {
            try {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();

                String className = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                Object[] parameters = joinPoint.getArgs();
                log.info("==================接口请求日志开始==================");
                log.info("URL:{}", request.getRequestURL().toString());
                log.info("IP:{}", request.getRemoteAddr());
                log.info("HTTP Method:{}", request.getMethod());
                log.info("类名方法名:{}#{}", className,methodName);
                log.info("请求参数:{}", JSON.toJSONString(parameters));
                log.info("==================接口请求日志结束==================");
            } catch (Throwable e) {
                log.info("around " + joinPoint + " with exception : " + e.getMessage());
            }

        }

        @Around("entryPoint()")
        public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();

            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long time = System.currentTimeMillis() - startTime;

            log.info("==================接口[返回]日志开始==================");
            log.info("类名:{}", className);
            log.info("方法名:{}", methodName);
            log.info("返回结果:{}", JSON.toJSONString(result));
            log.info("方法执行耗时:{}ms", time);
            log.info("==================接口[返回]日志结束==================");

            return result;
        }

        @AfterThrowing(pointcut = "entryPoint()", throwing = "e")
        public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
            try {
                String className = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                Object[] parameters = joinPoint.getArgs();

                log.info("异常方法:" + className + "." + methodName + "();参数:" + JSON.toJSONString(parameters));
                log.info("异常信息:" + e.getMessage());
            } catch (Exception ex) {
                log.error("异常信息:{}", ex.getMessage());
            }
        }
}
