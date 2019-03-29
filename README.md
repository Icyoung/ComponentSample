# ComponentSample
组件化通信的一点尝试

libRouter为Activity跳转库,仿Retrofit设计，动态代理实现，模块间跳转使用反射实现

libService为模块API暴露库，接口回调实现

ps: app为空壳app,pluga、plugb为两个不相互依赖的组件、通过接口暴露在libCommon库中，相互调用
