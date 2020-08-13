# ComponentSample
👾组件化通信的一点尝试

libRouter为Activity跳转库,仿Retrofit设计，动态代理实现，模块间跳转使用反射实现

libService为模块API暴露库，接口回调实现

    使用:
        1.定义暴露接口继承IService在公共lib,并在各自模块实现这些接口
        
        interface ServiceB: IService{
              fun testB(callback:(argsFromB: String)-> Unit)
              fun goB(context: Context, args: String)
        }
  
        2.在Application中注册接口的实现类
        
          ServiceRegistry.register(ServiceAImpl::class.java）
          
        3.在模块A调用模块B服务
        
           val serviceB = ServiceProvider.produce(ServiceB::class.java)
           serviceB.testB{argsFromB ->  doSomeThing(argsFromB)}

ps: app为空壳app,plugA、plugB为两个不相互依赖的组件、通过接口暴露在libCommon库中，相互调用
