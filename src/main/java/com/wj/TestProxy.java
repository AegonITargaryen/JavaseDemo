package com.wj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 工人接口
 */
interface Worker {
    void work(String workName);
}

/**
 * Worker实现类。计算机工人
 */
class ComputerWorker implements Worker {

    public void work(String workName) {
        System.out.println("computer worker work " + workName);
    }
}

/**
 * Worker接口的静态代理
 */
class WorkerStsticProxy implements Worker {

    private Worker worker;

    public WorkerStsticProxy(Worker worker) {
        this.worker = worker;
    }

    public void work(String workName) {
        preWork();
        this.worker.work(workName);
        afterWork();
    }

    private void preWork() {
        System.out.println("computer worker pre work");
    }

    private void afterWork() {
        System.out.println("computer worker after work");
    }

}

/**
 * 动态代理处理类。对象worker的全部动态代理都通过调用invoke方法来实现方法代理
 */
class WorkerDynamicProxyHandler implements InvocationHandler {

    private Object worker;

    public WorkerDynamicProxyHandler(Object worker) {
        this.worker = worker;
    }


    /**
     *
     * @param proxy   调用该方法的动态代理对象
     * @param method  调用的方法
     * @param args    参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        preWork();
        Object invoke = method.invoke(worker, args);
        afterWork();
        return null;
    }


    private void preWork() {
        System.out.println("computer worker pre work");
    }

    private void afterWork() {
        System.out.println("computer worker after work");
    }
}


public class TestProxy {
    /**
     * 使用静态代理
     *
     * 基于接口的代理。代理对象需要实现接口的所有方法
     */
    public static void staticProxyTest() {
        Worker worker = new ComputerWorker();
        WorkerStsticProxy workerStaticProxy = new WorkerStsticProxy(worker);
        workerStaticProxy.work("111");
    }

    /**
     * 使用动态代理
     *
     * 只需实现InvocationHandler接口即可。
     */
    public static void dynamicProxyTest() {
        Worker worker = new ComputerWorker();
        WorkerDynamicProxyHandler workerDynamicProxyHandler = new WorkerDynamicProxyHandler(worker);
        Worker dynamicProxy = (Worker) Proxy.newProxyInstance(
                // 指定当前target对象使用类加载器
                worker.getClass().getClassLoader(),
                // 对象实现的接口的类型
                worker.getClass().getInterfaces(),
                // 动态代理处理对象
                workerDynamicProxyHandler
        );
        dynamicProxy.work("111");
    }

    public static void main(String[] args) throws Exception {
        dynamicProxyTest();
    }




}
