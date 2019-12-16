dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
//    cache.region.factory_class = 'org.hibernate.cache.SingletonEhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}

// environment specific settings
environments {
    development {
        dataSource {
            properties {
              /*  MaxConnections = 200
                InitialConnections = 80*/
            }
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
        }

    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }

    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            properties {
               // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
               jmxEnabled = true
               initialSize = 5
               maxActive = 50
               minIdle = 5
               maxIdle = 25
               maxWait = 10000
               maxAge = 10 * 60000
               timeBetweenEvictionRunsMillis = 5000
               minEvictableIdleTimeMillis = 60000
               validationQuery = "SELECT 1"
               validationQueryTimeout = 3
               validationInterval = 15000
               testOnBorrow = true
               testWhileIdle = true
               testOnReturn = false
               jdbcInterceptors = "ConnectionState"
               defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
            }
        }
    }
}

dataSource_iago {
    driverClassName = "org.postgresql.Driver"
    dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
    properties {
    /*    MaxConnections = 200
        InitialConnections = 80*/
   }
}
dataSource_banner {
    driverClassName = "oracle.jdbc.driver.OracleDriver"
    jmxEnabled = true
    initialSize = 5
    maxActive = 50
    minIdle = 5
    maxIdle = 25
    maxWait = 10000
    maxAge = 10 * 60000
    timeBetweenEvictionRunsMillis = 5000
    minEvictableIdleTimeMillis = 60000
    validationQuery = "SELECT 1 from dual"
    validationQueryTimeout = 3
    validationInterval = 15000
    testOnBorrow = true
    testWhileIdle = true
    testOnReturn = true
    jdbcInterceptors = "ConnectionState"
    defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
}