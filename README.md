spring功能探索

1. AOP
    1. 动态代理 cglib/jdk
    2. 静态代理 aspectj-compiler
2. testcontainers-springboot
3. spring-data-jpa
    1. 最佳实践
       code first然后配合Jpa-buddy自动生成初始化DDL然后加以修改作为最终DDL
       本地开发环境hibernate.ddl-auto=update使用自动生成
       测试环境hibernate.ddl-auto=validate
       生产环境hibernate.ddl-auto=none